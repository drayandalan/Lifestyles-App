package com.ray.project.api.FoodApp.kafka.listener;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ray.project.api.FoodApp.model.dto.NutrientDTO;
import com.ray.project.api.FoodApp.service.GetNutrientByIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class GetNutrientByIdListener {

    @Autowired private GetNutrientByIdService getNutrientByIdService;

    @KafkaListener(
            topics = "${kafka.topic.food.getNutrientById-req}",
            groupId = "${kafka.consumer.group}",
            containerFactory = "getNutrientByIdKafkaListenerContainerFactory")
    @SendTo
    public NutrientDTO.Response getNutrientById(String message) {
        Map<String, Object> request;
        NutrientDTO.Response response = NutrientDTO.Response.builder().build();

        try {
            request = new ObjectMapper().readValue(message, new TypeReference<>() {});
            response = getNutrientByIdService.getNutrientById(request);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        return response;
    }
}
