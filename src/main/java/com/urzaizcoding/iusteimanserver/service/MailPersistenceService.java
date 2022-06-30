package com.urzaizcoding.iusteimanserver.service;

import java.io.File;
import java.net.URI;

import javax.mail.internet.MimeMessage;

public interface MailPersistenceService {

	
	URI persit(MimeMessage message, File path) throws Exception;
	
	
}
