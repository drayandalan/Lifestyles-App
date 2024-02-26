package com.ray.project.api.FoodApp.service;

import com.ray.project.api.FoodApp.model.dto.RandomRecipesDTO;

import java.util.Map;

public interface GetRandomRecipesService {

    public RandomRecipesDTO.Response getRandomRecipes(Map<String, Object> request);
}
