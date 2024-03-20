package com.ray.project.api.LifestylesApp.service;

import com.ray.project.api.LifestylesApp.model.dto.UpdateUserDTO;

public interface UpdateUserService {

    public UpdateUserDTO.Response updateUser(UpdateUserDTO.Request request);
}
