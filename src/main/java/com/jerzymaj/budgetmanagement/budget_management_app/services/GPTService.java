package com.jerzymaj.budgetmanagement.budget_management_app.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;

@Service
public class GPTService {

    private static final String API_URL = "https://api.openai.com/v1/chat/completions";

    private final String gptApiKey;
    private final RestTemplate restTemplate;

    public GPTService(@Value("${openai.api.key}") String gptApiKey, RestTemplate restTemplate) {
        this.gptApiKey = gptApiKey;
        this.restTemplate = restTemplate;
    }

    public String getAdviceFromGPT(String userPrompt) {

        String requestBody = "{"
                + "\"model\": \"gpt-4o-mini-2024-07-18\","
                + "\"messages\": [{\"role\": \"user\", \"content\": \"" + userPrompt + "\"}],"
                + "\"max_tokens\": 100" // TO CHANGE
                + "}";

        HttpHeaders headers = new HttpHeaders();

        headers.set("Authorization", "Bearer " + gptApiKey);
        headers.set("Content-Type", "application/json");

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(API_URL, HttpMethod.POST, requestEntity, String.class);

        return response.getBody();
    }

}
