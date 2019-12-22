package com.bookservice.domain.book;

import com.bookservice.domain.BaseEntity;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "BOOKS")
public class Book extends BaseEntity implements Serializable {

	@Column(name = "TITLE", nullable = false)
	private String title;

	@Column(name = "RELEASE_DATE", nullable = true)
	private LocalDate releaseDate;

	@Column(name = "GENRE", nullable = false)
	private String genre;

	@Column(name = "AUTHOR", nullable = false)
	private String author;

	@Embedded
	private Money price;

	@Column(name = "FILE_URL", nullable = true)
	private String fileUrl;

	@Column(name = "FILENAME", nullable = true)
	private String filename;

	public boolean isFileAttached() {
		return fileUrl != null && filename != null;
	}
}

