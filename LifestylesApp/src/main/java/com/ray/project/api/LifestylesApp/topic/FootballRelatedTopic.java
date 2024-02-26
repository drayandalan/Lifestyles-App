package com.ray.project.api.LifestylesApp.topic;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class FootballRelatedTopic {

    @Value("${kafka.topic.football.getLeagueStandingsBySeason-req}")
    private String footballGetLeagueStandingsBySeasonReqTopic;
    @Value("${kafka.topic.football.getLeagueStandingsBySeason-resp}")
    private String footballGetLeagueStandingsBySeasonRespTopic;

    @Bean
    public NewTopic footballGetLeagueStandingsBySeasonReqTopic() {
        return new NewTopic(footballGetLeagueStandingsBySeasonReqTopic, 1, (short) 1);
    }

    @Bean
    public NewTopic footballGetLeagueStandingsBySeasonRespTopic() {
        return new NewTopic(footballGetLeagueStandingsBySeasonRespTopic, 1, (short) 1);
    }
}
