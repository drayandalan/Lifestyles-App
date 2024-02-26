package com.ray.project.api.LifestylesApp.service;

import com.ray.project.api.LifestylesApp.model.dto.RegisterDTO;

public interface RegisterService {

    public RegisterDTO.Response register(RegisterDTO.Request request);
}
