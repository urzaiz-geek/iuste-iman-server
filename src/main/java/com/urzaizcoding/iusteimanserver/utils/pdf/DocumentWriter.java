package com.urzaizcoding.iusteimanserver.utils.pdf;

import java.io.ByteArrayOutputStream;

public abstract class DocumentWriter {
	
	protected UPDFWriter writer;
	protected ByteArrayOutputStream output;
	
	public abstract byte[] generateAndGet() throws Exception;
	
	public UPDFWriter getWriter() {
		return writer;
	}
	
	public void endPDF() throws Exception {
		if(writer != null)
			writer.close();
	}
}
