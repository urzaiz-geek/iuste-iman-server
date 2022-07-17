package com.urzaizcoding.iusteimanserver.utils;

import java.awt.Color;
import java.time.format.DateTimeFormatter;

import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import com.urzaizcoding.iusteimanserver.IusteimanServerApplication;
import com.urzaizcoding.iusteimanserver.domain.registration.Folder;
import com.urzaizcoding.iusteimanserver.domain.registration.Quitus;

public class UQuitusWriter extends FormDocumentWriter {
	private static final String TEMPLATES_TEMPLATE_QUITUS_IUSTE_PDF = "/templates/templateQuitus.pdf";
	private Quitus quitus;

	public UQuitusWriter(Folder folder, Quitus quitus) throws Exception {
		super(folder, IusteimanServerApplication.class.getResourceAsStream(TEMPLATES_TEMPLATE_QUITUS_IUSTE_PDF));
		this.quitus = quitus;
	}

	@Override
	public byte[] generateAndGet() throws Exception {

		writeYear(folder.getCourse().getYear());
		writeOtherInfos(folder, quitus);
		endPDF();
		return output.toByteArray();
	}

	private void writeYear(String year) throws Exception {
		UWritingZone zone = UWritingZoneImpl.builder().xOffset(362).yOffset(675).fontSize(11)
				.zoneContentType(ZoneContentType.TEXT).pdfont(PDType1Font.TIMES_ROMAN).color(Color.red)
				.textCase(Case.NOTHING).text(year).build();
		writer.writeNormal(zone);
	}

	private void writeOtherInfos(Folder folder, Quitus quitus) throws Exception {
		UWritingZone zone;
		PDFont font = PDType1Font.TIMES_ROMAN;
		Color color;

		// init nultiline

		writer.initMultiLineWriting(new UMarker(283, 586), 24);

		// folder registration number
		color = Color.blue;
		font = PDType1Font.TIMES_BOLD;
		zone = UWritingZoneImpl.builder().fontSize(12).zoneContentType(ZoneContentType.TEXT).pdfont(font).color(color)
				.textCase(Case.NOTHING).text(folder.getFolderRegistrationNumber()).build();
		writer.writeNextLine(zone);

		// quitus number
		color = new Color(7, 120, 37);
		zone = UWritingZoneImpl.builder().fontSize(12).zoneContentType(ZoneContentType.TEXT).pdfont(font).color(color)
				.textCase(Case.NOTHING).text(quitus.getQuitusNumber()).build();

		writer.writeNextLine(zone);

		// object
		color = Color.red;
		zone = UWritingZoneImpl.builder().fontSize(12).leading(26).zoneContentType(ZoneContentType.TEXT).pdfont(font).color(color)
				.textCase(Case.UPPER).text(quitus.getObject()).build();

		writer.writeNextLine(zone);

		// amount
		zone = UWritingZoneImpl.builder().fontSize(12).zoneContentType(ZoneContentType.TEXT).pdfont(font).color(color)
				.textCase(Case.NOTHING).text(quitus.getAmount().toString()).build();

		writer.writeNextLine(zone);

		// payment place
		zone = UWritingZoneImpl.builder().fontSize(11).zoneContentType(ZoneContentType.TEXT).pdfont(font).color(color)
				.textCase(Case.UPPER).text(quitus.getPaymentPlace()).build();

		writer.writeNextLine(zone);

		// student registration number
		color = new Color(7, 120, 37);
		zone = UWritingZoneImpl.builder().fontSize(12).zoneContentType(ZoneContentType.TEXT).pdfont(font).color(color)
				.textCase(Case.UPPER).text(folder.getStudent().getRegistrationId() == null ? ""
						: folder.getStudent().getRegistrationId())
				.build();

		writer.writeNextLine(zone);

		// student names
		color = new Color(50, 62, 79);
		zone = UWritingZoneImpl.builder().fontSize(12).zoneContentType(ZoneContentType.TEXT).pdfont(font).color(color)
				.textCase(Case.UPPER).text(folder.getStudent().getFirstName()).build();

		writer.writeNextLine(zone);

		// student lastNames
		zone = UWritingZoneImpl.builder().fontSize(12).zoneContentType(ZoneContentType.TEXT).pdfont(font).color(color)
				.textCase(Case.UPPER).text(folder.getStudent().getLastName()).build();

		writer.writeNextLine(zone);

		// student birth day and place
		String text = String.format("%s A %s",
				DateTimeFormatter.ofPattern("dd/MM/yyyy").format(folder.getStudent().getBirthDate()),
				folder.getStudent().getBirthPlace());
		
		font = PDType1Font.TIMES_BOLD_ITALIC;
		zone = UWritingZoneImpl.builder().fontSize(10.5f).zoneContentType(ZoneContentType.TEXT).pdfont(font).color(color)
				.textCase(Case.UPPER).text(text).build();

		writer.writeNextLine(zone);
		
		// student sex
		font = PDType1Font.TIMES_BOLD;
		zone = UWritingZoneImpl.builder().fontSize(11).zoneContentType(ZoneContentType.TEXT).pdfont(font).color(color)
				.textCase(Case.NOTHING).text(folder.getStudent().getSex().frenchValue()).build();

		writer.writeNextLine(zone);
		
		// student faculty
		color = Color.blue;
		zone = UWritingZoneImpl.builder().fontSize(10.5f).zoneContentType(ZoneContentType.TEXT).pdfont(font).color(color)
				.textCase(Case.UPPER).text(folder.getCourse().getFaculty()).build();

		writer.writeNextLine(zone);
		
		// student speciality
		zone = UWritingZoneImpl.builder().fontSize(10.5f).zoneContentType(ZoneContentType.TEXT).pdfont(font).color(color)
				.textCase(Case.UPPER).text(folder.getCourse().getSpeciality()).build();

		writer.writeNextLine(zone);
		
		// student cycle and level
		text = String.format("%s %d", folder.getCourse().getCycle(),folder.getCourse().getLevel());
		zone = UWritingZoneImpl.builder().fontSize(10.5f).zoneContentType(ZoneContentType.TEXT).pdfont(font).color(color)
				.textCase(Case.UPPER).text(text).build();

		writer.writeNextLine(zone);
		
		writer.endMultiLineWriting();
		
		
	}
}
