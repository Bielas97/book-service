package com.bookservice.application.book.query;

import com.bookservice.domain.book.Book;
import java.util.List;

public interface IBookQueryService {

	List<Book> getAll();

	Book getById(Long id);

	Book getByTitle(String title);

	List<Book> getReleasedBooks();

	List<Book> getUnReleasedBooks();

}
