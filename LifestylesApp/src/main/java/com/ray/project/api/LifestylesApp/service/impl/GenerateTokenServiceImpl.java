package com.ray.project.api.LifestylesApp.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ray.project.api.LifestylesApp.model.dto.GenerateTokenDTO;
import com.ray.project.api.LifestylesApp.repository.UserRepository;
import com.ray.project.api.LifestylesApp.service.GenerateTokenService;
import com.ray.project.api.LifestylesApp.util.JwtTokenUtil;
import com.ray.project.api.LifestylesApp.util.ObjectMapperUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GenerateTokenServiceImpl implements GenerateTokenService, UserDetailsService {

    private final ObjectMapper objectMapper = ObjectMapperUtil.getObjectMapper();
    @Autowired
    private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private JwtTokenUtil jwtTokenUtil;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(user -> User.withUsername(user.getUsername())
                        .password(user.getPassword())
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }

    @Override
    public GenerateTokenDTO.Response generateToken(GenerateTokenDTO.Request request) {
        GenerateTokenDTO.Response response = GenerateTokenDTO.Response.builder().build();
        UserDetails userDetails = null;

        try {
            userDetails = loadUserByUsername(request.getUsername());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (userDetails != null && passwordEncoder.matches(request.getPassword(), userDetails.getPassword())) {
            String token = jwtTokenUtil.generateToken(request.getUsername());
            log.info("JWT Token: "+token);
            response.setToken(token);
        }

        return response;
    }
}
