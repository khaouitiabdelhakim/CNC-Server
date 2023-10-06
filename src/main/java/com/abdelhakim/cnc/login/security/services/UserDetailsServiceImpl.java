package com.abdelhakim.cnc.login.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abdelhakim.cnc.login.models.User;
import com.abdelhakim.cnc.login.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  @Autowired
  UserRepository userRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String cin) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(cin)
        .orElseThrow(() -> new UsernameNotFoundException("User Not Found with cin: " + cin));

    return UserDetailsImpl.build(user);
  }

}
