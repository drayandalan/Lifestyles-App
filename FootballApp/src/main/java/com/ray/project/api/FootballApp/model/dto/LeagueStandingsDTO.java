package com.ray.project.api.FootballApp.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

public class LeagueStandingsDTO {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        @JsonProperty("isSuccess")
        private Boolean isSuccess;
        @JsonProperty("payload")
        private Map<String, Object> payload;
        @JsonProperty("responseCode")
        private String responseCode;
        @JsonProperty("responseMessage")
        private String responseMessage;
        @JsonProperty("payload_field_list")
        private List<String> payloadFieldList;
    }
}
