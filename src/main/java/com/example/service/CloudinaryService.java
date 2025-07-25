package com.example.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class CloudinaryService {

	
	private final Cloudinary cloudinary ;
	public CloudinaryService(
	        @Value("${cloudinary.cloud_name}") String cloudName,
	        @Value("${cloudinary.api_key}") String apiKey,
	        @Value("${cloudinary.api_secret}") String apiSecret
	    ) 
	{
        this.cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret
        ));
    }
	
	@Async
    public CompletableFuture<String> uploadFileAsync(MultipartFile file, String folder) {
        try {
            Map<String, Object> result = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap(
                    "resource_type", "auto",
                    "folder", folder
            ));
            return CompletableFuture.completedFuture(result.get("secure_url").toString());
        } catch (IOException e) {
            return CompletableFuture.failedFuture(e);
        }
    }
	

	public String uploadFile(MultipartFile file, String folder) throws IOException
{
        Map<String, Object> result = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap(
            "resource_type", "auto",
            "folder", folder
        ));
        return result.get("secure_url").toString();
    }

	
	
	
}
