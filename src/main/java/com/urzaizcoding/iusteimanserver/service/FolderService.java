package com.urzaizcoding.iusteimanserver.service;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import com.urzaizcoding.iusteimanserver.domain.registration.Folder;

public interface FolderService {

	List<Folder> findAllFolders();

	void deleteFolder(@NotNull @NotBlank String folderRegistrationNumber);

	Folder findFolderByRegistrationNumber(@NotNull @NotBlank String folderRegistrationNumber);

	Folder validateFolder(@NotNull @NotBlank String folderRegistrationNumber);

	void deleteFolder(@NotNull Long id);

	Long addPart(@NotNull @NotBlank String folderRegistrationNumber, MultipartFile part);

	FileSpec getPartFile(@NotNull @NotBlank String folderRegistrationNumber, @NotNull Long partId);

	FileSpec generateForm(@NotNull @NotBlank String folderRegistrationNumder);

	FileSpec generateQuitus(@NotNull @NotBlank String folderRegistrationNumber, @NotNull Integer quitusId);

}
