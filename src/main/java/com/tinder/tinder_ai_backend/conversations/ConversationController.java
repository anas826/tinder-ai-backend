package com.tinder.tinder_ai_backend.conversations;

import com.tinder.tinder_ai_backend.profiles.Profile;
import com.tinder.tinder_ai_backend.profiles.ProfileRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

@RestController
public class ConversationController {

    private final ConversationRepository conversationRepository;
    private final ProfileRepository profileRepository;
    private final ConversationService conversationService;

    public ConversationController(ConversationRepository conversationRepository, ProfileRepository profileRepository, ConversationService conversationService){
        this.conversationRepository = conversationRepository;
        this.profileRepository = profileRepository;
        this.conversationService = conversationService;
    }
    @CrossOrigin(origins = "*")
    @PostMapping("/conversations/{conversationId}")
    public Conversation addMessageToConversation(@PathVariable String conversationId,
                                                 @RequestBody ChatMessage chatMessage) {

        Conversation conversation = conversationRepository.findById(conversationId).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Unable to find the conversation with ID " + conversationId));

        String matchProfileId = conversation.profileId();

        Profile profile = profileRepository.findById(matchProfileId)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Unable to find the profile with Id " + matchProfileId));

        Profile user = profileRepository.findById(chatMessage.authorId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Unable to fing the Author Id " + chatMessage.authorId()));

        ChatMessage chatMessageWithTime = new ChatMessage(
                chatMessage.messageText(),
                chatMessage.authorId(),
                LocalDateTime.now()
        );

        conversation.messages().add(chatMessageWithTime);
        conversationService.generateProfileResponse(conversation, profile, user);
        conversationRepository.save(conversation);
        return conversation;

    }
    @CrossOrigin(origins = "*")
    @GetMapping("/conversations/{conversationId}")
    public Conversation getConversation(@PathVariable String conversationId){

        return conversationRepository.findById(conversationId).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Unable to find the conversation Id " + conversationId));

    }

}
