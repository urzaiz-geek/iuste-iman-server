package com.urzaizcoding.iusteimanserver.utils;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Set;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import com.urzaizcoding.iusteimanserver.IusteimanServerApplication;
import com.urzaizcoding.iusteimanserver.domain.Sex;
import com.urzaizcoding.iusteimanserver.domain.registration.Folder;
import com.urzaizcoding.iusteimanserver.domain.registration.course.Course;
import com.urzaizcoding.iusteimanserver.domain.registration.student.LanguageLevel;
import com.urzaizcoding.iusteimanserver.domain.registration.student.Level;
import com.urzaizcoding.iusteimanserver.domain.registration.student.Parent;
import com.urzaizcoding.iusteimanserver.domain.registration.student.ParentAttribute;
import com.urzaizcoding.iusteimanserver.domain.registration.student.Student;

public class UFormWriter {
	private static final String TEMPLATES_TEMPLATE_FORM_IUSTE_PDF = "/templates/templateFormIUSTE.pdf";
	private final UPDFWriter writer;
	private final ByteArrayOutputStream output;
	private final Folder folder;
	private final byte[] imageData;

	public UFormWriter(Folder folder, byte[] imageData) throws URISyntaxException, Exception {
		this.output = new ByteArrayOutputStream();
		writer = new UPDFWriterImpl(
				IusteimanServerApplication.class.getResourceAsStream(TEMPLATES_TEMPLATE_FORM_IUSTE_PDF), output,
				Mode.APPEND_TEXT);

		writer.setCurrentPage(0);

		if (folder == null) {
			throw new IllegalArgumentException("Caanot generate form cause the folder is null");
		}

		this.folder = folder;
		this.imageData = imageData;
	}

	public UPDFWriter getWriter() {
		return writer;
	}

	public void endPDf() throws IOException {
		writer.close();
	}

	public static void main(String[] args) throws Exception {

		Folder newFolder = new Folder();
		newFolder.setFolderRegistrationNumber("20220704-1032-10000");
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
		ByteArrayInputStream bis = new ByteArrayInputStream(
				IusteimanServerApplication.class.getResourceAsStream("/static/logo_IUSTE.png").readAllBytes());
		UFormWriter service = new UFormWriter(newFolder, bis.readAllBytes());

		PDDocument doc = PDDocument.load(service.generateAndGet());

		doc.save("test.pdf");

		// draw image

	}

	private void writeWhereComplement(String where) throws Exception {
		UWritingZone zone = null;

		Color color = new Color(85, 107, 47);
		PDFont font = PDType1Font.TIMES_BOLD;
		zone = UWritingZoneImpl.builder().zoneContentType(ZoneContentType.TEXT).text(where).isMultiline(false)
				.fontSize(10.5f).color(color).pdfont(font).xOffset(191).yOffset(270.5f).build();

		writer.resetMarker();
		writer.writeReset(zone);
	}

	private void writeFacultyDetails(String faculty, String speciality, String level, String cycle) throws Exception {
		UWritingZone zone = null;

		PDFont font = PDType1Font.TIMES_BOLD;
		Color color = new Color(85, 107, 47);

		// cycle
		zone = UWritingZoneImpl.builder().zoneContentType(ZoneContentType.TEXT).text(cycle).isMultiline(false)
				.fontSize(10.5f).color(color).pdfont(font).xOffset(95).yOffset(250.3f).build();

		writer.resetMarker();
		writer.writeReset(zone);

		// faculty
		zone = UWritingZoneImpl.builder().zoneContentType(ZoneContentType.TEXT).text(faculty).isMultiline(false)
				.fontSize(10.5f).color(color).pdfont(font).xOffset(145).yOffset(0).build();

		writer.writeNormal(zone, true);

		// level
		zone = UWritingZoneImpl.builder().zoneContentType(ZoneContentType.TEXT).text(level).isMultiline(false)
				.fontSize(10.5f).color(color).pdfont(font).xOffset(-30).yOffset(-15).build();

		writer.writeNormal(zone);

		// speciality
		zone = UWritingZoneImpl.builder().zoneContentType(ZoneContentType.TEXT).text(speciality).isMultiline(true)
				.textMaxLength(32).fontSize(10.5f).color(color).pdfont(font).xOffset(-237).leading(14).yOffset(1)
				.build();

		writer.writeNormal(zone);
	}

	private void writeDiploma(String diploma) throws Exception {
		UWritingZone zone = null;

		PDFont font = PDType1Font.TIMES_BOLD;
		Color color = new Color(85, 107, 47);

		// cycle
		zone = UWritingZoneImpl.builder().zoneContentType(ZoneContentType.TEXT).text(diploma).isMultiline(false)
				.fontSize(10.5f).color(color).pdfont(font).xOffset(2).yOffset(-44).build();

		writer.writeNormal(zone);
	}

	private void writeFatherNamesFunctionNumberRegion(String fatherNames, String function, String number, String region)
			throws Exception {
		UWritingZone zone = null;

		PDFont font = PDType1Font.TIMES_ROMAN;

		// FatherNames

		zone = UWritingZoneImpl.builder().zoneContentType(ZoneContentType.TEXT).text(fatherNames).isMultiline(true)
				.leading(14.5f).fontSize(10.5f).pdfont(font).textMaxLength(22).xOffset(82)
				.yOffset(writer.getCurrentPageHeight() * 0.5f + 24.1f).build();
		writer.resetMarker();
		writer.writeReset(zone);

		// Father function

		zone = UWritingZoneImpl.builder().zoneContentType(ZoneContentType.TEXT).text(function).isMultiline(true)
				.leading(14.5f).fontSize(9.5f).pdfont(font).textMaxLength(22).xOffset(77).yOffset(0).build();

		writer.writeNormal(zone, true);

		// Father number
		zone = UWritingZoneImpl.builder().zoneContentType(ZoneContentType.TEXT).text(number).isMultiline(false)
				.fontSize(10f).pdfont(font).xOffset(225).yOffset(-10).build();

		writer.writeReset(zone);

		// Father region

		zone = UWritingZoneImpl.builder().zoneContentType(ZoneContentType.TEXT).text(region).isMultiline(false)
				.fontSize(10f).pdfont(font).xOffset(42).yOffset(0).build();

		writer.writeNormal(zone, true);
	}

	private void writeMotherNamesFunctionNumberRegion(String motherNames, String function, String number, String region)
			throws Exception {
		UWritingZone zone = null;

		PDFont font = PDType1Font.TIMES_ROMAN;

		// MotherNames

		zone = UWritingZoneImpl.builder().zoneContentType(ZoneContentType.TEXT).text(motherNames).isMultiline(true)
				.leading(14.5f).fontSize(10.5f).pdfont(font).textMaxLength(21).xOffset(94)
				.yOffset(writer.getCurrentPageHeight() * 0.5f + -13f).build();
		writer.resetMarker();
		writer.writeReset(zone);

		// Mother function

		zone = UWritingZoneImpl.builder().zoneContentType(ZoneContentType.TEXT).text(function).isMultiline(true)
				.leading(14.5f).fontSize(9.5f).pdfont(font).textMaxLength(22).xOffset(71).yOffset(0).build();

		writer.writeNormal(zone, true);

		// Motherther number
		zone = UWritingZoneImpl.builder().zoneContentType(ZoneContentType.TEXT).text(number).isMultiline(false)
				.fontSize(10f).pdfont(font).xOffset(219).yOffset(-10).build();

		writer.writeReset(zone);

//		 Mother region

		zone = UWritingZoneImpl.builder().zoneContentType(ZoneContentType.TEXT).text(region).isMultiline(false)
				.fontSize(10f).pdfont(font).xOffset(42).yOffset(0).build();

		writer.writeNormal(zone, true);
	}

	private void writeTutorNamesFunctionNumberRegion(String tutorNames, String function, String number, String region)
			throws Exception {
		UWritingZone zone = null;

		PDFont font = PDType1Font.TIMES_ROMAN;

		// TutorNames

		zone = UWritingZoneImpl.builder().zoneContentType(ZoneContentType.TEXT).text(tutorNames).isMultiline(true)
				.leading(14.5f).fontSize(10.5f).pdfont(font).textMaxLength(21).xOffset(90)
				.yOffset(writer.getCurrentPageHeight() * 0.5f + -47f).build();
		writer.resetMarker();
		writer.writeReset(zone);

		// Tutor function

		zone = UWritingZoneImpl.builder().zoneContentType(ZoneContentType.TEXT).text(function).isMultiline(true)
				.leading(14.5f).fontSize(9.5f).pdfont(font).textMaxLength(22).xOffset(73).yOffset(0).build();

		writer.writeNormal(zone, true);

		// tutor number
		zone = UWritingZoneImpl.builder().zoneContentType(ZoneContentType.TEXT).text(number).isMultiline(false)
				.fontSize(10f).pdfont(font).xOffset(219).yOffset(-10).build();

		writer.writeReset(zone);

		// tutor region

		zone = UWritingZoneImpl.builder().zoneContentType(ZoneContentType.TEXT).text(region).isMultiline(false)
				.fontSize(10f).pdfont(font).xOffset(43).yOffset(0).build();

		writer.writeNormal(zone, true);
	}

	private void writeFrenchLevel(String read, String write, String speak, String comprehension) throws Exception {
		UWritingZone zone = null;

		PDFont font = PDType1Font.TIMES_ROMAN;

		// read
		zone = UWritingZoneImpl.builder().zoneContentType(ZoneContentType.TEXT).isMultiline(false).text(read)
				.fontSize(10f).pdfont(font).xOffset(125).yOffset(writer.getCurrentPageHeight() * 0.5f - 108).build();

		writer.resetMarker();
		writer.writeReset(zone);

		// write
		zone = UWritingZoneImpl.builder().zoneContentType(ZoneContentType.TEXT).isMultiline(false).text(write)
				.fontSize(10f).pdfont(font).xOffset(57).yOffset(0).build();

		writer.writeNormal(zone, true);

		// speak
		zone = UWritingZoneImpl.builder().zoneContentType(ZoneContentType.TEXT).isMultiline(false).text(speak)
				.fontSize(10f).pdfont(font).xOffset(115).yOffset(0).build();

		writer.writeNormal(zone);

		// speak
		zone = UWritingZoneImpl.builder().zoneContentType(ZoneContentType.TEXT).isMultiline(false).text(comprehension)
				.fontSize(10f).pdfont(font).xOffset(115).yOffset(0).build();

		writer.writeNormal(zone);
	}

	private void writeEnglishLevel(String read, String write, String speak, String comprehension) throws Exception {
		UWritingZone zone = null;

		PDFont font = PDType1Font.TIMES_ROMAN;

		// read
		zone = UWritingZoneImpl.builder().zoneContentType(ZoneContentType.TEXT).isMultiline(false).text(read)
				.fontSize(10f).pdfont(font).xOffset(125).yOffset(writer.getCurrentPageHeight() * 0.5f - 133).build();

		writer.resetMarker();
		writer.writeReset(zone);

		// write
		zone = UWritingZoneImpl.builder().zoneContentType(ZoneContentType.TEXT).isMultiline(false).text(write)
				.fontSize(10f).pdfont(font).xOffset(57).yOffset(0).build();

		writer.writeNormal(zone, true);

		// speak
		zone = UWritingZoneImpl.builder().zoneContentType(ZoneContentType.TEXT).isMultiline(false).text(speak)
				.fontSize(10f).pdfont(font).xOffset(115).yOffset(0).build();

		writer.writeNormal(zone);

		// speak
		zone = UWritingZoneImpl.builder().zoneContentType(ZoneContentType.TEXT).isMultiline(false).text(comprehension)
				.fontSize(10f).pdfont(font).xOffset(115).yOffset(0).build();

		writer.writeNormal(zone);
	}

	private void writeLastNamesSexCountryBirthPlace(String lastNames, String sex, String country, String birthPlace,
			String birthDate) throws Exception {

		UWritingZone zone = null;

		PDFont font = PDType1Font.TIMES_ROMAN;

		// LastNames

		zone = UWritingZoneImpl.builder().zoneContentType(ZoneContentType.TEXT).text(lastNames).isMultiline(true)
				.leading(14.5f).fontSize(11).pdfont(PDType1Font.TIMES_BOLD).textMaxLength(28).xOffset(298.5f).yOffset(0)
				.build();

		writer.writeReset(zone);

		// sex

		zone = UWritingZoneImpl.builder().zoneContentType(ZoneContentType.TEXT).text(sex).isMultiline(false)
				.fontSize(11).pdfont(font).xOffset(50.5f).yOffset(-25).build();

		writer.writeNormal(zone, true);

		// Country

		zone = UWritingZoneImpl.builder().zoneContentType(ZoneContentType.TEXT).text(country).isMultiline(false)
				.fontSize(11).pdfont(font).xOffset(-168).yOffset(0).build();

		writer.writeNormal(zone);

		// BirthPLace

		zone = UWritingZoneImpl.builder().zoneContentType(ZoneContentType.TEXT).text(birthPlace).isMultiline(false)
				.fontSize(10.1f).pdfont(font).xOffset(-220).yOffset(0).build();

		writer.writeNormal(zone);

		// BirthPLace

		zone = UWritingZoneImpl.builder().zoneContentType(ZoneContentType.TEXT).text(birthDate).isMultiline(false)
				.fontSize(10.1f).pdfont(font).xOffset(-70).yOffset(0).build();

		writer.writeNormal(zone);

	}

	private void writePhoneEmailQuarter(String phone, String email, String quarter) throws Exception {
		UWritingZone zone = null;

		PDFont font = PDType1Font.TIMES_ROMAN;

		// LastNames

		zone = UWritingZoneImpl.builder().zoneContentType(ZoneContentType.TEXT).text(phone).fontSize(10.5f).pdfont(font)
				.textMaxLength(28).xOffset(0).yOffset(-37).build();

		writer.writeNormal(zone);

		// Email

		zone = UWritingZoneImpl.builder().zoneContentType(ZoneContentType.TEXT).text(email).fontSize(10.5f).pdfont(font)
				.textMaxLength(28).xOffset(160).yOffset(0).build();

		writer.writeNormal(zone);

		// Quarter

		zone = UWritingZoneImpl.builder().zoneContentType(ZoneContentType.TEXT).text(quarter).fontSize(9f).pdfont(font)
				.textMaxLength(28).xOffset(205).yOffset(-24).build();

		writer.writeReset(zone);
	}

	private void drawStudentPhoto(final PDImageXObject image) throws Exception {
		ImageZone imageObject = new ImageZone(image, 115.7f, 115.7f);

		UWritingZone imageZone = UWritingZoneImpl.builder().zoneContentType(ZoneContentType.IMAGE)
				.pdImageZone(imageObject).xOffset(14.3f).yOffset(writer.getCurrentPageHeight() - 172.8f).build();
		writer.writeReset(imageZone);
	}

	private void writeYearNumberNames(String year, String folderNumber, String names) throws Exception {
		UWritingZone zone = null;
		PDFont font = PDType1Font.TIMES_ROMAN;

		// Year
		zone = UWritingZoneImpl.builder().zoneContentType(ZoneContentType.TEXT).color(Color.RED).text(year)
				.isMultiline(false).pdfont(font).fontSize(10).xOffset(writer.getCurrentPageWidth() * 0.5f + 30)
				.yOffset(writer.getCurrentPageHeight() - 158.3f).build();
		writer.writeNormal(zone);

		// Number

		zone = UWritingZoneImpl.builder().zoneContentType(ZoneContentType.TEXT).text(folderNumber).pdfont(font)
				.fontSize(10).isMultiline(false).xOffset(-17).yOffset(-33.5f).build();
		writer.writeNormal(zone);

		// Names
		zone = UWritingZoneImpl.builder().zoneContentType(ZoneContentType.TEXT).text(names)
				.pdfont(PDType1Font.TIMES_BOLD).color(null).fontSize(11).textMaxLength(28).isMultiline(true)
				.leading(14.5f).xOffset(-234).yOffset(-18).build();
		writer.writeNormal(zone);
	}

	public byte[] generateAndGet() throws Exception {
		if(imageData != null) {
			PDImageXObject imageObject = PDImageXObject.createFromByteArray(writer.getDocument(), imageData,
					folder.getStudent().getPhotoPath());
			drawStudentPhoto(imageObject);
		}
		

		Course course = folder.getCourse();
		Student student = folder.getStudent();

		writeYearNumberNames(course.getYear(), folder.getFolderRegistrationNumber(), student.getFirstName());
		writeLastNamesSexCountryBirthPlace(student.getLastName(), student.getSex().frenchValue(), student.getCountry(),
				student.getBirthPlace(), DateTimeFormatter.ofPattern("dd/MM/yyyy").format(student.getBirthDate()));

		writePhoneEmailQuarter(student.getContact(), student.getEmail(), student.getQuarter());

		Iterator<Parent> iterator = student.getParents().iterator();

		while (iterator.hasNext()) {
			Parent item = iterator.next();

			if (item.getAttribute() == ParentAttribute.FATHER) {
				writeFatherNamesFunctionNumberRegion(item.getNames(), item.getJob(), item.getContact(),
						item.getRegionOfOrigin());
			} else if (item.getAttribute() == ParentAttribute.MOTHER) {
				writeMotherNamesFunctionNumberRegion(item.getNames(), item.getJob(), item.getContact(),
						item.getRegionOfOrigin());
			} else {
				writeTutorNamesFunctionNumberRegion(item.getNames(), item.getJob(), item.getContact(),
						item.getRegionOfOrigin());
			}
		}

		writeFrenchLevel(student.getFrenchLevel().getReadLevel().frenchValue(),
				student.getFrenchLevel().getWriteLevel().frenchValue(),
				student.getFrenchLevel().getSpeakLevel().frenchValue(),
				student.getFrenchLevel().getComprehensionLevel().frenchValue());

		writeEnglishLevel(student.getEnglishLevel().getReadLevel().frenchValue(),
				student.getEnglishLevel().getWriteLevel().frenchValue(),
				student.getEnglishLevel().getSpeakLevel().frenchValue(),
				student.getEnglishLevel().getComprehensionLevel().frenchValue());

		writeWhereComplement(student.getSchoolOfGraduation());
		writeFacultyDetails(course.getFaculty(), course.getSpeciality(), course.getLevel().toString(), course.getCycle());
		writeDiploma(String.format("%s %s", student.getEntranceDiploma(),student.getDiplomaOption()));
		endPDf();

		return output.toByteArray();
	}
}
