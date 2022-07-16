package com.urzaizcoding.iusteimanserver.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cloudinary.Cloudinary;
import com.urzaizcoding.iusteimanserver.IusteimanServerApplication;

@Service
public class ImageStorageServiceImpl implements ImageStorageService {

	Logger logger = LoggerFactory.getLogger(ImageStorageServiceImpl.class);
	
	private final Cloudinary cloudinary;
	
	
	public ImageStorageServiceImpl(Cloudinary cloudinary) {
		super();
		this.cloudinary = cloudinary;
	}

	@Override
	public String saveFile(String path, FileSpec spec) throws Exception {
		if(spec == null || path == null)
			return null;
		
		Map<String,String> options = new HashMap<>();
		
		options.put("folder",path);
		options.put("public_id", spec.fileName());
		options.put("resource_type", "image");
		
		@SuppressWarnings("rawtypes")
		Map saveResult = cloudinary.uploader().upload(spec.data(), options);
		return saveResult.get("url").toString();
	}

	@Override
	public FileSpec getFile(String archivePath) throws Exception{
		//This is dummy file just for test purpose
		
		File file = new File(IusteimanServerApplication.class.getResource("/static/logo_IUSTE.png").toURI());
		
		return new FileSpec() {
			
			@Override
			public Long fileSize() {
				return file.length();
			}
			
			@Override
			public String fileName() {
				// TODO Auto-generated method stub
				return file.getName();
			}
			
			@Override
			public byte[] data() {
				try {
					return Files.readAllBytes(file.toPath());
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				return null;
			}

			@Override
			public String fileType() {
				// TODO Auto-generated method stub
				return null;
			}
		};
	}

	@Override
	public boolean deleteFile(String path) throws Exception {
		if(path != null) {
			Map<String,String> options = new HashMap<>();
			options.put("resource_type", "image");
			
			@SuppressWarnings("rawtypes")
			Map result = cloudinary.uploader().destroy(path, options);
			
			return result.get("result").toString().equals("ok");
		}
		
		return false;
	}

}
