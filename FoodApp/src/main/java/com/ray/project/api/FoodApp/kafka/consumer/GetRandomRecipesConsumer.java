package com.ray.project.api.FoodApp.kafka.consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import java.util.Map;

@EnableKafka
@Configuration
public class GetRandomRecipesConsumer extends DefaultFoodAppConsumer<Map<String, Object>> {

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Map<String, Object>>>
    getRandomRecipesKafkaListenerContainerFactory() {
        return registerKafkaListenerContainerFactory();
    }
}
