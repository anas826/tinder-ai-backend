package com.tinder.tinder_ai_backend;


import com.tinder.tinder_ai_backend.conversations.ConversationRepository;
import com.tinder.tinder_ai_backend.profiles.ProfileCreationService;
import com.tinder.tinder_ai_backend.profiles.ProfileRepository;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@EnableAutoConfiguration
public class TinderAiBackendApplication implements CommandLineRunner {

	@Autowired
	private ProfileRepository profileRepository;

	@Autowired
	private ConversationRepository conversationRepository;

	@Autowired
	private ProfileCreationService profileCreationService;

	private final ChatClient chatClient;

	public TinderAiBackendApplication(ChatClient.Builder builder) {
		this.chatClient = builder.build();
	}
	public static void main(String[] args) {
		SpringApplication.run(TinderAiBackendApplication.class, args);

	}

	public void run(String... args){
		//String response = chatClient.prompt().user("Who is Shahrukh Khan?").call().content();

		//System.out.println(response);

		profileRepository.deleteAll();
		conversationRepository.deleteAll();
		profileCreationService.saveProfilesToDB();

		profileRepository.findAll().forEach(System.out::println);


	}

}
