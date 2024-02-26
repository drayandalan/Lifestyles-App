package com.ray.project.api.LifestylesApp.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfiguration {

    @Value("${kafka.bootstrapAddress}")
    private String bootstrapAddress;
    @Value("${kafka.consumer.group}")
    private String groupId;

    @Bean
    public KafkaAdmin kafkaAdmin(@Value("${kafka.username:}") String username,
                                 @Value("${kafka.password:}") String password) {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configs.put(AdminClientConfig.RECEIVE_BUFFER_CONFIG, 104857600);
        configs.put(AdminClientConfig.SEND_BUFFER_CONFIG, 104857600);
        configs.put(AdminClientConfig.REQUEST_TIMEOUT_MS_CONFIG, 300000);
        configs.put(AdminClientConfig.DEFAULT_API_TIMEOUT_MS_CONFIG, 300000);

        if (!username.isEmpty() && !password.isEmpty()) {
            configs.put("security.protocol", "SASL_PLAINTEXT"); // Or SASL_SSL for SSL encryption
            configs.put("sasl.mechanism", "PLAIN");
            configs.put("sasl.jaas.config", "org.apache.kafka.common.security.plain.PlainLoginModule required username=\"" + username + "\" password=\"" + password + "\";");
        }

        return new KafkaAdmin(configs);
    }
}
