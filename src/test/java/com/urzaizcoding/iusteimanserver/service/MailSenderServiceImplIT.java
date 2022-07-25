package com.urzaizcoding.iusteimanserver.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.urzaizcoding.iusteimanserver.IusteImanApplicationTestContext;
import com.urzaizcoding.iusteimanserver.IusteimanServerApplication;
import com.urzaizcoding.iusteimanserver.domain.Sex;
import com.urzaizcoding.iusteimanserver.domain.registration.Folder;
import com.urzaizcoding.iusteimanserver.domain.registration.student.Student;

//@SpringBootTest(webEnvironment = WebEnvironment.NONE,classes = {IusteImanApplicationTestContext.class})
@SpringBootTest(classes = {IusteimanServerApplication.class})
@ActiveProfiles("test")
class MailSenderServiceImplIT {

	
	private MailNotificationService underTest;

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;

	@BeforeEach
	void setup() throws Exception {
		underTest = new MailNotificationServiceImpl(javaMailSender, freeMarkerConfigurer);
	}

	@ParameterizedTest(name = "sending email to {0}")
	@ValueSource(strings = { "naruffygolen@gmail.com", "gueboussitresor@yahoo.com", "urzaizgithub@outlook.com",
			"urzaizgithub@proton.me","urzaizgithub@aol.com" })
	void shouldSendAndEmailToAStudentWithGmail(String email) throws Exception{
		// given
		Folder folder = new Folder();
		folder.setFolderRegistrationNumber(Folder.generateNewNumber());
		Student student = Student.builder().firstName("Ghenhagne Gueboussi")
				.lastName("Trésor Chavaquiha")
				.sex(Sex.MALE)
				.email(email)
				.build();
		student.setFolder(folder);
		
		// when
		
		underTest.sendRegistrationEmail(student);
		
	}

}
