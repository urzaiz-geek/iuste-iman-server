package com.urzaizcoding.iusteimanserver.service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.urzaizcoding.iusteimanserver.configuration.AppConfigurer;
import com.urzaizcoding.iusteimanserver.domain.registration.Folder;
import com.urzaizcoding.iusteimanserver.domain.registration.Part;
import com.urzaizcoding.iusteimanserver.domain.registration.student.Student;
import com.urzaizcoding.iusteimanserver.domain.user.Account;
import com.urzaizcoding.iusteimanserver.domain.user.Notification;
import com.urzaizcoding.iusteimanserver.exception.ResourceNotFoundException;
import com.urzaizcoding.iusteimanserver.repository.FolderRepository;
import com.urzaizcoding.iusteimanserver.utils.RandomStringGenerator;

@Service
public class FolderServiceImpl implements FolderService {

	private static final String WELCOME = "Bienvenue";

	private static final String WELCOME_MESSAGE = "Félicitations vous avez été accepté comme étudiant dans notre établissement vous pouvez désormais accéder aux différentes fonctionnalités de notre plateforme et vous rapprocher de l'établissement pour bénéficier des cours";

	private static String NAME_FORMAT = "part_f%d_%s";

	private static String DATE_TIME_FORMAT = "yyyyMMdd_hhmmss";

	private final FolderRepository folderRepository;
	private final MailNotificationService mailNotificationService;
	private final ImageStorageService storageService;
	private final PDFGeneratorService pdfGeneratorService;
	private final AccountService accountService;

	public FolderServiceImpl(FolderRepository folderRepository, MailNotificationService mailNotificationService,
			ImageStorageService storageService, PDFGeneratorService pdfGeneratorService,
			AccountService accountService) {
		super();
		this.folderRepository = folderRepository;
		this.mailNotificationService = mailNotificationService;
		this.storageService = storageService;
		this.pdfGeneratorService = pdfGeneratorService;
		this.accountService = accountService;
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

		Student student = folderEntity.getStudent();

		Account account = Account.builder().username(student.getEmail())
				.password(RandomStringGenerator.numbersString(3)).build();

		accountService.saveAccount(account, null); // we have to save it ourself to encrypt password
		student.setAccount(account);

		// create student registration number

		student.setRegistrationId(Student.generateStudentRegistrationId());

		// send email to user to tel him that he has been accepted

		mailNotificationService.sendAcceptanceEmail(student);

		// send him notification
		student.getAccount().addNotification(Notification.builder().issuedDate(LocalDate.now())
				.subject(WELCOME)
				.content(WELCOME_MESSAGE)
				.build());

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
	public FileSpec getPartFile(@NotNull @NotBlank String folderRegistrationNumber, @NotNull Long partId)
			throws Exception {

		// we query the folder first
		Folder folderEntity = folderRepository.findByFolderRegistrationNumber(folderRegistrationNumber)
				.orElseThrow(() -> new ResourceNotFoundException(
						String.format("The folder referenced by the registration number %s does not exists",
								folderRegistrationNumber)));

		// we then query the part in that folder
		Part part = folderEntity.getParts().stream().filter(p -> p.getId().equals(partId)).findFirst()
				.orElseThrow(() -> new ResourceNotFoundException(
						String.format("The part referenced by the id %d does not exists", partId)));

		return storageService.getFile(part.getArchivePath());
	}

	@Override
	public FileSpec generateForm(@NotNull @NotBlank String folderRegistrationNumber) throws Exception {
		// Get the concerned Folder
		Folder folder = folderRepository.findByFolderRegistrationNumber(folderRegistrationNumber)
				.orElseThrow(() -> new ResourceNotFoundException(
						String.format("The folder referenced by the registration number %s does not exists",
								folderRegistrationNumber)));

		FileSpec file = pdfGeneratorService.generateForm(folder);

		return file;
	}

	@Override
	public FileSpec generateQuitus(@NotNull @NotBlank String folderRegistrationNumber, @NotNull Long quitusId)
			throws Exception {
		Folder folder = folderRepository.findByFolderRegistrationNumber(folderRegistrationNumber)
				.orElseThrow(() -> new ResourceNotFoundException(
						String.format("The folder referenced by the registration number %s does not exists",
								folderRegistrationNumber)));

		return pdfGeneratorService.generateQuitus(folder, quitusId);
	}

	@Override
	@Transactional
	public Part addPart(@NotNull @NotBlank String folderRegistrationNumber, Part partEntity, MultipartFile part)
			throws Exception {
		System.out.println("received file :\nname : " + part.getOriginalFilename() + "\nsize : " + part.getSize());
		// find concerned folder
		Folder folder = folderRepository.findByFolderRegistrationNumber(folderRegistrationNumber)
				.orElseThrow(() -> new ResourceNotFoundException(
						String.format("The folder referenced by the registration number %s does not exists",
								folderRegistrationNumber)));

		// add new empty part to it
		Part newPart = folder.newPart();

		// set new part infos
		if (partEntity.getName() == null) {
			newPart.setName(part.getOriginalFilename());
		} else {
			newPart.setName(partEntity.getName());
		}

		newPart.setFileType(partEntity.getFileType());

		newPart.setDescription(partEntity.getDescription());
		newPart.setFileType(partEntity.getFileType());
		newPart.setUploadDate(LocalDateTime.now(AppConfigurer.appTimeZoneId()));
		newPart.updatePartName(part.getOriginalFilename());
		newPart.setSize(part.getSize());

		// create to filespec to save

		String extension = part.getOriginalFilename().substring(part.getOriginalFilename().lastIndexOf(".") + 1);

		DateTimeFormatter formater = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);

		FileSpec fileSpec = new FileSpec() {

			@Override
			public Long fileSize() {
				return newPart.getSize();
			}

			@Override
			public String fileName() {
				return String.format(NAME_FORMAT, newPart.getFolder().getId(),
						formater.format(newPart.getUploadDate()));
			}

			@Override
			public byte[] data() throws IOException {
				return part.getBytes();
			}

			@Override
			public String fileType() {
				return extension;
			}
		};

		// generate the path /courses/:courseId/folders/:folderRegistrationNumber

		String path = String.format(Part.BASE_PART_FORMAT, folder.getCourse().getId(), folderRegistrationNumber);

		// Save on storage

		String archivePath = storageService.saveFile(path, fileSpec);

		// update the archive path

		newPart.setArchivePath(archivePath);

		return newPart;
	}

	@Override
	public void deletePart(@NotNull @NotBlank String folderRegistrationNumber, @NotNull Long id)
			throws ResourceNotFoundException {
		// TODO Auto-generated method stub

	}

	@Override
	public Part archivatePart(@NotBlank String folderRegistrationNumber, @NotNull Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
