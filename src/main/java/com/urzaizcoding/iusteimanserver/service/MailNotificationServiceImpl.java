package com.urzaizcoding.iusteimanserver.service;

import java.io.StringWriter;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.urzaizcoding.iusteimanserver.domain.Person;
import com.urzaizcoding.iusteimanserver.domain.Sex;
import com.urzaizcoding.iusteimanserver.domain.registration.student.Student;
import com.urzaizcoding.iusteimanserver.exception.MailNotificationFailureException;

import freemarker.template.Template;

@Service
public class MailNotificationServiceImpl implements MailNotificationService {

	private static final String SUBJECT_VERIFICATION = "Verification";
	private static final String OTP_MAIL_TEMPLATE_Name = "otpMailTemplate.ftl";
	private static final String STUDENT_FIELD = "student";
	private static final String PREFIX = "prefix";
	private static final String SUBSCRIPTION_ACCEPTED_TEMPLATE_NAME = "Candidature acceptée";
	private static final String ACCEPTANCE_MAIL_TEMPLATE_NAME = "acceptanceMailTemplate.ftl";
	private static final String REGISTRATION_MAIL_TEMPLATE_NAME = "registrationMailTemplate.ftl";
	private static final String SUBSCRIPTION_RECEIVED = "Candidature reçue";
	protected static final String INLINE_FORMAT = "inline-%s";

	private final JavaMailSender javaMailSender;
	private final FreeMarkerConfigurer freeMarkerConfiguration;

	public MailNotificationServiceImpl(JavaMailSender javaMailSender, FreeMarkerConfigurer freeMarkerConfiguration) {
		super();
		this.javaMailSender = javaMailSender;
		this.freeMarkerConfiguration = freeMarkerConfiguration;
	}

	@Override
	public void sendRegistrationEmail(Student student) throws MailNotificationFailureException {

		Map<String, String> data = emailData();

		data = composePersonalData(student, data);

		// add specific data

		data.put("folderRegistrationNumber", student.getFolder().getFolderRegistrationNumber());

		// inlines

		try {
			prepareAndSendEmail(REGISTRATION_MAIL_TEMPLATE_NAME, data, SUBSCRIPTION_RECEIVED, student.getEmail());

		} catch (Exception e) {
			throw new MailNotificationFailureException(
					"email sending failed due to reports to administrators to know more about this error", e);
		}

	}

	private Map<String, String> composePersonalData(Person person, Map<String, String> data) {
		if (data != null) {
			if (person != null) {
				data.put(PREFIX, person.getSex() == Sex.MALE ? "Monsieur" : "Madame");
				data.put(STUDENT_FIELD, String.format("%s %s", person.getFirstName(), person.getLastName()));
			}
		}
		return data;
	}

	private Map<String, String> emailData() {

		Map<String, String> data = new HashMap<>();
		data.put("year", String.valueOf(LocalDate.now().getYear()));
		return data;
	}

	@Override
	public void sendAcceptanceEmail(Student student) throws MailNotificationFailureException {

		Map<String, String> data = emailData();

		data = composePersonalData(student, data);

		try {
			prepareAndSendEmail(ACCEPTANCE_MAIL_TEMPLATE_NAME, data, SUBSCRIPTION_ACCEPTED_TEMPLATE_NAME,
					student.getEmail());

		} catch (Exception e) {
			throw new MailNotificationFailureException(
					"email sending failed due to reports to administrators to know more about this error", e);
		}
	}

	private void prepareAndSendEmail(String templateName, Map<String, String> data, String subject,
			String destinatorMail) throws Exception {

		String text = processTemplateToString(templateName, data);

		MimeMessagePreparator messagePreparator = mimeMessage -> {

			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

			mimeMessageHelper.setSubject(subject);
			mimeMessageHelper.setTo(destinatorMail);

			mimeMessageHelper.setText(text, true);
		};

		javaMailSender.send(messagePreparator);
	}

	private String processTemplateToString(String templatePath, Map<String, String> data) throws Exception {
		Template template = freeMarkerConfiguration.getConfiguration().getTemplate(templatePath);
		StringWriter writer = new StringWriter();
		template.process(data, writer);

		return writer.toString();
	}

	@Override
	public void sendOtpEmail(String otp, Person person) throws MailNotificationFailureException {
		Map<String, String> data = emailData();

		data = composePersonalData(person, data);

		try {
			prepareAndSendEmail(OTP_MAIL_TEMPLATE_Name, data, SUBJECT_VERIFICATION,
					person.getEmail());

		} catch (Exception e) {
			throw new MailNotificationFailureException(
					"email sending failed due to reports to administrators to know more about this error", e);
		}
	}

}
