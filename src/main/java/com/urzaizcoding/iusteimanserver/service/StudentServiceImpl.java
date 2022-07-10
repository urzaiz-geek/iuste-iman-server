package com.urzaizcoding.iusteimanserver.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.urzaizcoding.iusteimanserver.domain.registration.student.Student;
import com.urzaizcoding.iusteimanserver.exception.ResourceNotFoundException;
import com.urzaizcoding.iusteimanserver.repository.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService {

	private final StudentRepository studentRepository;

	private final StorageService storageService;

	public StudentServiceImpl(StudentRepository studentRepository, StorageService storageService) {
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
		
		String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
		
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
				return String.format("%s.%s", fileName, extension);
			}
			
			@Override
			public byte[] data() {
				return null;
			}
		};
		
		studentEntity.setPhotoPath(storageService.saveFile(path, fileSpec, file.getInputStream()));
		return studentRepository.save(studentEntity);
	}

}
