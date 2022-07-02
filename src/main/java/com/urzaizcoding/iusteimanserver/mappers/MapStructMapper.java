package com.urzaizcoding.iusteimanserver.mappers;

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
import com.urzaizcoding.iusteimanserver.dto.CourseDTOLigth;
import com.urzaizcoding.iusteimanserver.dto.FeesDTO;
import com.urzaizcoding.iusteimanserver.dto.FolderDTO;
import com.urzaizcoding.iusteimanserver.dto.FolderDTOLigth;
import com.urzaizcoding.iusteimanserver.dto.ParentDTO;
import com.urzaizcoding.iusteimanserver.dto.PartDTO;
import com.urzaizcoding.iusteimanserver.dto.StudentDTO;
import com.urzaizcoding.iusteimanserver.dto.StudentDTOLigth;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MapStructMapper {
	
	
	CourseDTO courseToCourseDTO(Course course);
	
	Course courseDTOToCourse(CourseDTO courseResource);
	
	CourseDTOLigth courseToCourseDTOLigth(Course course);
	
	StudentDTOLigth studentToStudentDTOLigth(Student student);
	
	ParentDTO parentToParentDTO(Parent parent);
	
	Parent parentDTOToParent(ParentDTO parentResource);
	
	@Mappings(
			@Mapping(target = "id", source = "student.id")
	)
	StudentDTO studentToStudentDTO(Student student);
	
	Student studentDTOToStudent(StudentDTO studentResource);
	
	@Mappings(
			@Mapping(target = "creationDate",source = "folder.creationDate",dateFormat = "dd-MM-yyyy HH:mm:ss")
	)
	FolderDTO folderToFolderDTO(Folder folder);
	
	FolderDTOLigth folderToFolderDTOLight(Folder folder);
	
	@Mappings(
			@Mapping(target = "folderId", source = "folder.id")
	)
	PartDTO partToPartDTO(Part part);
	
	Part partDTOToPart(PartDTO partResource);
	
	FeesDTO feesToFeesDTO(Fees fees);
}
