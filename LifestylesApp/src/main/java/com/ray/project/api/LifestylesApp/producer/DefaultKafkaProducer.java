package com.ray.project.api.LifestylesApp.producer;

import lombok.Getter;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public abstract class DefaultKafkaProducer<T> {

    @Getter
    @Value(value = "${kafka.bootstrapAddress}")
    protected String bootstrapAddress;

    @Getter
    @Value(value = "${kafka.consumer.group}")
    protected String groupId;

    @Value(("${kafka.request.timeout:300}"))
    private Long kafkaRequestTimeout;

    public abstract String getTopicResponse();

    public ReplyingKafkaTemplate<String, T, String> registerReplyingKafkaTemplate(
            ConcurrentKafkaListenerContainerFactory<String, T> containerFactory
    ) {
        containerFactory.setReplyTemplate(registerKafkaTemplate(registerProducerFactory()));
        ConcurrentMessageListenerContainer<String, T> container = registerReplyContainer(containerFactory);
        ReplyingKafkaTemplate<String, T, String> replyingKafkaTemplate = new ReplyingKafkaTemplate(registerProducerFactory(), container);
        replyingKafkaTemplate.setDefaultReplyTimeout(Duration.ofSeconds(kafkaRequestTimeout));
        return replyingKafkaTemplate;
    }

    public KafkaTemplate<String, T> registerKafkaTemplate(ProducerFactory<String, T> pf) {
        return new KafkaTemplate<>(pf);
    }

    public ProducerFactory<String, T> registerProducerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, getBootstrapAddress());
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    public ConcurrentMessageListenerContainer<String, T> registerReplyContainer(
            ConcurrentKafkaListenerContainerFactory<String, T> containerFactory
    ) {
        ConcurrentMessageListenerContainer<String, T>
                container = containerFactory.createContainer(getTopicResponse());
        container.getContainerProperties().setGroupId(getGroupId());
        return container;
    }
}
