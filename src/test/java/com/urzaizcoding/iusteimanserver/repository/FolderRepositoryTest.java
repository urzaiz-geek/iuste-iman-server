package com.urzaizcoding.iusteimanserver.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.urzaizcoding.iusteimanserver.domain.registration.Folder;


@DataJpaTest
class FolderRepositoryTest {

	@Autowired
	private FolderRepository underTest;
	
	@Autowired
	private TestEntityManager testEntityManager;
	
	
	@Test
	void shouldFindFolderByRegistrationName() throws Exception {
		//Given
		
		Folder folder = new Folder();
		
		folder.setFolderRegistrationNumber(Folder.generateNewNumber());
		
		folder.setCreationDate(LocalDateTime.of(2022, 7, 1, 7, 5));
		
		folder.setValidated(false);
		
		testEntityManager.persist(folder);
		
		//When
		
		final Optional<Folder> fetched = underTest.findByFolderRegistrationNumber(folder.getFolderRegistrationNumber());
		
		//Then
		
		assertThat(fetched).isPresent();
		assertThat(fetched.get().getId()).isNotNull();
		assertThat(fetched.get()).isEqualTo(folder);
	}
	
}
