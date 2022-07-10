package com.urzaizcoding.iusteimanserver.service;

import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import com.urzaizcoding.iusteimanserver.domain.registration.student.Student;

public interface StudentService {

	Student updateStudentPhoto(@NotNull Long studentId, MultipartFile file) throws Exception;

}
