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
	private float leading;
	
	static {
		//disable logging
		java.util.logging.Logger.getLogger("org.apache.pdfbox")
		.setLevel(java.util.logging.Level.OFF);
	}

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
		this(input, new FileOutputStream(new File(output)), mode);
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
		if (contentStream == null || zone == null)
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
		if (zone == null)
			return;
		marker.setX(marker.getX() + zone.getXOffset());
		marker.setY(marker.getY() + zone.getYOffset());
	}

	public void writeReset(UWritingZone zone) throws Exception {
		if (contentStream == null || zone == null)
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
		if (zone == null)
			return;
		// move the coursor
		if (zone.getZoneType() == ZoneContentType.TEXT) {
			if (useMarker) {
				contentStream.newLineAtOffset(zone.getXOffset() + marker.getX(), zone.getYOffset() + marker.getY());
			} else {
				if(zone.getXOffset() != 0.0 || zone.getYOffset() != 0.0)
					contentStream.newLineAtOffset(zone.getXOffset(), zone.getYOffset());
			}
			// configuring contentStream
			contentStream.setFont(zone.getFont(), zone.getFontSize());
			contentStream.setLeading(zone.getLeading() != 0? zone.getLeading():leading);
			contentStream.setNonStrokingColor(zone.getColor());

			String textToDisplay = null;

			// check if the zone has not Case properties

			if (zone.getTextCase() != Case.NOTHING) {
				switch (zone.getTextCase()) {
				case UPPER: {
					// uppercase
					textToDisplay = zone.getText().toUpperCase();
				}
					break;
				case LOWER: {
					// lowercase
					textToDisplay = zone.getText().toLowerCase();
				}
					break;

				case CAPITALIZE: {
					// capitalize
					textToDisplay = zone.getText().substring(0, 1).toUpperCase()
							.concat(zone.getText().substring(1).toLowerCase());
				}
					break;
				default:
					break;
				}
			} else {
				textToDisplay = zone.getText();
			}

			// check if the text is multiline
			if (zone.iMultiLined() && zone.getTextMaxLineLength() < zone.getText().length()) {
				contentStream.showText(textToDisplay.substring(0, zone.getTextMaxLineLength()) + "-");
				contentStream.newLine();
				contentStream.showText(textToDisplay.substring(zone.getTextMaxLineLength()));
			} else {
				contentStream.showText(textToDisplay);
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
			textEnded = true;
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

	@Override
	public void writeNextLine(UWritingZone zone) throws Exception {
		if (zone == null || zone.getZoneType() != ZoneContentType.TEXT)
			return;
		if (contentStream == null)
			throw new IllegalStateException(
					"The contentStream is null, the writer is not in the right state for this operation");
		
		writeZone(zone, false);
		contentStream.newLine();

	}

	@Override
	public void initMultiLineWriting(UMarker startPosition, float leading) throws Exception {
		if (startPosition == null || leading < 0)
			throw new IllegalArgumentException("Invalid arguments leading is negative or startPosition null");

		if (contentStream == null)
			throw new IllegalStateException(
					"The contentStrem is null the writer is not in the right state for this operation");

		if (!textEnded) {
			contentStream.endText();
		}

		contentStream.beginText();
		contentStream.newLineAtOffset(startPosition.getX(), startPosition.getY());
		this.leading = leading;

	}

	@Override
	public void endMultiLineWriting() throws Exception {
		if(contentStream != null) {
			contentStream.endText();
			textEnded = true;
		}
	}

}
