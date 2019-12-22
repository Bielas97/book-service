package com.bookservice.application.book.command.jpa;

import com.bookservice.application.book.command.IBookCommandService;
import com.bookservice.application.utils.Preconditions;
import com.bookservice.domain.book.Book;
import com.bookservice.domain.book.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookJpaCommandService implements IBookCommandService {

	private final BookRepository bookRepository;

	@Override
	public void addBook(Book book) {
		log.info("adding book with name: " + book.getTitle());
		Preconditions.requireNonNull(book);
		bookRepository.save(book);
		log.info("book added");
	}

	@Override
	public void deleteBook(Long id) {
		log.info("deleting book with id: " + id);
		Preconditions.requireNonNull(id);
		bookRepository.deleteById(id);
		log.info("book deleted");
	}

	@Override
	public void updateBook(Book book) {
		log.info("updating book with name: " + book.getTitle());
		Preconditions.requireNonNull(book);
		bookRepository.save(book);
		log.info("book updated");
	}
}
