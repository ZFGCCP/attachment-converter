package com.zfgc.AttachmentConverter.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.zfgc.AttachmentConverter.model.Smf1attachments;
import com.zfgc.AttachmentConverter.repository.Smf1attachmentsRepo;

import jakarta.annotation.PostConstruct;

@Component
public class Converter {

	@Autowired
	private Smf1attachmentsRepo repo;
	
	@Value("${smfFilesPath}")
	private String fileLocation;
	
	@Value("${writePath}")
	private String writeLocation;
	
	Logger logger = LoggerFactory.getLogger(Converter.class);
	
	@PostConstruct
	public void convert() throws IOException{
		
		Files.walk(Paths.get(fileLocation))
			 .filter(filePath -> !filePath.getFileName().toString().contains("avatar") && !filePath.getFileName().toString().equals("attachments") && ! filePath.getFileName().toString().equals(".gitignore"))
			 .forEach(filePath -> {
				 String fileName = filePath.getFileName().toString();
				 String fileHash = fileName.substring(fileName.indexOf("_") + 1);
				 
				 logger.info("converting file " + fileName);
				 
				 Smf1attachments attachment = repo.findByFileHashEquals(fileHash);
				 try {
					 if(attachment != null) {
						logger.info("found attachment in SMF db, writing to new file...");
						FileInputStream reader = new FileInputStream(filePath.toFile());
						FileOutputStream writer = new FileOutputStream(writeLocation + "\\" + attachment.getFilename());
						
						writer.write(reader.readAllBytes());
						
						writer.close();
						reader.close();
						logger.info("file written successfully");
					 }
					 else {
						 logger.error("attachment record not found in SMF for filehash: " + fileHash);
					 }
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				 
			 });
	}
}
