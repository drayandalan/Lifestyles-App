package com.ray.project.api.LifestylesApp.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@RestController
public class FootballController {

    @Value("${kafka.topic.football.getLeagueStandingsBySeason-req}")
    private String getLeagueStandingsBySeasonRequestTopic;

    @Autowired
    @Qualifier("footballGetLeagueStandingsBySeasonRequestReplyKafkaTemplate")
    private ReplyingKafkaTemplate<String, Map<String, Object>, String> footballGetLeagueStandingsBySeasonRequestReplyKafkaTemplate;

    @GetMapping("/lifestyles-api/football/getLeagueStandingsBySeason")
    public ResponseEntity<?> footballGetLeagueStandingsBySeason(
            @RequestParam String league,
            @RequestParam String season) {
        log.info("league: {}, season: {}", league, season);
        Map<String, Object> request = new LinkedHashMap<>();
        request.put("league", league);
        request.put("season", season);
        try {
            ProducerRecord<String, Map<String, Object>> pr = new ProducerRecord<>(getLeagueStandingsBySeasonRequestTopic, request);
            RequestReplyFuture<String, Map<String, Object>, String> future = footballGetLeagueStandingsBySeasonRequestReplyKafkaTemplate.sendAndReceive(pr);
            ConsumerRecord<String, String> record = future.get(5, TimeUnit.MINUTES);
            return ok().body(new ObjectMapper().readValue(record.value(), Map.class));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing the request.");
        }
    }
}
