package com.urzaizcoding.iusteimanserver.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.urzaizcoding.iusteimanserver.domain.registration.Folder;

public interface FolderRepository extends JpaRepository<Folder, Long> {
	Optional<Folder> findByFolderRegistrationNumber(String folderRegistrationNumber);
}
