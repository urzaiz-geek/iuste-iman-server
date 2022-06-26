package com.urzaizcoding.iusteimanserver.service;

import com.urzaizcoding.iusteimanserver.domain.registration.student.Student;

public interface MailSenderService {
	
	void sendRegistrationEmail(Student student) throws Exception;

}
