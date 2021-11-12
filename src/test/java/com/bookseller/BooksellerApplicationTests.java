package com.bookseller;

import com.bookseller.controller.BookSellerController;
import com.bookseller.entity.Book;
import com.bookseller.service.BookSellerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookSellerController.class)
@AutoConfigureMockMvc
class BooksellerApplicationTests {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	BookSellerService bookSellerService;

	ObjectMapper objectMapper = new ObjectMapper();

	@Test
	void addBookTest() throws Exception {
		Book book1= Book.builder().id(null).author("chetan bhagat").description("nobel").name("five point").price(300).isbn("ch001").type("fiction").build();
		Book book2= Book.builder().id(null).author("dastgeer").description("nobel").name("got").price(500).isbn("gt001").type("fiction").build();

		//given
		List<Book> books = new ArrayList<>();
		books.add(book1);
		books.add(book2);

		Book book3= Book.builder().id(Long.valueOf(1)).author("chetan bhagat").description("nobel").name("five point").price(300).isbn("ch001").type("fiction").build();
		Book book4= Book.builder().id(Long.valueOf(2)).author("dastgeer").description("nobel").name("got").price(500).isbn("gt001").type("fiction").build();
		List<Book> books2 = new ArrayList<>();
		books2.add(book3);
		books2.add(book4);
		String jsondata= objectMapper.writeValueAsString(books);
		when(bookSellerService.addBook(books)).thenReturn(books2);
		//when
		mockMvc.perform(post("/api/bookserller/v1/addBooks").content(jsondata).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
	}

	@Test
	void getAllBookTest() throws Exception {
//		Book book1= Book.builder().id(null).author("chetan bhagat").description("nobel").name("five point").price(300).isbn("ch001").type("fiction").build();
//		Book book2= Book.builder().id(null).author("dastgeer").description("nobel").name("got").price(500).isbn("gt001").type("fiction").build();
//
//		//given
//		List<Book> books = new ArrayList<>();
//		books.add(book1);
//		books.add(book2);

		Book book3= Book.builder().id(Long.valueOf(1)).author("chetan bhagat").description("nobel").name("five point").price(300).isbn("ch001").type("fiction").build();
		Book book4= Book.builder().id(Long.valueOf(2)).author("dastgeer").description("nobel").name("got").price(500).isbn("gt001").type("fiction").build();
		List<Book> books2 = new ArrayList<>();
		books2.add(book3);
		books2.add(book4);
		//String jsondata= objectMapper.writeValueAsString(books);
		when(bookSellerService.getAllBooks()).thenReturn(books2);
//		//when
		mockMvc.perform(get("/api/bookserller/v1/getAllBooks").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful());
	}


}
