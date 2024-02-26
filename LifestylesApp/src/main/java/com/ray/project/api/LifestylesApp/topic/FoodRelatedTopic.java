package com.ray.project.api.LifestylesApp.topic;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class FoodRelatedTopic {

    @Value("${kafka.topic.food.getRandomRecipes-req}")
    private String fooGetRandomRecipesReqTopic;
    @Value("${kafka.topic.food.getRandomRecipes-resp}")
    private String fooGetRandomRecipesRespTopic;
    @Value("${kafka.topic.food.getNutrientById-req}")
    private String fooGetNutrientByIdReqTopic;
    @Value("${kafka.topic.food.getNutrientById-resp}")
    private String fooGetNutrientByIdRespTopic;

    @Bean
    public NewTopic foodGetRandomRecipesReqTopic() {
        return new NewTopic(fooGetRandomRecipesReqTopic, 1, (short) 1);
    }

    @Bean
    public NewTopic foodGetRandomRecipesRespTopic() {
        return new NewTopic(fooGetRandomRecipesRespTopic, 1, (short) 1);
    }

    @Bean
    public NewTopic foodGetNutrientByIdReqTopic() {
        return new NewTopic(fooGetNutrientByIdReqTopic, 1, (short) 1);
    }

    @Bean
    public NewTopic foodGetNutrientByIdRespTopic() {
        return new NewTopic(fooGetNutrientByIdRespTopic, 1, (short) 1);
    }
}
