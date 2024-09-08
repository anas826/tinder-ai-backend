package com.tinder.tinder_ai_backend;


import com.tinder.tinder_ai_backend.conversations.ConversationRepository;
import com.tinder.tinder_ai_backend.matches.MatchRepository;
import com.tinder.tinder_ai_backend.profiles.ProfileCreationService;
import com.tinder.tinder_ai_backend.profiles.ProfileRepository;
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

	@Autowired
	private MatchRepository matchRepository;

	public static void main(String[] args) {
		SpringApplication.run(TinderAiBackendApplication.class, args);

	}

	public void run(String... args){

		conversationRepository.deleteAll();
		matchRepository.deleteAll();
		profileRepository.deleteAll();
		profileCreationService.saveProfilesToDB();

		profileRepository.findAll().forEach(System.out::println);


	}

}
