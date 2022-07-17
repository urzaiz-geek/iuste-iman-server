package com.urzaizcoding.iusteimanserver.utils;

import java.io.Closeable;

import org.apache.pdfbox.pdmodel.PDDocument;

public interface UPDFWriter extends Closeable,AutoCloseable {
	
	PDDocument getDocument();
	void writeNormal(UWritingZone zone, boolean useMarker) throws Exception;
	void writeNormal(UWritingZone zone) throws Exception;
	void writeReset(UWritingZone zone) throws Exception;
	void setCurrentPage(int page) throws Exception;
	void resetMarker();
	void setMarker(UMarker marker);
	void flush() throws Exception;
	float getCurrentPageHeight();
	float getCurrentPageWidth();
}
