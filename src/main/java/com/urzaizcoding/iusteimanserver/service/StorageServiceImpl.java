package com.urzaizcoding.iusteimanserver.service;

import java.io.InputStream;

import org.springframework.stereotype.Service;

@Service
public class StorageServiceImpl implements StorageService {

	@Override
	public String saveFile(String path, FileSpec spec, InputStream stream) throws Exception {
		// TODO Auto-generated method stub
		return path + "/" + spec.fileName();
	}

}
