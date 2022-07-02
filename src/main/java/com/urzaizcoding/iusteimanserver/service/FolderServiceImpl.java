package com.urzaizcoding.iusteimanserver.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.urzaizcoding.iusteimanserver.domain.registration.Folder;
import com.urzaizcoding.iusteimanserver.domain.registration.Part;
import com.urzaizcoding.iusteimanserver.domain.registration.student.Student;
import com.urzaizcoding.iusteimanserver.domain.user.Account;
import com.urzaizcoding.iusteimanserver.exception.ResourceNotFoundException;
import com.urzaizcoding.iusteimanserver.repository.FolderRepository;

@Service
public class FolderServiceImpl implements FolderService {

	private final FolderRepository folderRepository;
	private final MailNotificationService mailNotificationService;
	private final StorageService storageService;

	public FolderServiceImpl(FolderRepository folderRepository, MailNotificationService mailNotificationService,
			StorageService storageService) {
		super();
		this.folderRepository = folderRepository;
		this.mailNotificationService = mailNotificationService;
		this.storageService = storageService;
	}

	@Override
	public Folder findFolderByRegistrationNumber(String folderRegistrationNumber) {
		return folderRepository.findByFolderRegistrationNumber(folderRegistrationNumber)
				.orElseThrow(() -> new ResourceNotFoundException(
						String.format("The folder referenced by the registration number %s does not exists",
								folderRegistrationNumber)));
	}

	@Override
	@Transactional
	public Folder validateFolder(String folderRegistrationNumber) throws Exception {

		// get the folder first

		Folder folderEntity = folderRepository.findByFolderRegistrationNumber(folderRegistrationNumber)
				.orElseThrow(() -> new ResourceNotFoundException(
						String.format("The folder referenced by the registration number %s does not exists",
								folderRegistrationNumber)));

		// update folder status
		folderEntity.setValidated(true);

		// create user account

		Account account = Account.builder().build();

		Student student = folderEntity.getStudent();

		student.setAccount(account);

		// create student registration number

		student.setRegistrationId(Student.generateStudentRegistrationId());

		// send email to user to tel him that he has been accepted

		mailNotificationService.sendAcceptanceEmail(student);

		return folderEntity;
	}

	@Override
	public List<Folder> findAllFolders() {
		return folderRepository.findAll();
	}

	@Override
	public void deleteFolder(@NotNull @NotBlank String folderRegistrationNumber) throws ResourceNotFoundException {
		Folder toDelete = folderRepository.findByFolderRegistrationNumber(folderRegistrationNumber)
				.orElseThrow(() -> new ResourceNotFoundException(
						String.format("The folder referenced by the registration number %s does not exists",
								folderRegistrationNumber)));
		folderRepository.deleteById(toDelete.getId());
	}

	@Override
	public void deleteFolder(@NotNull Long id) {
		folderRepository.deleteById(id);
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

	@Override
	@Transactional
	public Part addPart(@NotNull @NotBlank String folderRegistrationNumber, Part partEntity, MultipartFile part)
			throws Exception {
		System.out.println("received file :\nname : "+part.getOriginalFilename()+"\nsize : "+part.getSize());
		//find concerned folder
		Folder folder = folderRepository.findByFolderRegistrationNumber(folderRegistrationNumber).orElseThrow(() -> new ResourceNotFoundException(
				String.format("The folder referenced by the registration number %s does not exists",
						folderRegistrationNumber)));
		
		//add new empty part to it
		Part newPart = folder.newPart();
		
		//set new part infos
		if(partEntity.getName() == null) {
			newPart.setName(part.getOriginalFilename());
		}else {
			newPart.setName(partEntity.getName());
		}
		
		newPart.setFileType(partEntity.getFileType());
		
		
		newPart.setDescription(partEntity.getDescription());
		newPart.setFileType(partEntity.getFileType());
		newPart.setUploadDate(LocalDateTime.now());
		newPart.updatePartName(part.getOriginalFilename());
		newPart.setSize(part.getSize());
		
		//create to filespec to save
		
		FileSpec fileSpec = new FileSpec() {
			
			@Override
			public Long fileSize() {
				return newPart.getSize();
			}
			
			@Override
			public String fileName() {
				return newPart.getName();
			}
			
			@Override
			public byte[] data() {
				return null;
			}
		};
		
		//generate the path /courses/:courseId/folders/:folderRegistrationNumber
		
		String path = String.format(Part.BASE_PART_FORMAT, folder.getCourse().getId(), folderRegistrationNumber);
		
		//Save on storage
		
		String archivePath = storageService.saveFile(path ,fileSpec , part.getInputStream());
		
		//update the archive path
		
		newPart.setArchivePath(archivePath);
		
		return newPart;
	}

}
