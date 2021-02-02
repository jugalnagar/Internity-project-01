package com.Internity.component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Component
public class FileUploadHelper {

	private final String UPLOAD_DIR = new ClassPathResource("static/images/").getFile().getAbsolutePath();
	//private final String UPLOAD_DIR = "C:\\Users\\Lenovo\\git\\repository5\\Java-Project-Internity\\src\\main\\resources\\static\\images";
	public FileUploadHelper() throws IOException {
		System.out.println(new ClassPathResource("static/images").getPath());
	}
	
	public String uploadFile(MultipartFile file) {
		String path = null;
		try {
			Files.copy(file.getInputStream(),Paths.get(UPLOAD_DIR+File.separator+file.getOriginalFilename()),StandardCopyOption.REPLACE_EXISTING);
			path = ServletUriComponentsBuilder.fromCurrentContextPath().path("/static/images/").path(file.getOriginalFilename()).toUriString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return path;
	}
}
