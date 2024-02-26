package com.ray.project.api.FoodApp.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ray.project.api.FoodApp.model.dto.RandomRecipesDTO;
import com.ray.project.api.FoodApp.service.GetRandomRecipesService;
import com.ray.project.api.FoodApp.util.ObjectMapperUtil;
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

@Slf4j
@Service
public class GetRandomRecipesServiceImpl implements GetRandomRecipesService {

    @Value("${food.app.open.api.uri}")
    private String foodOpenApiUri;
    @Value("${food.app.api.key}")
    private String foodApiKey;

    private final ObjectMapper objectMapper = ObjectMapperUtil.getObjectMapper();
    @Autowired private OkHttpClient okHttpClient;

    @Override
    public RandomRecipesDTO.Response getRandomRecipes(Map<String, Object> request) {
        RandomRecipesDTO.Response response = RandomRecipesDTO.Response.builder().build();

        HttpUrl.Builder urlBuilder = Objects.requireNonNull(
                HttpUrl.parse(foodOpenApiUri + "recipes/random")).newBuilder();
        urlBuilder.addQueryParameter("number", String.valueOf(request.get("number")));
        urlBuilder.addQueryParameter("limitLicense", String.valueOf(request.get("limitLicense")));
        String url = urlBuilder.build().toString();

        Request httpRequest = new Request.Builder()
                .url(url)
                .addHeader("x-api-key", foodApiKey)
                .build();
        try (Response httpResponse = okHttpClient.newCall(httpRequest).execute()) {
            assert httpResponse.body() != null;
            if (httpResponse.code() == 404) throw new IOException("Not found: "+httpResponse);
            String responseBody = httpResponse.body().string();
            log.info("Food API resp: "+responseBody);
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
