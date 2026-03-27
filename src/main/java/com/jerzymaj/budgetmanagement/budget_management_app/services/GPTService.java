package com.jerzymaj.budgetmanagement.budget_management_app.services;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.stereotype.Service;

@Service
public class GPTService {

    private final ChatClient chatClient;

    public GPTService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public String getAdviceFromGPT(String userPrompt) {

        return chatClient.prompt()
                .user(userPrompt)
                .options(OpenAiChatOptions.builder()
                        .model("gpt-4o-mini-2024-07-18")
                        .maxTokens(100) // CAN BE CHANGED
                        .build())
                .call()
                .content();
    }
}
