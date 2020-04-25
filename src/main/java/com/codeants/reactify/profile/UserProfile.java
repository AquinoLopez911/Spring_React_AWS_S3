package com.codeants.reactify.profile;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class UserProfile {
	
	private  final UUID userProfileId;
	
	private final String username;
	
	// S3 key
	private String userProfileImgLink; 
	

	public UserProfile(UUID userProfileId, String username, String userProfileImgLink) {
		
		super();
		this.userProfileId = userProfileId;
		this.username = username;
		this.userProfileImgLink = userProfileImgLink;
	}


	public UUID getUserProfileId() {
		return userProfileId;
	}


	public String getUsername() {
		return username;
	}


	public Optional<String> getUserProfileImgLink() {
		
		return Optional.ofNullable(userProfileImgLink);
	}


	public void setUserProfileImgLink(String userProfileImgLink) {
		this.userProfileImgLink = userProfileImgLink;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		
		if (obj == null || getClass() != obj.getClass()) return false;
		
		UserProfile other = (UserProfile) obj;
		
		
		return Objects.equals(userProfileId, other.userProfileId) && 
				Objects.equals(username, other.username) && 
				Objects.equals(userProfileImgLink, other.userProfileImgLink);
		
	}

	@Override
	public int hashCode() {
		return Objects.hash(userProfileId, username, userProfileImgLink);
	}


	
	
	
	
	

}
