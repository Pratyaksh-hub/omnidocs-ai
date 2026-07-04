package com.pratyaksh.omnidocs_ai.auth.service;

import com.pratyaksh.omnidocs_ai.auth.security.CustomUserDetails;
import com.pratyaksh.omnidocs_ai.user.entity.User;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserServiceImpl
    implements CurrentUserService {

  @Override
  public User getCurrentUser() {

    Authentication authentication =
        SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null ||
        !authentication.isAuthenticated() ||
        authentication instanceof AnonymousAuthenticationToken) {

      throw new AccessDeniedException(
          "User is not authenticated."
      );
    }

    Object principal = authentication.getPrincipal();

    if (!(principal instanceof UserDetails)) {

      throw new AccessDeniedException(
          "Invalid authenticated user."
      );
    }

    return ((CustomUserDetails) principal)
        .getUser();
  }

}