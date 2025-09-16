package com.afs.restapi.controller;

import com.afs.restapi.dto.PlanRequestDto;
import com.afs.restapi.agent.PlannerService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
public class ChatController {
    private final ChatClient chatClient;

    private final PlannerService plannerService;

    public ChatController(ChatClient.Builder chatClientBuilder, PlannerService plannerService) {
        this.chatClient = chatClientBuilder.build();
        this.plannerService = plannerService;
    }

    @GetMapping("/chat")
    String normalChat(String userInput) {
        return this.chatClient.prompt()
                .user(userInput)
                .call()
                .content();
    }


    @GetMapping(value = "/chat/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Flux<String> streamChat(@RequestParam String userInput) {
        return this.chatClient.prompt()
                .user(userInput)
                .stream()
                .content();
    }

    @GetMapping(value = "/chat/todo", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Flux<String> simplePromptExample(@RequestParam String userInput) {
        return this.chatClient.prompt()
                .system("Your are an Todo tasks manager, you should only answer about Todo manage related questions." +
                        "If the user ask you about other things, you should answer you don't know. ")
                .user(userInput)
                .stream()
                .content();
    }

    @PostMapping("/chat/plan")
    String plan(@RequestBody PlanRequestDto request) {
        String userInput = request.getUserInput();
        return plannerService.plan(userInput);
    }
}
