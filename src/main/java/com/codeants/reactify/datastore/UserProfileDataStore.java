 package com.codeants.reactify.datastore;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.codeants.reactify.profile.UserProfile;

import org.springframework.stereotype.Repository;


@Repository 
public class UserProfileDataStore {
	
	private static final List<UserProfile> USER_PROFILES = new ArrayList<>();
	
	static {
		USER_PROFILES.add(new UserProfile(UUID.fromString("5ddc4a9b-6dd2-448d-99a0-dc4d90cfe4de"), "Natasha Ayala", null));
		USER_PROFILES.add(new UserProfile(UUID.fromString("8316cd8e-4cec-4830-8415-03653ecd7d73"), "Alana Ayala", null));
		USER_PROFILES.add(new UserProfile(UUID.fromString("1744feac-f95d-4e09-8c4e-33e56fad82df"), "Anthony aquino Ayala", null));
	}

	
	public List<UserProfile> getUserProfiles() {
		return USER_PROFILES;
	}
}
