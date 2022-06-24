package com.urzaizcoding.iusteimanserver.domain.registration;

import java.io.InputStream;

public interface DocumentExportable {
	InputStream generatePDF() throws Exception;
}
