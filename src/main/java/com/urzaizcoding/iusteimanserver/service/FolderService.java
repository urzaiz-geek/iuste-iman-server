package com.urzaizcoding.iusteimanserver.service;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import com.urzaizcoding.iusteimanserver.domain.registration.Folder;
import com.urzaizcoding.iusteimanserver.domain.registration.Part;
import com.urzaizcoding.iusteimanserver.exception.ResourceNotFoundException;

public interface FolderService {

	List<Folder> findAllFolders();

	void deleteFolder(@NotNull @NotBlank String folderRegistrationNumber) throws ResourceNotFoundException;

	Folder findFolderByRegistrationNumber(@NotNull @NotBlank String folderRegistrationNumber)
			throws ResourceNotFoundException;

	Folder validateFolder(@NotNull @NotBlank String folderRegistrationNumber) throws Exception;

	void deleteFolder(@NotNull Long id) throws ResourceNotFoundException;

	Part addPart(@NotNull @NotBlank String folderRegistrationNumber, Part partEntity, MultipartFile part)
			throws Exception;

	FileSpec getPartFile(@NotNull @NotBlank String folderRegistrationNumber, @NotNull Long partId)
			throws Exception;

	FileSpec generateForm(@NotNull @NotBlank String folderRegistrationNumder) throws ResourceNotFoundException, Exception;

	FileSpec generateQuitus(@NotNull @NotBlank String folderRegistrationNumber, @NotNull Integer quitusId)
			throws ResourceNotFoundException;

	void deletePart(@NotNull @NotBlank String folderRegistrationNumber, @NotNull Long id) throws ResourceNotFoundException;

}
