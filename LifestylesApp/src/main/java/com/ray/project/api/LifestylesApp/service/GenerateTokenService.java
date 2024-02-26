package com.ray.project.api.LifestylesApp.service;

import com.ray.project.api.LifestylesApp.model.dto.GenerateTokenDTO;

public interface GenerateTokenService {

    public GenerateTokenDTO.Response generateToken(GenerateTokenDTO.Request request);
}
