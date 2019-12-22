package com.bookservice.application.utils;

import java.util.Collection;

public class Preconditions {

	public static <T> T requireNonNull(T object) {
		if (object == null) {
			throw new NullPointerException();
		}
		return object;
	}

	public static String requireNonEmpty(String nullableParam) {
		String param = requireNonNull(nullableParam);
		boolean isNotEmpty = !param.trim().isEmpty();
		return checkArgument(param, isNotEmpty, "argument is empty");
	}

	public static <T extends Collection> T requireNonEmpty(T collection) {
		T param = requireNonNull(collection);
		boolean isNotEmpty = !param.isEmpty();
		return checkArgument(param, isNotEmpty, "argument is empty");
	}

	public static <T> T checkArgument(T argument, boolean condition, String message) {
		if (!condition) {
			throw new IllegalArgumentException(message);
		}
		return argument;
	}
}
