package com.Internity.component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import java.util.Base64;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.SocketUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.Internity.model.User;

@Component
public class FileUploadHelper {

	private final String UPLOAD_DIR = new ClassPathResource("static/images/").getFile().getAbsolutePath();
	public FileUploadHelper() throws IOException {}
	
	
	public String uploadEncodedImage(String encodedImage,String filename) {	
		String path = null;
		try {
			System.out.println("reading");
			//System.out.println(encodedImage);
			byte contains[] = Base64.getDecoder().decode(encodedImage);
			String directory = UPLOAD_DIR+File.separator+filename+".jpg";
			System.out.println("Start writing >>>>>>>>");
			new FileOutputStream(directory).write(contains);
			System.out.println("End Writing>>>>>>>>>>");
			path = ServletUriComponentsBuilder.fromCurrentContextPath().path("/images/").path(filename).toUriString();
		} catch (Exception e) {
			return path;
		}
		return path;
	}
	
	/*public String getFile(String filePath,String name) {
		String path = null;
		try { 
			path  = Base64.getEncoder().encodeToString(Files.readAllBytes(Paths.get(filePath)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return path;
	}
	public String uploadFile(MultipartFile file) {
		String path = null;
		try {
			Files.copy(file.getInputStream(),Paths.get(UPLOAD_DIR+File.separator+file.getOriginalFilename()),StandardCopyOption.REPLACE_EXISTING);
			path = ServletUriComponentsBuilder.fromCurrentContextPath().path("/images/").path(file.getOriginalFilename()).toUriString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return path;
	}*/
}

/*
byte imageContent[] = Base64.getDecoder().decode(fileData); 
String fileName = null;
try {
	fileName = name+"profile";
	FileUtils.writeByteArrayToFile(new File(UPLOAD_DIR+"/"+fileName+".jpg"), imageContent);
} catch (Exception e) {
	e.printStackTrace();
}
return ServletUriComponentsBuilder.fromCurrentContextPath().path("/images/").path(fileName).toUriString();
*/