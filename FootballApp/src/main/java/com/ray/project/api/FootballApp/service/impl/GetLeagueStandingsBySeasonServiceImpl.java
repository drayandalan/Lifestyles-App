package com.ray.project.api.FootballApp.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ray.project.api.FootballApp.model.dto.LeagueStandingsDTO;
import com.ray.project.api.FootballApp.service.GetLeagueStandingsBySeasonService;
import com.ray.project.api.FootballApp.util.ObjectMapperUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class GetLeagueStandingsBySeasonServiceImpl implements GetLeagueStandingsBySeasonService {

    @Value("${football.app.open.api.uri}")
    private String footballOpenApiUri;
    @Value("${football.app.api.key}")
    private String footballApiKey;

    private final ObjectMapper objectMapper = ObjectMapperUtil.getObjectMapper();
    @Autowired
    private OkHttpClient okHttpClient;

    @Override
    public LeagueStandingsDTO.Response getLeagueStandingsBySeason(Map<String, Object> request) {
        LeagueStandingsDTO.Response response = LeagueStandingsDTO.Response.builder().build();

        HttpUrl.Builder urlBuilder = Objects.requireNonNull(
                HttpUrl.parse(footballOpenApiUri + "standings")).newBuilder();
        urlBuilder.addQueryParameter("league", String.valueOf(request.get("league")));
        urlBuilder.addQueryParameter("season", String.valueOf(request.get("season")));
        String url = urlBuilder.build().toString();

        Request httpRequest = new Request.Builder()
                .url(url)
                .addHeader("x-apisports-key", footballApiKey)
                .build();
        try (Response httpResponse = okHttpClient.newCall(httpRequest).execute()) {
            assert httpResponse.body() != null;
            if (httpResponse.code() == 404) throw new IOException("Not found: "+httpResponse);
            String responseBody = httpResponse.body().string();
            log.info("Football API resp: "+responseBody);
            Map<String, Object> payload = objectMapper.readValue(responseBody, new TypeReference<>() {});
            response.setIsSuccess(Boolean.TRUE);
            response.setPayload(payload);
            response.setResponseCode("0");
            response.setResponseMessage("Sukses");

            List<String> keyList = new ArrayList<>(payload.keySet());
            List<String> sortedKeyList = keyList.stream().sorted().toList();
            response.setPayloadFieldList(sortedKeyList);
        } catch (Exception e) {
            e.printStackTrace();
            response.setIsSuccess(Boolean.FALSE);
            response.setResponseCode("-1");
            response.setResponseMessage("Gagal");
        }

        return response;
    }
}
