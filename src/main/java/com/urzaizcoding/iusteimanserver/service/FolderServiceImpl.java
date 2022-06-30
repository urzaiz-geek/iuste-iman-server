package com.urzaizcoding.iusteimanserver.service;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.urzaizcoding.iusteimanserver.domain.registration.Folder;
import com.urzaizcoding.iusteimanserver.exception.ResourceNotFoundException;
import com.urzaizcoding.iusteimanserver.repository.FolderRepository;

@Service
public class FolderServiceImpl implements FolderService {

	private final FolderRepository folderRepository;

	public FolderServiceImpl(FolderRepository folderRepository) {
		super();
		this.folderRepository = folderRepository;
	}

	@Override
	public Folder findFolderByRegistrationNumber(String folderRegistrationNumber) {
		return folderRepository.findByFolderRegistrationNumber(folderRegistrationNumber)
				.orElseThrow(() -> new ResourceNotFoundException(
						String.format("The folder referenced by the registration number %s does not exists",
								folderRegistrationNumber)));
	}

	@Override
	public Folder validateFolder(String folderRegistrationNumber) {
		//update folder status
		
		//create user account
		
		//create student registration number
		
		//send email to user to tel him that he has been accepted
		return null;
	}

	@Override
	public List<Folder> findAllFolders() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteFolder(@NotNull @NotBlank String folderRegistrationNumber) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteFolder(@NotNull Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Long addPart(@NotNull @NotBlank String folderRegistrationNumber, MultipartFile part) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FileSpec getPartFile(@NotNull @NotBlank String folderRegistrationNumber, @NotNull Long partId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FileSpec generateForm(@NotNull @NotBlank String folderRegistrationNumder) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FileSpec generateQuitus(@NotNull @NotBlank String folderRegistrationNumber, @NotNull Integer quitusId) {
		// TODO Auto-generated method stub
		return null;
	}

}
