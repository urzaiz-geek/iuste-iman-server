package com.urzaizcoding.iusteimanserver.service;

import org.springframework.stereotype.Service;

import com.urzaizcoding.iusteimanserver.domain.registration.Folder;
import com.urzaizcoding.iusteimanserver.domain.registration.Quitus;
import com.urzaizcoding.iusteimanserver.exception.ResourceNotFoundException;
import com.urzaizcoding.iusteimanserver.utils.pdf.DocumentWriter;
import com.urzaizcoding.iusteimanserver.utils.pdf.UFormWriter;
import com.urzaizcoding.iusteimanserver.utils.pdf.UQuitusWriter;

@Service
public class PDFGeneratorServiceImpl implements PDFGeneratorService {
	private static final String DOCUMENT_CONTENT_TYPE = "Document/PDF";
	private static final String BASE_FORM_NAME = "%s_form";

	@Override
	public FileSpec generateForm(Folder folder) throws Exception {

		DocumentWriter writer = new UFormWriter(folder);
		return generateDocument(writer, DOCUMENT_CONTENT_TYPE,
				String.format(BASE_FORM_NAME, cleanName(folder.getStudent().getFirstName())));
	}

	@Override
	public FileSpec generateQuitus(Folder folder, Long quitusId) throws Exception {
		Quitus quitus =  folder.getQuitus().stream().filter(q -> q.getId() == quitusId).findFirst()
				.orElseThrow(() -> new ResourceNotFoundException(
						String.format("Quitus identified by id : %d is not present in this folder", quitusId)));
		
		DocumentWriter writer = new UQuitusWriter(folder, quitus);
		return generateDocument(writer, DOCUMENT_CONTENT_TYPE,
				String.format("%s_quitus_%d", cleanName(folder.getStudent().getFirstName()), quitusId));
	}

	private FileSpec generateDocument(DocumentWriter writer, String type, String name) throws Exception {

		byte[] data = writer.generateAndGet();

		return new FileSpec() {

			@Override
			public String fileType() {
				return type;
			}

			@Override
			public Long fileSize() {
				return (long) data.length;
			}

			@Override
			public String fileName() {
				return name;
			}

			@Override
			public byte[] data() throws Exception {
				return data;
			}
		};
	}
	
	private static String cleanName(String name) {
		return name.trim().strip().replace(" ", "_");
	}

}
