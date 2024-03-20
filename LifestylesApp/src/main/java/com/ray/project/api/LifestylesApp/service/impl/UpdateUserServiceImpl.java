package com.ray.project.api.LifestylesApp.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ray.project.api.LifestylesApp.model.dto.UpdateUserDTO;
import com.ray.project.api.LifestylesApp.model.entity.User;
import com.ray.project.api.LifestylesApp.repository.UserRepository;
import com.ray.project.api.LifestylesApp.service.UpdateUserService;
import com.ray.project.api.LifestylesApp.util.ObjectMapperUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class UpdateUserServiceImpl implements UpdateUserService {

    private final ObjectMapper objectMapper = ObjectMapperUtil.getObjectMapper();
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UpdateUserDTO.Response updateUser(UpdateUserDTO.Request request) {
        UpdateUserDTO.Response response = UpdateUserDTO.Response.builder().build();

        try {
            Optional<User> userOptional = userRepository.findByUsername(request.getUsername());
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                    String message = "";
                    if (request.getNewPassword() != null && request.getNewEmail() != null) {
                        log.info("Update password & email");
                        userRepository.updateUserPasswordAndEmail(
                                passwordEncoder.encode(request.getNewPassword()),
                                request.getNewEmail(),
                                user.getUsername()
                        );
                        message = "Successfully updated password & email.";
                    }
                    if (request.getNewEmail() == null) {
                        log.info("Update password");
                        userRepository.updateUserPassword(
                                passwordEncoder.encode(request.getNewPassword()),
                                user.getUsername()
                        );
                        message = "Successfully updated password.";
                    }
                    if (request.getNewPassword() == null) {
                        log.info("update email");
                        userRepository.updateUserEmail(
                                request.getNewEmail(),
                                user.getUsername()
                        );
                        message = "Successfully updated email.";
                    }
                    response.setError(Boolean.FALSE);
                    response.setMessage(message);
                    return response;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return UpdateUserDTO.Response.builder()
                .error(Boolean.TRUE)
                .message("Failed to update data user.")
                .build();
    }
}
