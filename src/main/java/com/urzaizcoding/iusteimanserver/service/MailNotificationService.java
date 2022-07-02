package com.urzaizcoding.iusteimanserver.service;

import com.urzaizcoding.iusteimanserver.domain.registration.student.Student;
import com.urzaizcoding.iusteimanserver.exception.MailNotificationFailureException;

public interface MailNotificationService {
	
	void sendRegistrationEmail(Student student) throws MailNotificationFailureException;

	void sendAcceptanceEmail(Student student) throws MailNotificationFailureException;

}
