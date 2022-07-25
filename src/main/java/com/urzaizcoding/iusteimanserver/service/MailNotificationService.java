package com.urzaizcoding.iusteimanserver.service;

import com.urzaizcoding.iusteimanserver.domain.Person;
import com.urzaizcoding.iusteimanserver.domain.registration.student.Student;
import com.urzaizcoding.iusteimanserver.exception.MailNotificationFailureException;

public interface MailNotificationService {
	
	void sendAcceptanceEmail(Student student) throws MailNotificationFailureException;
	void sendRegistrationEmail(Student student) throws MailNotificationFailureException;

	void sendOtpEmail(String otp, Person owner) throws MailNotificationFailureException;

}
