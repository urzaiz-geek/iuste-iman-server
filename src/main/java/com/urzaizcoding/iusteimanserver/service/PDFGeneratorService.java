package com.urzaizcoding.iusteimanserver.service;

import com.urzaizcoding.iusteimanserver.domain.registration.Folder;

public interface PDFGeneratorService {

	FileSpec generateForm(Folder folder);

}
