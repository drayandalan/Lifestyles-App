package com.ray.project.api.FoodApp.service;

import com.ray.project.api.FoodApp.model.dto.NutrientDTO;

import java.util.Map;

public interface GetNutrientByIdService {

    public NutrientDTO.Response getNutrientById(Map<String, Object> request);
}
