package com.urzaizcoding.iusteimanserver.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.urzaizcoding.iusteimanserver.domain.registration.Folder;
import com.urzaizcoding.iusteimanserver.domain.registration.Part;
import com.urzaizcoding.iusteimanserver.dto.FolderDTO;
import com.urzaizcoding.iusteimanserver.dto.FolderDTOLigth;
import com.urzaizcoding.iusteimanserver.dto.PartDTO;
import com.urzaizcoding.iusteimanserver.mappers.MapStructMapper;
import com.urzaizcoding.iusteimanserver.service.FileSpec;
import com.urzaizcoding.iusteimanserver.service.FolderService;

@Controller
@CrossOrigin
@RequestMapping("/folders")
public class FolderController {

	private final FolderService folderService;
	private final MapStructMapper mapper;

	public FolderController(FolderService folderService, MapStructMapper mapper) {
		super();
		this.folderService = folderService;
		this.mapper = mapper;
	}

	@PutMapping(
			produces = { MediaType.APPLICATION_JSON_VALUE },
			path = "{folderRegistrationNumber}/status"
	)
	public ResponseEntity<FolderDTOLigth> validateFolder(@PathVariable @NotNull @NotBlank String folderRegistrationNumber)
			throws Exception {

		Folder folderEntity = folderService.validateFolder(folderRegistrationNumber);

		return ResponseEntity.status(HttpStatus.OK)
				.body(mapper.folderToFolderDTOLight(folderEntity));

	}

	@GetMapping(
			produces = { MediaType.APPLICATION_JSON_VALUE },
			path = "{folderRegistrationNumber}"
	)
	public ResponseEntity<FolderDTO> getFolderByRegistrationNumber(
			@PathVariable @NotNull @NotBlank String folderRegistrationNumber) {

		Folder folderEntity = folderService.findFolderByRegistrationNumber(folderRegistrationNumber);

		return ResponseEntity.ok(mapper.folderToFolderDTO(folderEntity));
	}

	@GetMapping(
			produces = { MediaType.APPLICATION_JSON_VALUE }
	)
	public ResponseEntity<List<FolderDTOLigth>> getAllFolders() {
		return ResponseEntity.ok(folderService.findAllFolders().stream()
				.map(f -> mapper.folderToFolderDTOLight(f)).collect(Collectors.toList()));
	}

	@DeleteMapping(
			path = "{folderRegistrationNumber}"
	)
	public ResponseEntity<Void> deleteFolder(@PathVariable @NotNull @NotBlank String folderRegistrationNumber)
			throws Exception {

		folderService.deleteFolder(folderRegistrationNumber);

		return ResponseEntity.ok().build();
	}

	@DeleteMapping(
			path = "{id}"
	)
	public ResponseEntity<Void> deleteFolder(@PathVariable @NotNull Long id) {

		folderService.deleteFolder(id);

		return ResponseEntity.ok().build();
	}

	@PostMapping(
			consumes = { MediaType.MULTIPART_FORM_DATA_VALUE,MediaType.APPLICATION_JSON_VALUE },
			path = { "{folderRegistrationNumber}/parts" }
	)
	public ResponseEntity<PartDTO> addPart(@RequestPart("part") MultipartFile part,
			@PathVariable @NotNull @NotBlank String folderRegistrationNumber,
			@RequestPart("metadata") @NotNull PartDTO partResource,
			UriComponentsBuilder uriComponentsBuilder) throws Exception {
		
		Part partEntity = mapper.partDTOToPart(partResource);

		partEntity = folderService.addPart(folderRegistrationNumber,partEntity, part);

		UriComponents uriComponents = uriComponentsBuilder.path("/folders/{folderRegistrationNumber}/parts/{id}")
				.buildAndExpand(folderRegistrationNumber, partEntity.getId());

		return ResponseEntity.status(HttpStatus.CREATED).header("Location", uriComponents.toUriString())
				.body(mapper.partToPartDTO(partEntity));
	}

	@GetMapping(
			path = { "{folderRegistrationNumber}/parts/{id}" },
			produces = { MediaType.IMAGE_JPEG_VALUE,MediaType.IMAGE_PNG_VALUE }
	)
	public ResponseEntity<byte[]> getPart(
			@PathVariable("folderRegistrationNumber") @NotNull @NotBlank String folderRegistrationNumber,
			@PathVariable("id") @NotNull Long partId) throws Exception {

		FileSpec fileSpec = folderService.getPartFile(folderRegistrationNumber, partId);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION,String.format("attachment;filename=%s", fileSpec.fileName()))
				.contentLength(fileSpec.fileSize())
				.body(fileSpec.data());

	}

	@GetMapping(
			path = { "{folderRegistrationNumber}/form" },
			produces = { MediaType.APPLICATION_PDF_VALUE }
	)
	public ResponseEntity<byte[]> generatePdfForm(@PathVariable @NotNull @NotBlank String folderRegistrationNumder) {

		FileSpec fileSpec = folderService.generateForm(folderRegistrationNumder);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION,String.format("attachment;filename=%s", fileSpec.fileName()))
				.contentLength(fileSpec.fileSize())
				.body(fileSpec.data());
	}

	@GetMapping(
			produces = { MediaType.APPLICATION_PDF_VALUE },
			path = {"{folderRegistrationNumber}/"}
	)
	public ResponseEntity<byte[]> generatePdfQuitus(@PathVariable @NotNull @NotBlank String folderRegistrationNumber,
			@PathVariable @NotNull Integer quitusId) throws Exception {

		FileSpec fileSpec = folderService.generateQuitus(folderRegistrationNumber, quitusId);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION,String.format("attachment;filename=%s", fileSpec.fileName()))
				.contentLength(fileSpec.fileSize())
				.body(fileSpec.data());
	}

}
