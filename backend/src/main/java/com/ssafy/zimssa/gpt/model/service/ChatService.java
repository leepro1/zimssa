package com.ssafy.zimssa.gpt.model.service;

import io.github.flashvayne.chatgpt.service.ChatgptService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatgptService chatgptService;

    public String getChatResponse(String prompt) {
        // ChatGPT 에게 질문을 던집니다.
        String expertPrompt = "You are a real estate expert. Answer the following question with detailed and professional insights.\n\n" + prompt;

        return chatgptService.sendMessage(expertPrompt);
    }
}