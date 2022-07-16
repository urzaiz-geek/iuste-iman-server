package com.urzaizcoding.iusteimanserver.service;

import org.springframework.stereotype.Service;

import com.urzaizcoding.iusteimanserver.domain.registration.Folder;
import com.urzaizcoding.iusteimanserver.utils.DocumentWriter;
import com.urzaizcoding.iusteimanserver.utils.UFormWriter;


@Service
public class PDFGeneratorServiceImpl implements PDFGeneratorService {
	private static final String DOCUMENT_CONTENT_TYPE = "Document/PDF";
	private static final String BASE_FORM_NAME = "%s_form";
	
	DocumentWriter writer;
	
	@Override
	public FileSpec generateForm(Folder folder) throws Exception {
		
		writer = new UFormWriter(folder);
		
		byte [] data = writer.generateAndGet();
		
		writer = null;
		
		return new FileSpec() {
			
			@Override
			public String fileType() {
				return DOCUMENT_CONTENT_TYPE;
			}
			
			@Override
			public Long fileSize() {
				return (long) data.length;
			}
			
			@Override
			public String fileName() {
				return String.format(BASE_FORM_NAME, folder.getStudent().getFirstName());
			}
			
			@Override
			public byte[] data() throws Exception {
				return data;
			}
		};
	}

}
