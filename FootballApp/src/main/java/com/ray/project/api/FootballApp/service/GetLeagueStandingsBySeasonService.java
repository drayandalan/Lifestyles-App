package com.ray.project.api.FootballApp.service;

import com.ray.project.api.FootballApp.model.dto.LeagueStandingsDTO;

import java.util.Map;

public interface GetLeagueStandingsBySeasonService {

    public LeagueStandingsDTO.Response getLeagueStandingsBySeason(Map<String, Object> request);
}
