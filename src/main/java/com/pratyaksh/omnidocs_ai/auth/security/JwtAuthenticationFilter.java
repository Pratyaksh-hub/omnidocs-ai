package com.pratyaksh.omnidocs_ai.auth.security;

import com.pratyaksh.omnidocs_ai.auth.jwt.JwtService;
import com.pratyaksh.omnidocs_ai.auth.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtService jwtService;
  private final CustomUserDetailsService userDetailsService;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain
  ) throws ServletException, IOException {

    String header = request.getHeader(HttpHeaders.AUTHORIZATION);

    if (header == null || !header.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }

    String token = header.substring(7);

    if (!jwtService.isTokenValid(token)
        || !jwtService.isAccessToken(token)) {

      filterChain.doFilter(request, response);
      return;
    }

    String email = jwtService.extractEmail(token);

    if (email != null
        && SecurityContextHolder.getContext().getAuthentication() == null) {

      UserDetails userDetails =
          userDetailsService.loadUserByUsername(email);

      UsernamePasswordAuthenticationToken authentication =
          new UsernamePasswordAuthenticationToken(
              userDetails,
              null,
              userDetails.getAuthorities()
          );

      authentication.setDetails(
          new WebAuthenticationDetailsSource()
              .buildDetails(request)
      );

      SecurityContextHolder.getContext()
          .setAuthentication(authentication);
    }

    filterChain.doFilter(request, response);
  }
}