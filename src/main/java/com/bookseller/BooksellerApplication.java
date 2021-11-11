package com.bookseller;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BooksellerApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(BooksellerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		initData();
	}

	private void initData() {
	}
}
