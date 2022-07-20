package com.urzaizcoding.iusteimanserver.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.urzaizcoding.iusteimanserver.domain.registration.course.Course;
import com.urzaizcoding.iusteimanserver.domain.registration.student.Student;
import com.urzaizcoding.iusteimanserver.dto.CourseDTO;
import com.urzaizcoding.iusteimanserver.dto.CourseDTOLight;
import com.urzaizcoding.iusteimanserver.dto.FolderDTOEmbedded;
import com.urzaizcoding.iusteimanserver.dto.StudentDTO;
import com.urzaizcoding.iusteimanserver.mappers.MapStructMapper;
import com.urzaizcoding.iusteimanserver.service.CourseService;

@RestController
@RequestMapping(path = "/courses", produces = { MediaType.APPLICATION_JSON_VALUE })
@CrossOrigin("*")
public class CourseController {

	private final CourseService courseService;

	private final MapStructMapper mapper;

	public CourseController(CourseService courseService, MapStructMapper mapper) {
		super();
		this.courseService = courseService;
		this.mapper = mapper;
	}

	@GetMapping
	public ResponseEntity<Page<CourseDTOLight>> getAllCourses(@RequestParam(required = false) Integer page,
			@RequestParam(required = false) Integer size) {

		return ResponseEntity.status(HttpStatus.OK)
				.body(courseService.getAllCourses(page, size).map(course -> mapper.courseToCourseDTOLigth(course)));
	}

	@GetMapping(path = "{id}")
	public ResponseEntity<CourseDTO> getSpecifiedCourse(@PathVariable @NotNull Long id) throws Exception {
		Course courseEntity = courseService.findSpecifiedCourse(id);
		return ResponseEntity.ok(mapper.courseToCourseDTO(courseEntity));
	}

	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<CourseDTO> createCourse(@Valid @RequestBody CourseDTO courseResource,
			UriComponentsBuilder uriComponentsBuilder) throws Exception {

		Course courseEntity = mapper.courseDTOToCourse(courseResource);
		courseEntity.setId(null);
		courseEntity = courseService.saveCourse(courseEntity);

		UriComponents uriComponent = uriComponentsBuilder.path("/courses/{id}").buildAndExpand(courseEntity.getId());

		return ResponseEntity.status(HttpStatus.CREATED).header("Location", uriComponent.toUriString())
				.body(mapper.courseToCourseDTO(courseEntity));
	}

	@PutMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, path = "{id}")
	public ResponseEntity<CourseDTO> editCourse(@PathVariable(name = "id") @NotNull Long courseId,
			@Valid @RequestBody CourseDTO courseResource) throws Exception {

		Course courseEntity = mapper.courseDTOToCourse(courseResource);

		courseEntity.setId(courseId);

		courseEntity = courseService.saveCourse(courseEntity);

		return ResponseEntity.status(HttpStatus.OK).body(mapper.courseToCourseDTO(courseEntity));

	}

	@DeleteMapping(path = "{id}")
	public ResponseEntity<Void> deleteCourse(@PathVariable Long id) throws Exception {

		courseService.deleteCourse(id);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, path = "{id}/registrations")
	public ResponseEntity<StudentDTO> subscribeToCourse(@Valid @RequestBody StudentDTO studentResource,
			@PathVariable(name = "id") Long courseId, UriComponentsBuilder uriComponentsBuilder) throws Exception {
		Student studentEntity = mapper.studentDTOToStudent(studentResource);

		if (studentEntity.getId() != null) {
			studentEntity.setId(null);
		}

		studentEntity = courseService.subscribeStudent(studentEntity, courseId);

		UriComponents uri = uriComponentsBuilder.path("/courses/{id}/registrations/{studId}").buildAndExpand(courseId,
				studentEntity.getId());

		return ResponseEntity.status(HttpStatus.CREATED).header("Location", uri.toUriString())
				.body(mapper.studentToStudentDTO(studentEntity));

	}

	@PutMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, path = "{courseId}/registrations/{studentId}")
	public ResponseEntity<StudentDTO> updateSubscription(@Valid @RequestBody StudentDTO studentResource,
			@PathVariable(name = "courseId") Long courseId, @PathVariable(name = "studentId") Long id)
			throws Exception {

		Student studentEntity = mapper.studentDTOToStudent(studentResource);

		studentEntity.setId(id);

		studentEntity = courseService.updateSubscription(studentEntity, courseId);

		return ResponseEntity.status(HttpStatus.OK).body(mapper.studentToStudentDTO(studentEntity));
	}

	@GetMapping(path = "{id}/folders")
	public ResponseEntity<Page<FolderDTOEmbedded>> getFoldersForCourse(
			@PathVariable(name = "id") @NotNull @NotBlank Long courseId, @RequestParam(required = false) Integer page,
			@RequestParam(required = false) Integer size) throws Exception {

		Page<FolderDTOEmbedded> folderResources = courseService.getFoldersOfCourse(courseId, page, size)
				.map(f -> mapper.folderToFolderDTOEmbedded(f));

		return ResponseEntity.ok(folderResources);
	}

	@PatchMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, path = { "{id}/status" })
	public ResponseEntity<CourseDTOLight> editCourseStatus(@NotNull @Valid @RequestBody CourseDTOLight courseResource,
			@NotNull @PathVariable(name = "id") Long courseId) {

		Course courseEntity = courseService.editCourseStatus(courseResource.getOpen(), courseId);

		return ResponseEntity.ok(mapper.courseToCourseDTOLigth(courseEntity));
	}
}
