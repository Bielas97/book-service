package com.bookservice.infrastructure.query;

import com.bookservice.application.book.BookFileStorageService;
import com.bookservice.application.book.query.IBookQueryService;
import com.bookservice.domain.book.Book;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("books/query")
@RequiredArgsConstructor
public class BookQueryResourceServer {

	private final IBookQueryService bookQueryService;

	private final BookFileStorageService bookFileStorageService;

	@GetMapping
	public List<Book> getAll() {
		return bookQueryService.getAll();
	}

	@GetMapping("/id/{id}")
	public Book getById(@PathVariable Long id) {
		return bookQueryService.getById(id);
	}

	@GetMapping("/title/{title}")
	public Book getByTitle(@PathVariable String title) {
		return bookQueryService.getByTitle(title);
	}

	@GetMapping("/released")
	public List<Book> getReleased() {
		return bookQueryService.getReleasedBooks();
	}

	@GetMapping("/unreleased")
	public List<Book> getUnReleased() {
		return bookQueryService.getUnReleasedBooks();
	}

	@GetMapping("/downloadfile/{id}")
	public ResponseEntity<Resource> downloadFile(@PathVariable Long id, HttpServletRequest request) {
		Resource resource = bookFileStorageService.downloadAttachedFileToBook(id);
		return getDownloadFileResponseEntity(resource, request);
	}

	private ResponseEntity<Resource> getDownloadFileResponseEntity(Resource resource, HttpServletRequest request) {
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException ex) {
			log.error(ex.getMessage());
		}
		if (contentType == null) {
			contentType = "application/octet-stream";
		}
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}
}
