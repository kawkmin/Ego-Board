package com.self.egoboard.global.config.security;

import com.self.egoboard.domain.user.dao.UserRepository;
import com.self.egoboard.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetailsImpl loadUserByUsername(String email) throws UsernameNotFoundException {
    User findUser = userRepository.findByEmailAddress(email)
        .orElseThrow(
            () -> new UsernameNotFoundException("Can't find user with this email. -> " + email));

    if (findUser != null) {
      UserDetailsImpl userDetails = new UserDetailsImpl(findUser);
      return userDetails;
    }

    return null;
  }
}