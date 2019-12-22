package com.bookservice.infrastructure.command;

import com.bookservice.application.book.BookFileStorageService;
import com.bookservice.application.book.command.IBookCommandService;
import com.bookservice.domain.book.Book;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookCommandResourceServer {

	private final IBookCommandService bookCommandService;

	private final BookFileStorageService bookFileStorageService;

	@PostMapping
	public ResponseEntity<CommandResourceResponse> addBook(@RequestBody Book book) {
		bookCommandService.addBook(book);
		CommandResourceResponse response = CommandResourceResponse.builder().msg("Book added!").msgTime(LocalDateTime.now()).build();
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<CommandResourceResponse> deleteBook(@PathVariable Long id) {
		bookCommandService.deleteBook(id);
		CommandResourceResponse response = CommandResourceResponse.builder().msg("Book deleted").msgTime(LocalDateTime.now()).build();
		return ResponseEntity.ok(response);
	}

	@PostMapping("/uploadFile/{id}")
	public ResponseEntity<CommandResourceResponse> attachFileToBook(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
		bookFileStorageService.attachFileToBook(id, file);
		CommandResourceResponse response = CommandResourceResponse.builder().msg("File attached").msgTime(LocalDateTime.now()).build();
		return ResponseEntity.ok(response);
	}

}
