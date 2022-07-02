package com.urzaizcoding.iusteimanserver.service;

import java.io.File;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.urzaizcoding.iusteimanserver.IusteimanServerApplication;
import com.urzaizcoding.iusteimanserver.domain.Sex;
import com.urzaizcoding.iusteimanserver.domain.registration.student.Student;
import com.urzaizcoding.iusteimanserver.exception.MailNotificationFailureException;

import freemarker.template.Template;

@Service
public class MailNotificationServiceImpl implements MailNotificationService {

	private static final String STATIC_LOGO_IUSTE_PNG_PATH = "/static/logo_IUSTE.png";
	private static final String REGISTRATION_MAIL_TEMPLATE_NAME = "registrationMailTemplate.ftl";
	private static final String SUBSCRIPTION_RECEIVED = "Candidature reçue";
	private static final String LOGO_CONTENT_ID = "iuste-logo";
	
	
	private final JavaMailSender javaMailSender;
	private final FreeMarkerConfigurer freeMarkerConfiguration;

	public MailNotificationServiceImpl(JavaMailSender javaMailSender, FreeMarkerConfigurer freeMarkerConfiguration) {
		super();
		this.javaMailSender = javaMailSender;
		this.freeMarkerConfiguration = freeMarkerConfiguration;
	}

	@Override
	public void sendRegistrationEmail(Student student) throws MailNotificationFailureException {

		Map<String, String> data = new HashMap<>();
		Map<String, File> inlines = new HashMap<>();

		// text in template

		data.put("prefix", student.getSex() == Sex.MALE ? "Monsieur" : "Madame");
		data.put("student", String.format("%s %s", student.getFirstName(), student.getLastName()));
		data.put("folderRegistrationNumber", student.getFolder().getFolderRegistrationNumber());
		data.put("contentLogoId", LOGO_CONTENT_ID);

		// inlines
		MimeMessagePreparator mimeMessagePreparator = null;
		try {
			inlines.put(LOGO_CONTENT_ID,
					new File(IusteimanServerApplication.class.getResource(STATIC_LOGO_IUSTE_PNG_PATH).toURI()));

			String text = processTemplateToString(REGISTRATION_MAIL_TEMPLATE_NAME, data);

			mimeMessagePreparator = prepareEmail(SUBSCRIPTION_RECEIVED, text, student.getEmail(),
					inlines);
		}catch (Exception e) {
			throw new MailNotificationFailureException("email sending failed due to reports to administrators to know more abour this error",e);
		}

		
		javaMailSender.send(mimeMessagePreparator);

	}

	@Override
	public void sendAcceptanceEmail(Student student) throws MailNotificationFailureException {

	}

	private MimeMessagePreparator prepareEmail(String subject, String text, String destinatorMail,
			Map<String, File> inlines) throws MessagingException {

		return mimeMessage -> {
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

			mimeMessageHelper.setSubject(subject);
			mimeMessageHelper.setTo(destinatorMail);

			mimeMessageHelper.setText(text, true);

			if(inlines != null) {
				for (String id : inlines.keySet()) {
					mimeMessageHelper.addInline(id, inlines.get(id));
				}
			}
		};
	}

	private String processTemplateToString(String templatePath, Map<String, String> data) throws Exception {
		Template template = freeMarkerConfiguration.getConfiguration().getTemplate(templatePath);
		StringWriter writer = new StringWriter();
		template.process(data, writer);

		return writer.toString();
	}

}
