package com.bookservice.application.book.command;

import com.bookservice.domain.book.Book;

public interface IBookCommandService {

	void addBook(Book book);

	void deleteBook(Long id);

	void updateBook(Book book);
}
