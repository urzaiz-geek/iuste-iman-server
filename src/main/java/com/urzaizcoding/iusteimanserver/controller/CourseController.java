package com.urzaizcoding.iusteimanserver.controller;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.urzaizcoding.iusteimanserver.domain.registration.Folder;
import com.urzaizcoding.iusteimanserver.domain.registration.course.Course;
import com.urzaizcoding.iusteimanserver.domain.registration.student.Student;
import com.urzaizcoding.iusteimanserver.dto.CourseDTO;
import com.urzaizcoding.iusteimanserver.dto.FolderDTO;
import com.urzaizcoding.iusteimanserver.dto.StudentDTO;
import com.urzaizcoding.iusteimanserver.mappers.MapStructMapper;
import com.urzaizcoding.iusteimanserver.service.CourseService;

@RestController
@RequestMapping("/courses")
@CrossOrigin("*")
public class CourseController {

	private CourseService courseService;

	private ModelMapper mapper;
	
	@Autowired
	private MapStructMapper mapStructMapper;

	public CourseController(CourseService courseService, ModelMapper mapper) {
		super();
		this.courseService = courseService;
		this.mapper = mapper;
	}

	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<CourseDTO>> getAllCourses() {

		return ResponseEntity.status(HttpStatus.OK)
				.body(courseService.getAllCourses().stream()
						.map(course -> mapStructMapper.courseToCourseDTO(course))
						.collect(Collectors.toList()));
	}

	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<CourseDTO> createCourse(@Valid @RequestBody CourseDTO courseResource,
			UriComponentsBuilder uriComponentsBuilder) throws Exception {

		Course courseEntity = mapper.map(courseResource, Course.class);
		courseEntity.setId(null);
		courseEntity = courseService.saveCourse(courseEntity);

		UriComponents uriComponent = uriComponentsBuilder.path("/courses/{id}").buildAndExpand(courseEntity.getId());

		return ResponseEntity.status(HttpStatus.CREATED).header("Location", uriComponent.toUriString())
				.body(mapper.map(courseEntity, CourseDTO.CourseDTOBuilder.class).build());
	}

	@PutMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<CourseDTO> editCourse(@Valid @RequestBody CourseDTO courseResource) throws Exception {

		Course courseEntity = mapper.map(courseResource, Course.class);

		courseEntity = courseService.saveCourse(courseEntity);

		return ResponseEntity.status(HttpStatus.OK)
				.body(mapper.map(courseEntity, CourseDTO.CourseDTOBuilder.class).build());

	}

	@DeleteMapping(path = "{id}")
	public ResponseEntity<Void> deleteCourse(@PathVariable Long id) throws Exception {

		courseService.deleteCourse(id);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE }, path = "{id}/registrations")
	public ResponseEntity<StudentDTO> subscribeToCourse(@Valid @RequestBody StudentDTO studentResource,
			@PathVariable(name = "id") Long courseId, UriComponentsBuilder uriComponentsBuilder) throws Exception {
		Student studentEntity = mapper.map(studentResource, Student.class);

		if (studentEntity.getId() != null) {
			studentEntity.setId(null);
		}

		studentEntity = courseService.subscribeStudent(studentEntity, courseId);

		UriComponents uri = uriComponentsBuilder.path("/courses/{id}/registrations/{studId}").buildAndExpand(courseId,
				studentEntity.getId());

		return ResponseEntity.status(HttpStatus.CREATED).header("Location", uri.toUriString())
				.body(mapper.map(studentEntity, StudentDTO.StudentDTOBuilder.class).build());

	}

	@PutMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE }, path = "{id}/registrations")
	public ResponseEntity<StudentDTO> updateSubscription(@Valid @RequestBody StudentDTO studentResource,
			@PathVariable(name = "id") Long courseId) throws Exception {

		Student studentEntity = mapper.map(studentResource, Student.class);

		studentEntity = courseService.updateSubscription(studentEntity, courseId);

		return ResponseEntity.status(HttpStatus.OK)
				.body(mapper.map(studentEntity, StudentDTO.StudentDTOBuilder.class).build());
	}

	@GetMapping(produces = {
			MediaType.APPLICATION_JSON_VALUE }, path = "{id}/folders")
	public ResponseEntity<List<FolderDTO>> getFoldersForCourse(@PathVariable(name = "id") @NotNull @NotBlank Long courseId)
			throws Exception {

		Set<Folder> folders = courseService.getFoldersOfCourse(courseId);

		List<FolderDTO> folderResources = folders.stream()
				.map(f -> mapper.map(f, FolderDTO.FolderDTOBuilder.class).build()).collect(Collectors.toList());

		return ResponseEntity.ok(folderResources);
	}
}
