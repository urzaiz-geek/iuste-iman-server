package com.urzaizcoding.iusteimanserver.controller;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.urzaizcoding.iusteimanserver.dto.StudentDTO;
import com.urzaizcoding.iusteimanserver.dto.StudentDTOLight;
import com.urzaizcoding.iusteimanserver.mappers.MapStructMapper;
import com.urzaizcoding.iusteimanserver.service.StudentService;

@RestController
@CrossOrigin("*")
@RequestMapping(path = "/students", produces = {MediaType.APPLICATION_JSON_VALUE})
public class StudentController {
	
	
	private final MapStructMapper mapper;
	
	private final StudentService studentService;
	
	
	
	public StudentController(MapStructMapper mapper, StudentService studentService) {
		super();
		this.mapper = mapper;
		this.studentService = studentService;
	}


	@GetMapping
	public ResponseEntity<List<StudentDTOLight>> getStudents(){
		
		return ResponseEntity.ok(null);
	}
	
	
	@PatchMapping(
			path = {"{studentId}/photo"},
			consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}
	)
	public ResponseEntity<StudentDTO> updateStudentPhoto(@NotNull @PathVariable Long studentId, @RequestParam("file") MultipartFile file) throws Exception {
		
		StudentDTO studentResource = mapper.studentToStudentDTO(studentService.updateStudentPhoto(studentId,file));
		return ResponseEntity.ok(studentResource);
	}

}
