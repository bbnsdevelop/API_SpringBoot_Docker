package br.com.restWithSpringBoot.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.restWithSpringBoot.config.FileStorageConfig;
import br.com.restWithSpringBoot.exception.FileStorageException;
import br.com.restWithSpringBoot.mapper.UploadFileResponseVO;

@Service
public class FileStorageService {

	private final Path fileStorageLocation;

	@Autowired
	public FileStorageService(FileStorageConfig config) {
		this.fileStorageLocation = Paths.get(config.getUploadDir()).toAbsolutePath().normalize();

		try {

		} catch (Exception e) {
			throw new FileStorageException("Could not create the directory where the upload files will be stored", e);
		}
	}

	public UploadFileResponseVO storeFile(MultipartFile file) {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		try {
			
			if(fileName.contains("..")) {
				throw new FileStorageException("Sorry! FileName contains invalid path sequence" + fileName);
			}
			Path targetLocation = this.fileStorageLocation.resolve(fileName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			
			return new UploadFileResponseVO(fileName, fileDownloadUri(fileName), file.getContentType(), file.getSize());
		} catch (Exception e) {
			throw new FileStorageException("Could not store file" +fileName+". Please try again", e);
		}
	}
	
	private String fileDownloadUri(String fileName) {
		return ServletUriComponentsBuilder.fromCurrentContextPath()
			.path("/api/v1/file/download/")
			.path(fileName)
			.toUriString();
	}

}
