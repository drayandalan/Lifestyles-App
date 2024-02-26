package com.ray.project.api.FootballApp.kafka.listener;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ray.project.api.FootballApp.model.dto.LeagueStandingsDTO;
import com.ray.project.api.FootballApp.service.GetLeagueStandingsBySeasonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class GetLeagueStandingsBySeasonListener {

    @Autowired private GetLeagueStandingsBySeasonService getLeagueStandingsBySeasonService;

    @KafkaListener(
            topics = "${kafka.topic.football.getLeagueStandingsBySeason-req}",
            groupId = "${kafka.consumer.group}",
            containerFactory = "getLeagueStandingsBySeasonKafkaListenerContainerFactory")
    @SendTo
    public LeagueStandingsDTO.Response getLeagueStandingsBySeason(String message) {
        Map<String, Object> request;
        LeagueStandingsDTO.Response response = LeagueStandingsDTO.Response.builder().build();

        try {
            request = new ObjectMapper().readValue(message, new TypeReference<>() {});
            response = getLeagueStandingsBySeasonService.getLeagueStandingsBySeason(request);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        return response;
    }
}
