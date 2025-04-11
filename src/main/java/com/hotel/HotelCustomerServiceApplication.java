package com.hotel;

import com.hotel.util.Audio;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.content.Media;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.util.Map;
import java.util.Scanner;

@SpringBootApplication
public class HotelCustomerServiceApplication {

    public HotelCustomerServiceApplication(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public static void main(String[] args) {
        SpringApplication.run(HotelCustomerServiceApplication.class, args);
    }

    private final ChatClient chatClient;

    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {

            try (Scanner scanner = new Scanner(System.in)) {

                Audio audio = new Audio();

                while (true) {
                    audio.startRecording();
                    System.out.println("Recording... Press Enter to stop.");
                    scanner.nextLine();
                    audio.stopRecording();

                    System.out.println("Processing audio...");

                    AssistantMessage response = chatClient.prompt()
                            .messages(new UserMessage("Please answer the question based on the audio recording.",
                                    new Media(MediaType.parseMediaType("audio/wav"),
                                            new ByteArrayResource(audio.getLastRecording()))))
                            .system(s -> s.params(Map.of(
                                    "current_date", LocalDate.now().toString())))
                            .call()
                            .chatResponse()
                            .getResult()
                            .getOutput();
                    System.out.println("Assistant: " + response.getText());
                    Audio.play(response.getMedia().get(0).getDataAsByteArray());
                }

            }
        };
    }

}
