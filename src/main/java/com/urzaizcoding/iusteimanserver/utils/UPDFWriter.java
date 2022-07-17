package com.urzaizcoding.iusteimanserver.utils;

import java.io.Closeable;

import org.apache.pdfbox.pdmodel.PDDocument;

public interface UPDFWriter extends Closeable,AutoCloseable {
	
	PDDocument getDocument();
	void writeNormal(UWritingZone zone, boolean useMarker) throws Exception;
	void writeNormal(UWritingZone zone) throws Exception;
	void writeReset(UWritingZone zone) throws Exception;
	
	/**
	 * write at the current position if no offset is provided or at the zone described by the current position and the offset
	 * go to the next line based on writer leading this method does not reset the cursor position, the next call of this method
	 * will write at the next line  
	 * 
	 * @param zone
	 * @throws Exception
	 */
	void writeNextLine(UWritingZone zone) throws Exception;
	void setCurrentPage(int page) throws Exception;
	void initMultiLineWriting(UMarker startPosition, float leading) throws Exception;
	void endMultiLineWriting() throws Exception;
	void resetMarker();
	void setMarker(UMarker marker);
	void flush() throws Exception;
	float getCurrentPageHeight();
	float getCurrentPageWidth();
}
