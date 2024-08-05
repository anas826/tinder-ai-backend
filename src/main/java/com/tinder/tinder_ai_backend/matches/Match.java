package com.tinder.tinder_ai_backend.matches;

import com.tinder.tinder_ai_backend.profiles.Profile;

public record Match(String id,
                    Profile profile,
                    String conversationId) {
}
