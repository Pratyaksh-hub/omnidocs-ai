package com.pratyaksh.omnidocs_ai.auth.service;

import com.pratyaksh.omnidocs_ai.auth.security.CustomUserDetails;
import com.pratyaksh.omnidocs_ai.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String email)
      throws UsernameNotFoundException {

    return userRepository
        .findByEmailIgnoreCaseAndDeletedFalse(email)
        .map(CustomUserDetails::new)
        .orElseThrow(() ->
            new UsernameNotFoundException(
                "User not found."
            ));
  }

}