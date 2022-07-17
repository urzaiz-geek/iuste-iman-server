package com.urzaizcoding.iusteimanserver.service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.urzaizcoding.iusteimanserver.domain.registration.student.Student;
import com.urzaizcoding.iusteimanserver.exception.ResourceNotFoundException;
import com.urzaizcoding.iusteimanserver.repository.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService {

	private Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

	private final StudentRepository studentRepository;

	private final ImageStorageService storageService;

	public StudentServiceImpl(StudentRepository studentRepository, ImageStorageService storageService) {
		super();
		this.studentRepository = studentRepository;
		this.storageService = storageService;
	}

	@Override
	public Student updateStudentPhoto(@NotNull Long studentId, MultipartFile file) throws Exception {
		Student studentEntity = studentRepository.findById(studentId).orElseThrow(() -> new ResourceNotFoundException(
				String.format("Student identified by id : %d does not exists in the system", studentId)));

		String fileName = String.format(Student.PICTURE_NAME_FORMAT, studentId,
				LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddhhmmss")));

		String path = String.format(Student.BASE_PICTURE_PATH_FORMAT,
				LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE));

		FileSpec fileSpec;

		fileSpec = new FileSpec() {

			@Override
			public Long fileSize() {
				return file.getSize();
			}

			@Override
			public String fileName() {
				return String.format("%s", fileName);
			}

			@Override
			public byte[] data() throws IOException {
				return file.getBytes();
			}

			@Override
			public String fileType() {
				return null;
			}
		};
		System.out.println("Holla");

		// We have to delete the previous picture
		if (studentEntity.getPhotoPath() != null) {
			if (!storageService.deleteFile(studentEntity.getPhotoPublicId())) {
				logger.error("failed to delete the former resource student pp at public id : " + studentEntity.getPhotoPublicId());
			}
		}

		String newPath = storageService.saveFile(path, fileSpec);

		//we will use it to delete the photo later
		studentEntity.setPhotoPublicId(
				String.format("%s%s",path.substring(1), fileSpec.fileName()));
		studentEntity.setPhotoPath(newPath);
		
		return studentRepository.save(studentEntity);
	}

}
