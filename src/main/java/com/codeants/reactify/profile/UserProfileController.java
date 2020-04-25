package com.codeants.reactify.profile;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/user-profile")
@CrossOrigin("*") // not good practice to allow accept from anywhere (should know what ports you want to access from)
public class UserProfileController {
	
	private final UserProfileService userProfileService;
	
	@Autowired
	public UserProfileController(UserProfileService userProfileService) {
		this.userProfileService = userProfileService;
	}
	
	
	
	//GET
	
	@GetMapping
	public List<UserProfile> getUserProfiles() {
		return userProfileService.getUserProfiles();
	}
	
	
	@GetMapping("{userProfileId}/image/download")
	public byte[] downloadUserProfileImg(@PathVariable("userProfileId") UUID userProfileId) {
	
		return userProfileService.downloadUserProfileImg(userProfileId);
		

	}//end downloadUserProfileImg
	
	
	
	
	
	
	//POST
	
	@PostMapping(
			
			path = "{userProfileId}/image/upload",
			consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public void uploadUserProfileImg(@PathVariable("userProfileId") UUID userProfileId,
										@RequestParam("file") MultipartFile file) {
		
		userProfileService.uploadUserProfileImg(userProfileId, file);
		
	}//end uploadUserProfileImg
	
}
