package com.urzaizcoding.iusteimanserver.utils;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;

public class UPDFWriterImpl implements UPDFWriter {
	private PDPageContentStream contentStream;
	private PDDocument document;
	private OutputStream output;
	private Mode mode;
	private PDPage currentPage;
	private UMarker marker;
	private boolean textEnded = true;

	{
		marker = new UMarker();
	}

	private void init(OutputStream output, Mode mode) {

		if (output == null) {
			throw new IllegalArgumentException("The outputStream can not be null");
		}

		this.output = output;
		this.mode = mode;
	}

	public UPDFWriterImpl(File input, String output, Mode mode) throws Exception {
		this(new FileOutputStream(new File(output)), mode);
	}

	public UPDFWriterImpl(InputStream input, OutputStream output, Mode mode) throws Exception {
		init(output, mode);

		if (input != null) {
			this.document = PDDocument.load(input);
		}
	}
	
	public UPDFWriterImpl(InputStream input, String output, Mode mode) throws Exception {
		this(input,new FileOutputStream(new File(output)),mode);
	}

	public UPDFWriterImpl(String output, Mode mode) throws Exception {

		this(new FileOutputStream(new File(output)), mode);
	}

	public UPDFWriterImpl(OutputStream output, Mode mode) throws Exception {
		init(output, mode);
		this.document = new PDDocument();
		this.document.addPage(new PDPage());
	}

	public void writeNormal(UWritingZone zone, boolean useMarker) throws Exception {
		if (contentStream == null)
			return;
		if (textEnded && zone.getZoneType() == ZoneContentType.TEXT) {
			contentStream.beginText();
			textEnded = false;
		}

		updateMarker(zone); // update the marker for when whe will reset the contentStream
		writeZone(zone, useMarker);

	}

	public void writeNormal(UWritingZone zone) throws Exception {
		writeNormal(zone, false);
	}

	private void updateMarker(UWritingZone zone) {
		marker.setX(marker.getX() + zone.getXOffset());
		marker.setY(marker.getY() + zone.getYOffset());
		System.out.println(marker);
	}

	public void writeReset(UWritingZone zone) throws Exception {
		if (contentStream == null)
			return;

		if (zone.getZoneType() == ZoneContentType.TEXT) {
			if (!textEnded)
				contentStream.endText();

			contentStream.beginText();
			writeZone(zone, true);
			contentStream.endText();
			textEnded = true;
			updateMarker(zone);
		} else {
			writeZone(zone, true);
		}

	}

	private void writeZone(UWritingZone zone, boolean useMarker) throws Exception {
		System.out.println(zone);
		// move the coursor
		if (zone.getZoneType() == ZoneContentType.TEXT) {
			if (useMarker) {
				contentStream.newLineAtOffset(zone.getXOffset() + marker.getX(), zone.getYOffset() + marker.getY());
			} else {
				contentStream.newLineAtOffset(zone.getXOffset(), zone.getYOffset());
			}
			// configuring contentStream
			contentStream.setFont(zone.getFont(), zone.getFontSize());
			contentStream.setLeading(zone.getLeading());
			contentStream.setNonStrokingColor(zone.getColor());

			// check if the text is multiline
			if (zone.iMultiLined() && zone.getTextMaxLineLength() < zone.getText().length()) {
				contentStream.showText(zone.getText().substring(0, zone.getTextMaxLineLength()) + "-");
				contentStream.newLine();
				contentStream.showText(zone.getText().substring(zone.getTextMaxLineLength()));
			} else {
				contentStream.showText(zone.getText());
			}
		} else {
			contentStream.drawImage(zone.getImageObject().getImage(), zone.getXOffset(), zone.getYOffset(),
					zone.getImageObject().getWidth(), zone.getImageObject().getHeigth());
		}
	}

	public void setCurrentPage(int page) throws Exception {
		if (page < document.getNumberOfPages() && page >= 0) {
			currentPage = document.getPage(page);
		} else {
			if (mode == Mode.APPEND_PAGE || mode == Mode.APPEND_PAGE_AND_TEXT) {
				currentPage = new PDPage();
				document.addPage(currentPage);
			} else {
				throw new IllegalArgumentException("provided index for page is invalid");
			}
		}

		contentStream = (mode == Mode.APPEND_TEXT || mode == Mode.APPEND_PAGE_AND_TEXT)
				? new PDPageContentStream(document, currentPage, AppendMode.APPEND, true, false)
				: new PDPageContentStream(document, currentPage, AppendMode.OVERWRITE, false, false);
	}

	public void close() throws IOException {
		if (contentStream != null) {
			flush();
		}

		document.close();
		output.close();
	}

	public void flush() throws IOException {
		if (!textEnded) {
			contentStream.endText();
		}
		contentStream.close();
		document.save(output);
	}

	public PDDocument getDocument() {
		return document;
	}

	public float getCurrentPageHeight() {
		if (currentPage != null)
			return currentPage.getTrimBox().getHeight();
		return 0;
	}

	public float getCurrentPageWidth() {
		if (currentPage != null)
			return currentPage.getTrimBox().getWidth();
		return 0;
	}

	public void resetMarker() {
		marker.setX(0);
		marker.setY(0);
	}

	public void setMarker(UMarker marker) {
		this.marker = marker;
	}

}
