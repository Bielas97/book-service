package com.bookservice.application.book.query.jpa;

import com.bookservice.application.book.query.IBookQueryService;
import com.bookservice.domain.book.Book;
import com.bookservice.domain.book.BookRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookJpaQueryService implements IBookQueryService {

	private final BookRepository bookRepository;

	@Override
	public List<Book> getAll() {
		return bookRepository.findAll();
	}

	@Override
	public Book getById(Long id) {
		Optional<Book> byId = bookRepository.findById(id);
		if(byId.isPresent()){
			return byId.get();
		}
		throw new IllegalStateException("No book with id: " + id);
	}

	@Override
	public Book getByTitle(String title) {
		Optional<Book> byTitle = bookRepository.findByTitle(title);
		if(byTitle.isPresent()){
			return byTitle.get();
		}
		throw new IllegalStateException("No book with title: " + title);
	}

	@Override
	public List<Book> getReleasedBooks() {
		List<Book> byReleaseDateAfter = bookRepository.findByReleaseDateAfter(LocalDate.now());
		if(!byReleaseDateAfter.isEmpty()) {
			return byReleaseDateAfter;
		}
		throw new NullPointerException("No released books yet");
	}

	@Override
	public List<Book> getUnReleasedBooks() {
		List<Book> byReleaseDateBefore = bookRepository.findByReleaseDateBefore(LocalDate.now());
		if(!byReleaseDateBefore.isEmpty()){
			return byReleaseDateBefore;
		}
		throw new NullPointerException("No unreleased books yet");
	}
}
