package com.urzaizcoding.iusteimanserver.domain;

import java.util.Arrays;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.urzaizcoding.iusteimanserver.domain.registration.student.LanguageLevel;
import com.urzaizcoding.iusteimanserver.domain.registration.student.Level;
import com.urzaizcoding.iusteimanserver.domain.user.Role;


public class Converters {
	@Converter(autoApply = true)
	public static class MaritalStatusConverter implements AttributeConverter<MaritalStatus, String> {

		@Override
		public String convertToDatabaseColumn(MaritalStatus attribute) {
			return attribute.getCode();
		}

		@Override
		public MaritalStatus convertToEntityAttribute(String dbData) {
			return Arrays.stream(MaritalStatus.values()).filter(m -> m.getCode().equals(dbData)).findFirst()
					.orElseThrow(IllegalArgumentException::new);
		}

	}

	@Converter(autoApply = true)
	public static class SexConverter implements AttributeConverter<Sex, String> {

		@Override
		public String convertToDatabaseColumn(Sex attribute) {
			return attribute.getCode();
		}

		@Override
		public Sex convertToEntityAttribute(String dbData) {
			return Arrays.stream(Sex.values()).filter(m -> m.getCode().equals(dbData)).findFirst()
					.orElseThrow(IllegalArgumentException::new);
		}

	}

	@Converter(autoApply = true)
	public static class RoleConverter implements AttributeConverter<Role, String> {

		@Override
		public String convertToDatabaseColumn(Role attribute) {
			return attribute.getCode();
		}

		@Override
		public Role convertToEntityAttribute(String dbData) {
			return Arrays.stream(Role.values()).filter(m -> m.getCode().equals(dbData)).findFirst()
					.orElseThrow(IllegalArgumentException::new);
		}

	}
	
	@Converter(autoApply = true)
	public static class LanguageLevelConverter implements AttributeConverter<LanguageLevel, String> {

		@Override
		public String convertToDatabaseColumn(LanguageLevel attribute) {
			StringBuilder builder = new StringBuilder();
			builder.append(attribute.getReadLevel());
			builder.append(attribute.getWriteLevel());
			builder.append(attribute.getSpeakLevel());
			builder.append(attribute.getComprehensionLevel());
			
			return builder.toString();
		}

		@Override
		public LanguageLevel convertToEntityAttribute(String dbData) {
			
			return LanguageLevel.builder()
					.readLevel(Level.of(Character.toString(dbData.charAt(0))))
					.writeLevel(Level.of(Character.toString(dbData.charAt(1))))
					.speakLevel(Level.of(Character.toString(dbData.charAt(2))))
					.comprehensionLevel(Level.of(Character.toString(dbData.charAt(3))))
					.build();
		}
		
	}
}
