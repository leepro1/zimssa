package com.ssafy.zimssa.gpt.controller;

import com.ssafy.zimssa.gpt.model.service.ChatService;
import io.github.flashvayne.chatgpt.service.ChatgptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("GPTController") // 커스텀 이름 설정

@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/chat-gpt")
public class TestController {
    private final ChatgptService chatgptService;
    private final ChatService chatService;

    @PostMapping("")
    public String test(@RequestBody String question){
        return chatService.getChatResponse(question);

    }
}
