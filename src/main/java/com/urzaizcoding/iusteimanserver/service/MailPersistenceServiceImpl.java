package com.urzaizcoding.iusteimanserver.service;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URI;

import javax.mail.internet.MimeMessage;

public class MailPersistenceServiceImpl implements MailPersistenceService {

	@Override
	public URI persit(MimeMessage message, File path) throws Exception {
		
		if(!path.exists()) {
			path.createNewFile();
		}
		message.writeTo(new FileOutputStream(path));
		
		return path.toURI();
	}

}
