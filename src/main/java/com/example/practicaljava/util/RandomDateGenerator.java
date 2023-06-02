package com.example.practicaljava.util;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

public class RandomDateGenerator {
	private static final long MIN_DAY = LocalDate.of(2000, 1, 1).toEpochDay();
	private static final long MAX_DAY = LocalDate.of(2018, 1, 1).toEpochDay();
	
	public static LocalDate getRandomDate() {
		long randomDate = ThreadLocalRandom.current()
									.nextLong(RandomDateGenerator.MIN_DAY, RandomDateGenerator.MAX_DAY);
		
		return LocalDate.ofEpochDay(randomDate);
	}
}
