package com.urzaizcoding.iusteimanserver.mappers;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.urzaizcoding.iusteimanserver.domain.registration.Folder;
import com.urzaizcoding.iusteimanserver.domain.registration.course.Course;
import com.urzaizcoding.iusteimanserver.domain.registration.course.Fees;
import com.urzaizcoding.iusteimanserver.domain.registration.course.FeesType;
import com.urzaizcoding.iusteimanserver.domain.registration.student.Parent;
import com.urzaizcoding.iusteimanserver.domain.registration.student.ParentAttribute;
import com.urzaizcoding.iusteimanserver.dto.CourseDTO;
import com.urzaizcoding.iusteimanserver.dto.FeesDTO;
import com.urzaizcoding.iusteimanserver.dto.FolderDTO;
import com.urzaizcoding.iusteimanserver.dto.ParentDTO;

@SpringBootTest(webEnvironment = WebEnvironment.NONE, classes = { MapStructMapperImpl.class })
class MapStructMapperTest {

	@Autowired
	private MapStructMapper underTest;

	@Test
	void shouldConvertCourseDTOToCourse() throws Exception {
		// Given

		final CourseDTO courseResource = CourseDTO.builder().id(1L).cycle("Licence").faculty("Genie Informatique")
				.level(3).speciality("Genie Logiciel").year("2022/2023")
				.fees(Set.of(FeesDTO.builder().amount(25000).object("Inscription").type(FeesType.DEFAULT).build(),
						FeesDTO.builder().amount(250000).object("Inscription Premiere tranche")
								.type(FeesType.WHEN_ACCEPTED).build()))
				.build();

		// When

		final Course courseEntity = underTest.courseDTOToCourse(courseResource);

		// Then
		assertThat(courseEntity.getId()).isEqualTo(courseResource.getId());
		assertThat(courseEntity.getCycle()).isEqualTo(courseResource.getCycle());
		assertThat(courseEntity.getFaculty()).isEqualTo(courseResource.getFaculty());
		assertThat(courseEntity.getLevel()).isEqualTo(courseResource.getLevel());
		assertThat(courseEntity.getYear()).isEqualTo(courseResource.getYear());
		assertThat(courseEntity.getFees()).size().isEqualTo(2);

		assertThat(courseEntity.getFees().stream().allMatch(fees -> {
			return courseResource.getFees().stream().anyMatch(feesDto -> {
				return fees.getAmount().equals(feesDto.getAmount()) && fees.getObject().equals(feesDto.getObject())
						&& fees.getType().equals(feesDto.getType());
			});
		})).isTrue();
	}

	@Test
	void shouldConvertCourseToCourseDTO() throws Exception {
		// Given
		final Course courseEntity = Course.builder().id(1L).cycle("Licence").faculty("Genie Informatique").level(3)
				.speciality("Genie Logiciel").year("2022/2023")
				.fees(Set.of(Fees.builder().amount(25000).object("Inscription").type(FeesType.DEFAULT).build(),
						Fees.builder().amount(250000).object("Inscription Premiere tranche")
								.type(FeesType.WHEN_ACCEPTED).build()))
				.build();
		// When
		final CourseDTO courseResource = underTest.courseToCourseDTO(courseEntity);

		// Then
		assertThat(courseResource.getId()).isEqualTo(courseEntity.getId());
		assertThat(courseResource.getCycle()).isEqualTo(courseEntity.getCycle());
		assertThat(courseResource.getFaculty()).isEqualTo(courseEntity.getFaculty());
		assertThat(courseResource.getLevel()).isEqualTo(courseEntity.getLevel());
		assertThat(courseResource.getYear()).isEqualTo(courseEntity.getYear());
		assertThat(courseResource.getFees()).size().isEqualTo(2);

		assertThat(courseResource.getFees().stream().allMatch(feesDto -> {
			return courseEntity.getFees().stream().anyMatch(fees -> {
				return fees.getAmount().equals(feesDto.getAmount()) && fees.getObject().equals(feesDto.getObject())
						&& fees.getType().equals(feesDto.getType());
			});
		})).isTrue();
	}

	@Test
	void shouldConvertParentDTOToParent() throws Exception {
		// Given
		final ParentDTO parentResource = ParentDTO.builder().attribute(ParentAttribute.MOTHER).contact("699592077")
				.job("Emseignante").names("Tiofack Dongo Justine").place("Bafoussam").regionOfOrigin("OUEST").build();
		// When
		final Parent parentEntity = underTest.parentDTOToParent(parentResource);

		// Then

		assertThat(parentEntity.getAttribute()).isEqualTo(parentResource.getAttribute());
		assertThat(parentEntity.getContact()).isEqualTo(parentResource.getContact());
		assertThat(parentEntity.getJob()).isEqualTo(parentResource.getJob());
		assertThat(parentEntity.getNames()).isEqualTo(parentResource.getNames());
		assertThat(parentEntity.getPlace()).isEqualTo(parentResource.getPlace());
		assertThat(parentEntity.getRegionOfOrigin()).isEqualTo(parentResource.getRegionOfOrigin());
	}

	@Test
	void shouldConvertParentToParentDTO() throws Exception {
		// Given
		final Parent parentEntity = Parent.builder().attribute(ParentAttribute.MOTHER).contact("699592077")
				.job("Emseignante").names("Tiofack Dongo Justine").place("Bafoussam").regionOfOrigin("OUEST").build();
		// When
		final ParentDTO parentResource = underTest.parentToParentDTO(parentEntity);

		// Then

		assertThat(parentResource.getAttribute()).isEqualTo(parentEntity.getAttribute());
		assertThat(parentResource.getContact()).isEqualTo(parentEntity.getContact());
		assertThat(parentResource.getJob()).isEqualTo(parentEntity.getJob());
		assertThat(parentResource.getNames()).isEqualTo(parentEntity.getNames());
		assertThat(parentResource.getPlace()).isEqualTo(parentEntity.getPlace());
		assertThat(parentResource.getRegionOfOrigin()).isEqualTo(parentEntity.getRegionOfOrigin());
	}

	@Test
	void shouldConvertFeesDTOToFees() throws Exception {
		// Given

		final FeesDTO feesResource = FeesDTO.builder().amount(25000).object("Preinscription").type(FeesType.DEFAULT)
				.build();

		// When

		final Fees feesEntity = underTest.feesDTOToFees(feesResource);

		// Then

		assertThat(feesEntity.getObject()).isEqualTo(feesResource.getObject());
		assertThat(feesEntity.getAmount()).isEqualTo(feesResource.getAmount());
		assertThat(feesEntity.getType()).isEqualTo(feesResource.getType());
	}

	@Test
	void shouldConvertFolderDTOToFolder() throws Exception {
		// Given

		final FolderDTO folderResource = FolderDTO.builder()

				.build();

		// When
		final Folder folderEntity = underTest.folderDTOToFolder(folderResource);

		// Then

		assertThat(folderEntity.getCreationDate()).isEqualTo(LocalDateTime.parse(folderResource.getCreationDate(),
				DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
		assertThat(folderEntity.getDepositDate()).isEqualTo(folderResource.getCreationDate());
		assertThat(folderEntity.getFolderRegistrationNumber()).isEqualTo(folderResource.getFolderRegistrationNumber());
	}

	@Test
	void shouldConvertStudentDTOToStudent() throws Exception {
		// Given

		// When

		// Then
	}

}
