package com.urzaizcoding.iusteimanserver.service;

import java.io.File;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.urzaizcoding.iusteimanserver.IusteimanServerApplication;
import com.urzaizcoding.iusteimanserver.domain.Sex;
import com.urzaizcoding.iusteimanserver.domain.registration.student.Student;

import freemarker.template.Template;

public class MailSenderServiceImpl implements MailSenderService {

	private static final String SUBSCRIPTION_RECEIVED = "Candidature reçue";
	private static final String LOGO_CONTENT_ID = "iuste-logo";
	private final JavaMailSender javaMailSender;
	private final FreeMarkerConfigurer freeMarkerConfiguration;

	public MailSenderServiceImpl(JavaMailSender javaMailSender, FreeMarkerConfigurer freeMarkerConfiguration) {
		super();
		this.javaMailSender = javaMailSender;
		this.freeMarkerConfiguration = freeMarkerConfiguration;
	}

	@Override
	public void sendRegistrationEmail(Student student) throws Exception {

		MimeMessagePreparator mimeMessagePreparator = mimeMessage -> {
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);

			mimeMessageHelper.setSubject(SUBSCRIPTION_RECEIVED);
			mimeMessageHelper.setTo(student.getEmail());

			Map<String, String> data = new HashMap<>();

			data.put("prefix", student.getSex() == Sex.MALE ? "Monsieur" : "Madame");
			data.put("student", String.format("%s %s", student.getFirstName(), student.getLastName()));
			data.put("folderRegistrationNumber", student.getFolder().getFolderRegistrationNumber());
			data.put("contentLogoId", LOGO_CONTENT_ID);
			StringWriter writer = new StringWriter();

			Template template = freeMarkerConfiguration.getConfiguration().getTemplate("registrationMailTemplate.ftl");

			template.process(data, writer);

			String parsedEncodedTemplate = writer.toString();

			mimeMessageHelper.setText(parsedEncodedTemplate, true);
			mimeMessageHelper.addInline(LOGO_CONTENT_ID,
					new File(IusteimanServerApplication.class
							.getResource("/static/logo_IUSTE.png").toURI()));
		};
		
		
		
		javaMailSender.send(mimeMessagePreparator);

	}

}
