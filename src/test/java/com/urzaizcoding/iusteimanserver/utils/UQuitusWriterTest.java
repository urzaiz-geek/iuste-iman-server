package com.urzaizcoding.iusteimanserver.utils;

import java.time.LocalDate;
import java.util.Set;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.junit.jupiter.api.Test;

import com.urzaizcoding.iusteimanserver.domain.Sex;
import com.urzaizcoding.iusteimanserver.domain.registration.Folder;
import com.urzaizcoding.iusteimanserver.domain.registration.Quitus;
import com.urzaizcoding.iusteimanserver.domain.registration.course.Course;
import com.urzaizcoding.iusteimanserver.domain.registration.student.LanguageLevel;
import com.urzaizcoding.iusteimanserver.domain.registration.student.Level;
import com.urzaizcoding.iusteimanserver.domain.registration.student.Parent;
import com.urzaizcoding.iusteimanserver.domain.registration.student.ParentAttribute;
import com.urzaizcoding.iusteimanserver.domain.registration.student.Student;

class UQuitusWriterTest {

	@Test
	void test() throws Exception {
		Folder newFolder = new Folder();
		newFolder.setFolderRegistrationNumber("20220704-1032-10000");
		Quitus quitus = Quitus.builder().amount(15000).object("Frais de dossier").folder(newFolder)
				.paymentPlace("Etablissement").id(1L).build();
		newFolder.setQuitus(Set.of(quitus));

		newFolder.setCourse(Course.builder().cycle("Licence").faculty("Gestion")
				.speciality("Maintenance et après Vente automobile").level(3).year("2022/2023").build());
		newFolder.setStudent(Student.builder().firstName("GHENHAGNE GUEBOUSSI CHAVAQUIHA")
				.lastName("TRESOR ADIANIE VENCESLAS HOUHA").sex(Sex.MALE).country("CAMEROUNAISE")
				.birthDate(LocalDate.of(1998, 11, 13)).birthPlace("HOPITAL DE MAIRIE RURALE VERS KOTO")
				.quarter("BEPANDA CARREFOUR TENDON").email("naruffygolen@gmail.com").contact("690872959")
				.entranceDiploma("Baccalauréat ESG").diplomaOption("C")
				.schoolOfGraduation("Institut supérieur des Sciences, des Technologies et de l'Ethique")
				.frenchLevel(LanguageLevel.builder().writeLevel(Level.GOOD).readLevel(Level.MEDIUM)
						.speakLevel(Level.NOT_AT_ALL).comprehensionLevel(Level.LITTLE).build())
				.englishLevel(LanguageLevel.builder().writeLevel(Level.MEDIUM).readLevel(Level.GOOD)
						.speakLevel(Level.LITTLE).comprehensionLevel(Level.NOT_AT_ALL).build())
				.parents(Set.of(
						Parent.builder().names("GUEBOUSSI TEFOSSA EMMANUEL CHAVAQUIHA")
								.attribute(ParentAttribute.FATHER).contact("670889114").regionOfOrigin("OUEST")
								.job("RESPONSABLE DE LA CONSOLIDATION").build(),
						Parent.builder().names("TIOFACK DONGO JUSTINE").attribute(ParentAttribute.MOTHER)
								.contact("699592077").regionOfOrigin("OUEST").job("ENSEIGNANTE").build(),
						Parent.builder().names("DONGO GUEBOUSSI GWLADYS SIDOINE").attribute(ParentAttribute.TUTOR)
								.contact("670755261").regionOfOrigin("OUEST").job("ENSEIGNANTE DE LYCEE").build()))
				.build());
		
		UQuitusWriter uw = new UQuitusWriter(newFolder,quitus);

		PDDocument doc = PDDocument.load(uw.generateAndGet());
		doc.save("test.pdf");
		doc.close();
	}

}
