package com.pratyaksh.omnidocs_ai.auth.service;

import com.pratyaksh.omnidocs_ai.auth.config.JwtProperties;
import com.pratyaksh.omnidocs_ai.auth.entity.RefreshToken;
import com.pratyaksh.omnidocs_ai.auth.entity.UserSession;
import com.pratyaksh.omnidocs_ai.auth.jwt.JwtService;
import com.pratyaksh.omnidocs_ai.auth.repository.RefreshTokenRepository;
import com.pratyaksh.omnidocs_ai.auth.request.LoginRequest;
import com.pratyaksh.omnidocs_ai.auth.request.LogoutRequest;
import com.pratyaksh.omnidocs_ai.auth.request.RefreshTokenRequest;
import com.pratyaksh.omnidocs_ai.auth.request.SignupRequest;
import com.pratyaksh.omnidocs_ai.auth.response.AuthResponse;
import com.pratyaksh.omnidocs_ai.auth.util.ClientInfo;
import com.pratyaksh.omnidocs_ai.auth.util.ClientInfoExtractor;
import com.pratyaksh.omnidocs_ai.dashboard.entity.UserStatistics;
import com.pratyaksh.omnidocs_ai.dashboard.repository.UserStatsRepository;
import com.pratyaksh.omnidocs_ai.user.entity.User;
import com.pratyaksh.omnidocs_ai.user.exception.UserAlreadyExistsException;
import com.pratyaksh.omnidocs_ai.user.repository.UserRepository;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final JwtProperties jwtProperties;
  private final AuthenticationManager authenticationManager;
  private final ClientInfoExtractor clientInfoExtractor;
  private final UserSessionService sessionService;
  private final RefreshTokenService refreshTokenService;
  private final RefreshTokenRepository refreshTokenRepository;
  private final UserStatsRepository userStatsRepository;

  @Override
  public AuthResponse signup(SignupRequest request) {

    if (userRepository.existsByEmailIgnoreCaseAndDeletedFalse(request.getEmail())) {
      throw new UserAlreadyExistsException(request.getEmail());
    }

    User user = User.create(
        request.getFirstName(),
        request.getLastName(),
        request.getEmail(),
        passwordEncoder.encode(request.getPassword())
    );

    user.activate();
    user = userRepository.save(user);

    UserStatistics stats = new UserStatistics();
    stats.setUser(user);
    stats.setTotalWorkspaces(0);
    stats.setTotalDocuments(0);

    userStatsRepository.save(stats);

    return buildAuthResponse(user, null, null);
  }

  @Override
  public AuthResponse login(LoginRequest request,
      jakarta.servlet.http.HttpServletRequest servletRequest) {

    try {
      // 1. Attempt authentication
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              request.getEmail(),
              request.getPassword()
          )
      );
    } catch (org.springframework.security.core.AuthenticationException e) {
      // Catches BadCredentialsException, LockedException, DisabledException, etc.
      throw new BadCredentialsException("Invalid email or password.");
    }

    // 2. Fetch the user safely
    // If authentication passed, the user definitely exists, but we still handle the optional safely.
    User user = userRepository.findByEmailIgnoreCaseAndDeletedFalse(request.getEmail())
        .orElseThrow(() -> new BadCredentialsException("User not found"));

    ClientInfo client = clientInfoExtractor.extract(servletRequest);

    UserSession session = sessionService.create(
        user,
        client.getDeviceName(),
        client.getBrowser(),
        client.getOperatingSystem(),
        client.getIpAddress(),
        client.getUserAgent()
    );

    String accessToken = jwtService.generateAccessToken(user);
    String refreshToken = jwtService.generateRefreshToken(user);

    refreshTokenService.create(session, refreshToken);

    user.resetFailedLoginAttempts();
    user.updateLastLogin();

    return buildAuthResponse(user, accessToken, refreshToken);
  }

  @Override
  public AuthResponse refreshToken(RefreshTokenRequest request) {

    String oldRefreshTokenStr = request.getRefreshToken();

    // 1. Structural validation
    if (!jwtService.isTokenValid(oldRefreshTokenStr) || !jwtService.isRefreshToken(oldRefreshTokenStr)) {
      throw new IllegalArgumentException("Invalid refresh token format");
    }

    // 2. Database validation: Find the database record of the token
    RefreshToken oldTokenRecord = refreshTokenRepository.findByToken(oldRefreshTokenStr)
        .orElseThrow(() -> new IllegalArgumentException("Refresh token not found in database"));

    if (oldTokenRecord.isRevoked()) {
      throw new IllegalArgumentException("Refresh token has already been revoked");
    }

    // 3. Extract the UserSession from your old token record
    // Assuming your RefreshToken entity has a `getSession()` relationship mapping to UserSession
    UserSession session = oldTokenRecord.getSession();

    // Guard clause: Make sure the parent session itself is still active
    if (session == null || !session.isActive()) {
      throw new IllegalArgumentException("The associated user session is inactive or expired");
    }

    // 4. Revoke the old token and refresh the session timestamp
    oldTokenRecord.setRevoked(true);
    refreshTokenRepository.save(oldTokenRecord);

    session.touch(); // Updates the lastActivityAt timestamp on your session entity
    // userSessionRepository.save(session); // Only needed if cascading isn't turned on

    // 5. Generate new tokens using the session's owner
    User user = session.getOwner();
    String newAccessToken = jwtService.generateAccessToken(user);
    String newRefreshTokenStr = jwtService.generateRefreshToken(user);

    // 6. Save the new refresh token to the database linked to the same session
    RefreshToken newTokenRecord = RefreshToken.builder()
        .token(newRefreshTokenStr)
        .revoked(false)
        .session(session)
        .expiresAt(LocalDateTime.now().plus(jwtProperties.getRefreshTokenExpiration(),
            ChronoUnit.MILLIS))// Attaching the fetched UserSession object here
        .build();
    refreshTokenRepository.save(newTokenRecord);

    return buildAuthResponse(user, newAccessToken, newRefreshTokenStr);
  }

  @Override
  public void logout(LogoutRequest request) {

    RefreshToken refreshToken =
        refreshTokenService.validate(
            request.getRefreshToken()
        );

    UserSession session =
        refreshToken.getSession();

    refreshTokenService.revoke(refreshToken);

    sessionService.revoke(session);
  }

  private AuthResponse buildAuthResponse(
      User user,
      String accessToken,
      String refreshToken) {

    LocalDateTime accessExpiry =
        LocalDateTime.now()
            .plus(jwtService.getAccessTokenExpiration(), ChronoUnit.MILLIS);

    LocalDateTime refreshExpiry =
        LocalDateTime.now()
            .plus(jwtService.getRefreshTokenExpiration(), ChronoUnit.MILLIS);

    return AuthResponse.builder()
        .userUuid(user.getUuid())
        .firstName(user.getFirstName())
        .lastName(user.getLastName())
        .email(user.getEmail())
        .role(user.getRole().name())
        .accessToken(accessToken)
        .refreshToken(refreshToken)
        .accessTokenExpiresAt(accessExpiry)
        .refreshTokenExpiresAt(refreshExpiry)
        .accessTokenExpiresIn(jwtService.getAccessTokenExpiration())
        .refreshTokenExpiresIn(jwtService.getRefreshTokenExpiration())
        .build();
  }
}