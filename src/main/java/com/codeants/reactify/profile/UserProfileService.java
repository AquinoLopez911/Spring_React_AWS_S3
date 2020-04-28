package com.codeants.reactify.profile;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.codeants.reactify.bucket.Bucket;
import com.codeants.reactify.fileStore.FileStore;

@Service
public class UserProfileService {
	
	private final FileStore fileStore;
	private final UserProfileDataAccessService userProfileDataAccessService;
	
	@Autowired
	public UserProfileService(UserProfileDataAccessService userProfileDataAccessService, FileStore fileStore) {
		this.userProfileDataAccessService = userProfileDataAccessService;
		this.fileStore = fileStore;
	}
	
	
	//returns all UserProfiles in UserProfileDataStore
	public List<UserProfile> getUserProfiles() {
		return userProfileDataAccessService.getUserProfiles();
	}//end gerUserProfile

	
	//uploads user profile image
	public List<UserProfile> uploadUserProfileImg(UUID userProfileId, MultipartFile file) {
		
		//CHECK
		//if image is not empty
		if(file.isEmpty()) {
			throw new IllegalStateException("cannot upload empty file [ " + file.getSize() + " ] ");
		}
		
		//if file is an image
		if(!Arrays.asList(ContentType.IMAGE_JPEG.getMimeType(),
				ContentType.IMAGE_PNG.getMimeType(),
				ContentType.IMAGE_GIF.getMimeType()).
				contains(file.getContentType())) {
			throw new IllegalStateException("file must be an image or GIF [ " + file.getSize() + " ]");
		}
		
		//if the user exists in the DB
		UserProfile user = getUserProfileIdOrThrow(userProfileId);
		
		//grab some metadata from file if any
		Map<String, String> metadata = new HashMap<>();
		metadata.put("Content-Type", file.getContentType());
		metadata.put("Content-Length", String.valueOf(file.getSize()));
		
		//store the image in s3 bucket AND update database (userProfileImgLink) with s3 image link
		String path = String.format("%s/%s", Bucket.PROFILE_IMAGE.getBucket(), user.getUserProfileId());
		
		String fileName = String.format("%s-%s", file.getName(), UUID.randomUUID());
		
		try {
//			user.setUserProfileImgLink(fileName);
			fileStore.save( path , fileName, Optional.of(metadata), file.getInputStream());
			user.setUserProfileImgLink(fileName);
			return getUserProfiles();
		}
		catch(IOException e) {
			throw new IllegalStateException(e);
		}

		
	}//end uploadUserProfileImg()
	
	
	public byte[] downloadUserProfileImgWithLink(UUID userProfileId, String userProfileImgLink) {
			
UserProfile user = getUserProfileIdOrThrow(userProfileId);
		
		String path = String.format("%s/%s",
                Bucket.PROFILE_IMAGE.getBucket(),
                user.getUserProfileId());
		
		return user.getUserProfileImgLink().
				map(key -> fileStore.download(path, key))
				.orElse(new byte[0]);
		
	}
	
	//returns one userProfile 
	private UserProfile getUserProfileIdOrThrow(UUID userProfileId) {
		
		return userProfileDataAccessService.
				getUserProfiles().
				stream().
				filter( userProfile -> userProfile.getUserProfileId().equals(userProfileId)).
				findFirst().
				orElseThrow(() -> new IllegalStateException(String.format( "user profile %s not found ", userProfileId )));
	}//getUserProfileIdOrThrow
	
	

}//end UserProfileService
