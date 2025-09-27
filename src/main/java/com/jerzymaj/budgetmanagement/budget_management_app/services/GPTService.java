package com.jerzymaj.budgetmanagement.budget_management_app.services;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;

@Service
public class GPTService {

    private static final String API_URL = "https://api.openai.com/v1/chat/completions";
    private static final String API_KEY = "MY API KEY";

    public static String getAdviceFromGPT(String userPrompt){

        RestTemplate restTemplate = new RestTemplate();

        String requestBody = "{"
                + "\"model\": \"gpt-4o-mini-2024-07-18\","
                + "\"messages\": [{\"role\": \"user\", \"content\": \"" + userPrompt + "\"}],"
                + "\"max_tokens\": 100" // TO CHANGE
                + "}";

        HttpHeaders headers = new HttpHeaders();

        headers.set("Authorization", "Bearer " + API_KEY);
        headers.set("Content-Type", "application/json");

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody,headers);
        ResponseEntity<String> response = restTemplate.exchange(API_URL, HttpMethod.POST, requestEntity, String.class);

        return response.getBody();
    }

}
