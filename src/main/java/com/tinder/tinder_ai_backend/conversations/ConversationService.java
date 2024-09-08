package com.tinder.tinder_ai_backend.conversations;

import com.tinder.tinder_ai_backend.profiles.Profile;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.ai.ollama.api.OllamaApi.Message.Role.*;

@Service
public class ConversationService {


    private final ChatClient chatClient;

    public ConversationService(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    public Conversation generateProfileResponse(Conversation conversation, Profile  profile, Profile user) {

        String userMessage =null;
        String assistantMessage = null;

        for (ChatMessage message: conversation.messages()){
            if(message.authorId().equals(profile.id())){
                assistantMessage = message.messageText();
            }else{
                userMessage = message.messageText();
            }
        }

        OllamaApi ollamaApi = new OllamaApi();
        OllamaApi.ChatRequest request = OllamaApi.ChatRequest.builder("llama3.1")
                .withStream(false) // not streaming
                .withMessages(List.of(OllamaApi.Message.builder(
                                        SYSTEM)
                                .withContent("Act as tinder user")
                                .build(),
                        OllamaApi.Message.builder(
                                ASSISTANT)
                                .withContent(assistantMessage)
                                .build(),
                        OllamaApi.Message.builder(USER)
                                .withContent(userMessage)
                                .build()))
                .withOptions(OllamaOptions.create().withTemperature(0.9f))
                .build();
        var response = ollamaApi.chat(request);
        String content = response.message().content().toString();
        conversation.messages().add(new ChatMessage(
                content,
                profile.id(),
                LocalDateTime.now()
        ));

        return conversation;
    }

}
