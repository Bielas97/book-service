package com.bookservice.application.book;

import com.bookservice.application.book.command.IBookCommandService;
import com.bookservice.application.book.query.IBookQueryService;
import com.bookservice.application.utils.Preconditions;
import com.bookservice.domain.book.Book;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Slf4j
@Service
public class BookFileStorageService {

	private final IBookCommandService bookCommandService;

	private final IBookQueryService bookQueryService;

	private final Path fileStoragePath;

	@Autowired
	public BookFileStorageService(
			@Value("${upload-file-location}") String fileStorageLocation,
			IBookCommandService bookCommandService,
			IBookQueryService bookQueryService) {
		this.bookCommandService = bookCommandService;
		this.bookQueryService = bookQueryService;
		this.fileStoragePath = Paths.get(fileStorageLocation)
				.toAbsolutePath().normalize();
		try {
			Files.createDirectories(this.fileStoragePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void attachFileToBook(Long id, MultipartFile file) {
		log.info("attaching file to book with id: " + id);
		Preconditions.requireNonNull(id);
		Preconditions.requireNonNull(file);
		Preconditions.requireNonNull(file.getOriginalFilename());
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		if (fileName.contains("..")) {
			throw new IllegalStateException("Sorry! Filename contains invalid path sequence " + fileName);
		}
		try {
			Path targetLocation = this.fileStoragePath.resolve(fileName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
					.path("books/query/downloadfile/" + id)
					.toUriString();
			Book book = bookQueryService.getById(id);
			book.setFileUrl(fileDownloadUri);
			book.setFilename(fileName);
			bookCommandService.updateBook(book);
		} catch (IOException ex) {
			log.error(ex.getMessage());
		}
		log.info("file attached");
	}

	public Resource downloadAttachedFileToBook(Long id) {
		log.info("downloading attached file of book with id: " + id);
		Preconditions.requireNonNull(id);
		Book book = bookQueryService.getById(id);
		return getAttachedFile(book);
	}

	private Resource getAttachedFile(Book book) {
		Preconditions.requireNonNull(book);
		if (!book.isFileAttached()) {
			throw new NullPointerException("No file attached to book with name: " + book.getTitle());
		}
		try {
			StringBuilder resourcePathString = new StringBuilder(fileStoragePath.toString());
			resourcePathString.append("/");
			resourcePathString.append(book.getFilename());
			Path resourcePath = Paths.get(resourcePathString.toString()).toAbsolutePath().normalize();
			Resource resource = new UrlResource(resourcePath.toUri());
			if (resource.exists()) {
				return resource;
			} else {
				throw new IllegalStateException("File not found " + book.getFilename());
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		throw new NullPointerException("Something went wrong");
	}
}
