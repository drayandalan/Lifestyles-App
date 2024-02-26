package com.ray.project.api.LifestylesApp.producer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;

import java.util.Map;

@Configuration
public class FoodGetNutrientById extends DefaultKafkaProducer<Map<String, Object>>{
    @Value("${kafka.topic.food.getNutrientById-resp}")
    private String topicResponse;

    @Override
    public String getTopicResponse() {
        return topicResponse;
    }

    @Bean
    public ReplyingKafkaTemplate<String, Map<String, Object>, String>
    foodGetNutrientByIdRequestReplyKafkaTemplate(
            ConcurrentKafkaListenerContainerFactory<String, Map<String, Object>> containerFactory
    ) {
        return registerReplyingKafkaTemplate(containerFactory);
    }
}
