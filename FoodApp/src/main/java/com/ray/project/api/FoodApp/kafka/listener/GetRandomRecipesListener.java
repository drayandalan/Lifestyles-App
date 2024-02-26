package com.ray.project.api.FoodApp.kafka.listener;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ray.project.api.FoodApp.model.dto.RandomRecipesDTO;
import com.ray.project.api.FoodApp.service.GetRandomRecipesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class GetRandomRecipesListener {

    @Autowired private GetRandomRecipesService getRandomRecipesService;

    @KafkaListener(
            topics = "${kafka.topic.food.getRandomRecipes-req}",
            groupId = "${kafka.consumer.group}",
            containerFactory = "getRandomRecipesKafkaListenerContainerFactory")
    @SendTo
    public RandomRecipesDTO.Response getRandomRecipes(String message) {
        Map<String, Object> request;
        RandomRecipesDTO.Response response = RandomRecipesDTO.Response.builder().build();

        try {
            request = new ObjectMapper().readValue(message, new TypeReference<>() {});
            response = getRandomRecipesService.getRandomRecipes(request);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        return response;
    }
}
