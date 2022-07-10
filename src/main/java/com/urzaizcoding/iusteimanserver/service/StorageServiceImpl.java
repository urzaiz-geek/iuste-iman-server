package com.urzaizcoding.iusteimanserver.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import org.springframework.stereotype.Service;

import com.urzaizcoding.iusteimanserver.IusteimanServerApplication;

@Service
public class StorageServiceImpl implements StorageService {

	@Override
	public String saveFile(String path, FileSpec spec, InputStream stream) throws Exception {
		// TODO Auto-generated method stub
		return path + "/" + spec.fileName();
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
		};
	}

}
