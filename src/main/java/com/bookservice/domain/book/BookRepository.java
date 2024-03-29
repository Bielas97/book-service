package com.bookservice.domain.book;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
	Optional<Book> findByTitle(String title);

	List<Book> findByReleaseDateAfter(LocalDate localDate);

	List<Book> findByReleaseDateBefore(LocalDate localDate);
}
