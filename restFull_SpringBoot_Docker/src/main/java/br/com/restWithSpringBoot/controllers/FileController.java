package br.com.restWithSpringBoot.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.restWithSpringBoot.mapper.UploadFileResponseVO;
import br.com.restWithSpringBoot.service.FileStorageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "File Service", description = "Endpoint to upload files", tags = {"File Service"})
@RestController
@RequestMapping("/api/v1/file")
public class FileController {
	private static Logger log = LoggerFactory.getLogger(FileController.class);
	@Autowired
	private FileStorageService fileStorageService;
	
	
	@ApiOperation(value ="Upload file")
	@PostMapping(value ="/upload")
	public ResponseEntity<UploadFileResponseVO> uploadFile(@RequestParam("file") MultipartFile file) {
		return ResponseEntity.status(HttpStatus.OK).body(this.fileStorageService.storeFile(file));
	}
	
	@ApiOperation(value ="Upload mult files")
	@PostMapping(value ="/upload-multi")
	public ResponseEntity<List<UploadFileResponseVO>> uploadMultiFiles(@RequestParam("files") MultipartFile[] files) {
		return ResponseEntity.status(HttpStatus.OK).body(this.fileStorageService.storeFiles(files));
	}
	
	@ApiOperation(value ="Download file")
	@GetMapping(value ="/download/{fileName:.+}")
	public ResponseEntity<Resource> downloadFile(@PathVariable("fileName") String fileName, HttpServletRequest request) {
		Resource resource = this.fileStorageService.loadFileResource(fileName);
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (Exception e) {
			log.error("Could not determine file type");
		}
		if(contentType == null) {
			contentType = "application/octet-stream";
		}
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+ resource.getFilename() +"\"")
				.body(resource);
	}
	

}
