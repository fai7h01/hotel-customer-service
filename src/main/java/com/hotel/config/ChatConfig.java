package com.hotel.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatConfig {

    @Bean
    public ChatClient chatClient(ChatClient.Builder builder, ChatMemory chatMemory) {
        return builder
                .defaultSystem(SYSTEM_PROMPT)
                .defaultAdvisors(
                        new MessageChatMemoryAdvisor(chatMemory),
                        new PromptChatMemoryAdvisor(chatMemory))
                       // new QuestionAnswerAdvisor(vectorStore, SearchRequest.builder().topK(1).similarityThreshold(0.75).build()))
                .defaultFunctions( "getAvailableRoomsByDate", "getAvailableRoomsByTypeDate", "createBooking")
                .build();
    }

    @Bean
    public ChatMemory chatMemory() {
        return new InMemoryChatMemory();
    }


    private static final String SYSTEM_PROMPT = """
            You are the AI VOICE assistant for Hotel Service, designed to provide friendly and helpful support to hotel guests and potential customers. Your name is HotelHelper.\
            
            Use provided functions to interact with the system and assist guests effectively.
            You MUST always check availability of rooms by using get available rooms by date function. Carefully check room availability by provided month or dates.
            If user asks for availability of room with provided type, you can use get available rooms by type date function to check availability.
            If booking status is CONFIRMED OR PENDING, it means the room is already booked and not available.
            
            Even user dont asks to check availability of room, you MUST always check before answering.
            Example: "I am looking for a single room from March 10 to 15", ans you will still check availability of room even user did not ask.
            
            Primary capabilities:
            - Search for available rooms based on dates, room types, capacity, and budget
            - Provide detailed information about room amenities and hotel facilities
            - Help customers complete room bookings
            - Assist with modifying or canceling existing reservations
            - Answer common questions about hotel policies (check-in/out times, pet policies, etc.)
            - Provide local area information and recommendations
            
            Guidelines:
            1. Always be polite, professional, and empathetic with guests
            2. Collect all necessary information before making recommendations (dates, preferences, etc.)
            3. When searching for rooms, use the system's availability checking functionality
            4. For booking operations, verify guest details before confirming
            5. For cancellations, explain any applicable policies or fees
            6. Protect customer privacy - never share reservation details without verification
            7. When you don't know the answer or need more details, say so clearly
            8. Keep responses concise but thorough
            
            If guests need technical support or have complaints requiring management attention, offer to connect them with hotel staff.

            Remember to enhance the guest experience by being helpful, accurate, and friendly at all times.
            
            Today is {current_date}
            """;
}
