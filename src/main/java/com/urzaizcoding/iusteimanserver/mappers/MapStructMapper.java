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
import com.urzaizcoding.iusteimanserver.domain.registration.manager.Manager;
import com.urzaizcoding.iusteimanserver.domain.registration.student.Parent;
import com.urzaizcoding.iusteimanserver.domain.registration.student.Student;
import com.urzaizcoding.iusteimanserver.domain.user.Account;
import com.urzaizcoding.iusteimanserver.domain.user.Notification;
import com.urzaizcoding.iusteimanserver.dto.AccountDTO;
import com.urzaizcoding.iusteimanserver.dto.AccountDTOIn;
import com.urzaizcoding.iusteimanserver.dto.CourseDTO;
import com.urzaizcoding.iusteimanserver.dto.CourseDTOLight;
import com.urzaizcoding.iusteimanserver.dto.FeesDTO;
import com.urzaizcoding.iusteimanserver.dto.FolderDTO;
import com.urzaizcoding.iusteimanserver.dto.FolderDTOEmbedded;
import com.urzaizcoding.iusteimanserver.dto.FolderDTOLight;
import com.urzaizcoding.iusteimanserver.dto.ManagerDTO;
import com.urzaizcoding.iusteimanserver.dto.NotificationDTO;
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
	
	@Mappings({@Mapping(target="account",source="manager.account.id")})
	ManagerDTO managerToManagerDTO(Manager manager);
	
	@Mapping(target = "account",ignore = true)
	Manager managerDTOToManager(ManagerDTO managerResource);

	Account accountDTOToAccount(AccountDTO accountResource);

	@Mappings({ @Mapping(target = "creationDate", source = "account.creationDate", qualifiedBy = { UTCTimeMapper.class }),
			@Mapping(target = "lastConnectionDate", source = "account.lastConnectionDate", qualifiedBy = { UTCTimeMapper.class }) })
	AccountDTO accountToAccountDTO(Account account);

	Account accountDTOInToAccount(AccountDTOIn accountResource);
	
	NotificationDTO notificationToNotificationDTO(Notification notification);
	
	Parent parentDTOToParent(ParentDTO parentResource);

	@Mappings({ @Mapping(target = "id", source = "student.id"),
			@Mapping(target = "folderRegistrationNumber", source = "folder.folderRegistrationNumber") ,@Mapping(target="account",source="student.account.id")})
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

		if(dateTime != null) {
			return dateTime.atOffset(ZoneOffset.UTC).format(DateTimeFormatter.ISO_DATE_TIME);
		}
		
		return null;

	}
	
	
}
