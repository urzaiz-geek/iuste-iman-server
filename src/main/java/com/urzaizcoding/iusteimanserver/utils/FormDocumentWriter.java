package com.urzaizcoding.iusteimanserver.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import com.urzaizcoding.iusteimanserver.domain.registration.Folder;

public abstract class FormDocumentWriter extends DocumentWriter {
	
	protected Folder folder;

	protected FormDocumentWriter(Folder folder, InputStream inputStream) throws Exception{
		if(folder == null || inputStream == null)
			throw new IllegalArgumentException("Provided null args for input stream or folder in FormDocumentConstructor args must be not null");
		
		this.output = new ByteArrayOutputStream();
		
		this.writer = new UPDFWriterImpl(inputStream, this.output, Mode.APPEND_TEXT);
		this.folder = folder;
		this.writer.setCurrentPage(0);
	}

}
