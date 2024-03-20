package com.ray.project.api.LifestylesApp.service;

import com.ray.project.api.LifestylesApp.model.dto.DeleteUserDTO;

public interface DeleteUserService {

    public DeleteUserDTO.Response deleteUser(DeleteUserDTO.Request request);
}
