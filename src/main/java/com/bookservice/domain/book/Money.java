package com.bookservice.domain.book;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Money implements Serializable {

	@Column(name = "PRICE", nullable = false)
	private Long price;

	@Column(name = "CURRENCY", nullable = false)
	private String currency;
}
