package com.pratyaksh.omnidocs_ai.auth.service;

import com.pratyaksh.omnidocs_ai.auth.entity.RefreshToken;
import com.pratyaksh.omnidocs_ai.auth.entity.UserSession;
import com.pratyaksh.omnidocs_ai.auth.jwt.JwtService;
import com.pratyaksh.omnidocs_ai.auth.request.LoginRequest;
import com.pratyaksh.omnidocs_ai.auth.request.RefreshTokenRequest;
import com.pratyaksh.omnidocs_ai.auth.request.SignupRequest;
import com.pratyaksh.omnidocs_ai.auth.response.AuthResponse;
import com.pratyaksh.omnidocs_ai.auth.util.ClientInfo;
import com.pratyaksh.omnidocs_ai.auth.util.ClientInfoExtractor;
import com.pratyaksh.omnidocs_ai.user.entity.User;
import com.pratyaksh.omnidocs_ai.user.exception.UserAlreadyExistsException;
import com.pratyaksh.omnidocs_ai.user.exception.UserNotFoundException;
import com.pratyaksh.omnidocs_ai.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {

  private static final int MAX_FAILED_LOGIN_ATTEMPTS = 5;

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;
  private final JwtService jwtService;
  private final ClientInfoExtractor clientInfoExtractor;
  private final UserSessionService userSessionService;
  private final RefreshTokenService refreshTokenService;

  @Override
  public AuthResponse signup(
      SignupRequest request,
      HttpServletRequest servletRequest) {

    if (userRepository.existsByEmailIgnoreCaseAndDeletedFalse(
        request.getEmail())) {

      throw new UserAlreadyExistsException(
          request.getEmail()
      );
    }

    User user = User.create(
        request.getFirstName(),
        request.getLastName(),
        request.getEmail(),
        passwordEncoder.encode(request.getPassword())
    );

    /*
     * Temporary until Email Verification is implemented.
     * Later this line will be removed and user will remain
     * in PENDING_VERIFICATION state.
     */
    user.activate();

    user = userRepository.save(user);

    ClientInfo clientInfo =
        clientInfoExtractor.extract(servletRequest);

    UserSession session =
        userSessionService.create(
            user,
            clientInfo.getDeviceName(),
            clientInfo.getBrowser(),
            clientInfo.getOperatingSystem(),
            clientInfo.getIpAddress(),
            clientInfo.getUserAgent()
        );

    return buildAuthResponse(
        user,
        session
    );
  }

  @Override
  public AuthResponse login(
      LoginRequest request,
      HttpServletRequest servletRequest) {

    User user = userRepository
        .findByEmailIgnoreCaseAndDeletedFalse(request.getEmail())
        .orElseThrow(() ->
            new UserNotFoundException(request.getEmail())
        );

    if (user.isDisabled()) {
      throw new DisabledException("User account is disabled.");
    }

    if (user.isLocked()) {
      throw new LockedException("User account is locked.");
    }

    try {

      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              request.getEmail(),
              request.getPassword()
          )
      );

    } catch (BadCredentialsException ex) {

      user.incrementFailedLoginAttempts();

      if (user.getFailedLoginAttempts() >= MAX_FAILED_LOGIN_ATTEMPTS) {
        user.lock(java.time.LocalDateTime.now().plusMinutes(30));
      }

      userRepository.save(user);

      throw ex;
    }

    user.resetFailedLoginAttempts();
    user.updateLastLogin();

    userRepository.save(user);

    ClientInfo clientInfo =
        clientInfoExtractor.extract(servletRequest);

    UserSession session =
        userSessionService.create(
            user,
            clientInfo.getDeviceName(),
            clientInfo.getBrowser(),
            clientInfo.getOperatingSystem(),
            clientInfo.getIpAddress(),
            clientInfo.getUserAgent()
        );

    return buildAuthResponse(user, session);
  }
  @Override
  public AuthResponse refreshToken(
      RefreshTokenRequest request) {

    RefreshToken refreshToken =
        refreshTokenService.validate(
            request.getRefreshToken()
        );

    UserSession session =
        refreshToken.getSession();

    if (session.isRevoked()) {
      throw new IllegalArgumentException(
          "Session has been revoked."
      );
    }

    if (session.getExpiresAt().isBefore(java.time.LocalDateTime.now())) {
      throw new IllegalArgumentException(
          "Session has expired."
      );
    }

    User user = session.getUser();

    if (!user.isActive()) {
      throw new IllegalStateException(
          "User account is inactive."
      );
    }

    refreshTokenService.revoke(refreshToken);

    session.touch();

    userSessionService.touch(session);

    return buildAuthResponse(
        user,
        session
    );
  }

  @Override
  public void logout() {

    /*
     * JWT is stateless.
     *
     * Logout endpoint for current user will be completed
     * after JwtAuthenticationFilter stores the authenticated
     * UserSession UUID inside SecurityContext.
     *
     * Then we'll revoke:
     * 1. current session
     * 2. current refresh token
     *
     * without affecting other devices.
     */
  }

  private AuthResponse buildAuthResponse(
      User user,
      UserSession session) {

    String accessToken =
        jwtService.generateAccessToken(user);

    String refreshToken =
        jwtService.generateRefreshToken(user);

    if (session != null) {

      refreshTokenService.create(
          session,
          refreshToken
      );
    }

    return AuthResponse.builder()
        .userUuid(user.getUuid())
        .firstName(user.getFirstName())
        .lastName(user.getLastName())
        .email(user.getEmail())
        .role(user.getRole().name())
        .accessToken(accessToken)
        .refreshToken(refreshToken)
        .build();
  }

}