package com.codeants.reactify.profile;

import java.util.List;

import com.codeants.reactify.datastore.UserProfileDataStore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository 
public class UserProfileDataAccessService {

	
	private final UserProfileDataStore userProfileDataStore;
	
	@Autowired
	public UserProfileDataAccessService(UserProfileDataStore userProfileDataStore) {
		this.userProfileDataStore = userProfileDataStore;
	}
	
	
	
	List<UserProfile> getUserProfiles() {
		return userProfileDataStore.getUserProfiles();
	}
}
