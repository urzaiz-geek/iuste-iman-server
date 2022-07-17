package com.urzaizcoding.iusteimanserver.mappers;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

import com.urzaizcoding.iusteimanserver.domain.registration.Folder;
import com.urzaizcoding.iusteimanserver.domain.registration.Part;
import com.urzaizcoding.iusteimanserver.domain.registration.course.Course;
import com.urzaizcoding.iusteimanserver.domain.registration.course.Fees;
import com.urzaizcoding.iusteimanserver.domain.registration.student.Parent;
import com.urzaizcoding.iusteimanserver.domain.registration.student.Student;
import com.urzaizcoding.iusteimanserver.dto.CourseDTO;
import com.urzaizcoding.iusteimanserver.dto.CourseDTOLight;
import com.urzaizcoding.iusteimanserver.dto.FeesDTO;
import com.urzaizcoding.iusteimanserver.dto.FolderDTO;
import com.urzaizcoding.iusteimanserver.dto.FolderDTOEmbedded;
import com.urzaizcoding.iusteimanserver.dto.FolderDTOLight;
import com.urzaizcoding.iusteimanserver.dto.ParentDTO;
import com.urzaizcoding.iusteimanserver.dto.PartDTO;
import com.urzaizcoding.iusteimanserver.dto.StudentDTO;
import com.urzaizcoding.iusteimanserver.dto.StudentDTOLight;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MapStructMapper {

	CourseDTO courseToCourseDTO(Course course);

	Course courseDTOToCourse(CourseDTO courseResource);

	CourseDTOLight courseToCourseDTOLigth(Course course);

	StudentDTOLight studentToStudentDTOLigth(Student student);

	ParentDTO parentToParentDTO(Parent parent);

	Parent parentDTOToParent(ParentDTO parentResource);

	@Mappings({@Mapping(target = "id", source = "student.id"),
			@Mapping(target = "folderRegistrationNumber", source = "folder.folderRegistrationNumber")}
			)
	StudentDTO studentToStudentDTO(Student student);

	Student studentDTOToStudent(StudentDTO studentResource);

	@Mappings(@Mapping(target = "creationDate", source = "folder.creationDate", qualifiedBy = { UTCTimeMapper.class }))
	FolderDTO folderToFolderDTO(Folder folder);

	@Mappings(@Mapping(target = "creationDate", source = "folder.creationDate", qualifiedBy = { UTCTimeMapper.class }))
	FolderDTOLight folderToFolderDTOLight(Folder folder);
	
	FolderDTOEmbedded folderToFolderDTOEmbedded(Folder folder);

	@Mappings({ @Mapping(target = "folderId", source = "folder.id"),
			@Mapping(target = "uploadDate", source = "part.uploadDate", qualifiedBy = { UTCTimeMapper.class }) })
	PartDTO partToPartDTO(Part part);

	Part partDTOToPart(PartDTO partResource);

	FeesDTO feesToFeesDTO(Fees fees);

	@UTCTimeMapper
	default String toUtCString(LocalDateTime dateTime) {

		return dateTime.atOffset(ZoneOffset.UTC).format(DateTimeFormatter.ISO_DATE_TIME);

	}
}
