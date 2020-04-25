package com.codeants.reactify.bucket;

public enum Bucket {

	//i spent an hour not being able to connect to my S3 bucket because the bucket name had extra white space 
	PROFILE_IMAGE("javareactify");
	
	private final String bucket;
	
	Bucket(String bucket) {
		this.bucket = bucket;
	}
	
	public String getBucket() {
		return bucket;
	}
}
