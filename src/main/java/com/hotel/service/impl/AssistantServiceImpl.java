package com.hotel.service.impl;

import com.hotel.service.AssistantService;
import com.hotel.util.Audio;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class AssistantServiceImpl implements AssistantService {

    private final ChatClient chatClient;
    private Audio audio;

    public AssistantServiceImpl(ChatClient chatClient, Audio audio) {
        this.chatClient = chatClient;
        this.audio = audio;
    }

    @Override
    public void processVoiceRequest(String text) {

    }
}
