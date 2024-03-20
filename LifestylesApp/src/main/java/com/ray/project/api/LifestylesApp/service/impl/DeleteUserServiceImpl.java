package com.ray.project.api.LifestylesApp.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ray.project.api.LifestylesApp.model.dto.DeleteUserDTO;
import com.ray.project.api.LifestylesApp.model.entity.User;
import com.ray.project.api.LifestylesApp.repository.UserRepository;
import com.ray.project.api.LifestylesApp.service.DeleteUserService;
import com.ray.project.api.LifestylesApp.util.ObjectMapperUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class DeleteUserServiceImpl implements DeleteUserService {

    private final ObjectMapper objectMapper = ObjectMapperUtil.getObjectMapper();
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public DeleteUserDTO.Response deleteUser(DeleteUserDTO.Request request) {
        DeleteUserDTO.Response response = DeleteUserDTO.Response.builder().build();

        try {
            Optional<User> userOptional = userRepository.findByUsername(request.getUsername());
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                    userRepository.deleteByIdAndUsername(user.getId(), user.getUsername());
                    response.setError(Boolean.FALSE);
                    response.setMessage("Successfully deleted user.");
                    return response;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return DeleteUserDTO.Response.builder()
                .error(Boolean.TRUE)
                .message("Failed to delete user.")
                .build();
    }
}
