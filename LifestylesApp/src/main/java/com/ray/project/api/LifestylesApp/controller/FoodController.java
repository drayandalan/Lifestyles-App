package com.ray.project.api.LifestylesApp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@RestController
public class FoodController {

    @Value("${kafka.topic.food.getRandomRecipes-req}")
    private String getRandomRecipesReqTopic;
    @Value("${kafka.topic.food.getNutrientById-req}")
    private String getNutrientByIdReqTopic;

    @Autowired
    @Qualifier("foodGetRandomRecipesRequestReplyKafkaTemplate")
    private ReplyingKafkaTemplate<String, Map<String, Object>, String> foodGetRandomRecipesRequestReplyKafkaTemplate;
    @Autowired
    @Qualifier("foodGetNutrientByIdRequestReplyKafkaTemplate")
    private ReplyingKafkaTemplate<String, Map<String, Object>, String> foodGetNutrientByIdRequestReplyKafkaTemplate;

    @GetMapping("/lifestyles-api/food/getRandomRecipes")
    public ResponseEntity<?> foodGetRandomRecipes(
            @RequestParam int number,
            @RequestParam boolean limitLicense) {
        log.info("number: {}, limitLicense: {}", number, limitLicense);
        Map<String, Object> request = new LinkedHashMap<>();
        request.put("number", number);
        request.put("limitLicense", limitLicense);
        try {
            ProducerRecord<String, Map<String, Object>> pr = new ProducerRecord<>(getRandomRecipesReqTopic, request);
            RequestReplyFuture<String, Map<String, Object>, String> future = foodGetRandomRecipesRequestReplyKafkaTemplate.sendAndReceive(pr);
            ConsumerRecord<String, String> record = future.get(5, TimeUnit.MINUTES);
            return ok().body(new ObjectMapper().readValue(record.value(), Map.class));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing the request.");
        }
    }

    @GetMapping("/lifestyles-api/food/getNutrientById/{id}")
    public ResponseEntity<?> foodGetNutrientById(
            @PathVariable String id) {
        log.info("id: {}", id);
        Map<String, Object> request = new LinkedHashMap<>();
        request.put("id", id);
        try {
            ProducerRecord<String, Map<String, Object>> pr = new ProducerRecord<>(getNutrientByIdReqTopic, request);
            RequestReplyFuture<String, Map<String, Object>, String> future = foodGetNutrientByIdRequestReplyKafkaTemplate.sendAndReceive(pr);
            ConsumerRecord<String, String> record = future.get(5, TimeUnit.MINUTES);
            return ok().body(new ObjectMapper().readValue(record.value(), Map.class));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing the request.");
        }
    }
}
