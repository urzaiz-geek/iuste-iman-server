package com.urzaizcoding.iusteimanserver.service;

import java.io.InputStream;

public interface StorageService {
	/**
	 * Save the stream of bytes as file described by spec
	 * @param path : path where the file should be saved
	 * @param spec : file metadata, with the data positionned to null as it will be ignored
	 * @param stream : stream to be saved
	 * @return the path if the saving operation succeed
	 */
	String saveFile(String path,FileSpec spec, InputStream stream) throws Exception;
}
