package br.com.restWithSpringBoot.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	

}
