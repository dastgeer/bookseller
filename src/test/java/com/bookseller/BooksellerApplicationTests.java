package com.bookseller;

import com.bookseller.controller.BookSellerController;
import com.bookseller.dto.ResponseMessage;
import com.bookseller.entity.Book;
import com.bookseller.service.BookSellerService;
import com.fasterxml.jackson.core.type.TypeReference;
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
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
		MvcResult result = mockMvc.perform(post("/api/bookserller/v1/addBooks").content(jsondata).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andReturn();
		List<Book> testReturnResult = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<Book>>() {});
		assertEquals("size of returned data",2, testReturnResult.size());
		assertEquals("id value of after save book to db",Long.valueOf(1),testReturnResult.get(0).getId());
	}

	@Test
	void getAllBookTest() throws Exception {

		Book book3= Book.builder().id(Long.valueOf(1)).author("chetan bhagat").description("nobel").name("five point").price(300).isbn("ch001").type("fiction").build();
		Book book4= Book.builder().id(Long.valueOf(2)).author("dastgeer").description("nobel").name("got").price(500).isbn("gt001").type("fiction").build();
		List<Book> books2 = new ArrayList<>();
		books2.add(book3);
		books2.add(book4);

		when(bookSellerService.getAllBooks()).thenReturn(books2);
//		//when
		MvcResult result = mockMvc.perform(get("/api/bookserller/v1/getAllBooks").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful())
		.andReturn();
		List<Book> books = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<Book>>() {});
		assertEquals("size of returned data",2, books.size());

	}

	@Test
	void updateBookTest() throws Exception {
		Book book2= Book.builder().id(Long.valueOf(2)).author("dastgeer update").description("nobel").name("got").price(500).isbn("gt001").type("fiction").build();

//		//given
		List<Book> inputbookList = new ArrayList<>();
		inputbookList.add(book2);

		Book book4= Book.builder().id(Long.valueOf(2)).author("dastgeer update").description("nobel").name("got").price(500).isbn("gt001").type("fiction").build();
		List<Book> returnList = new ArrayList<>();
		returnList.add(book4);
		String jsondata= objectMapper.writeValueAsString(inputbookList);
		when(bookSellerService.addBook(inputbookList)).thenReturn(returnList);
//		//when
		MvcResult result = mockMvc.perform(put("/api/bookserller/v1/updateBook").content(jsondata).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful())
				.andReturn();
		List<Book> testReturnResult = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<Book>>() {});
		assertEquals("size of returned data",1, testReturnResult.size());
		assertEquals(" value of after update book to db",book2.getAuthor(),testReturnResult.get(0).getAuthor());

	}

	@Test
	void deleteBookByNameTestRecordFound() throws Exception {

		ResponseMessage responseMessage= ResponseMessage.builder().message("book deleted from db with book name "+"five point").status(200).build();
		when(bookSellerService.deleteBookByName("five point")).thenReturn(responseMessage);
//		//when
		MvcResult result = mockMvc.perform(delete("/api/bookserller/v1/deleteBook/five point").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful())
				.andReturn();
		ResponseMessage testReturnResult = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<ResponseMessage>() {});
		assertEquals("matched result","book deleted from db with book name "+"five point", testReturnResult.getMessage());
	}

	@Test
	void deleteBookByNameTestRecordNotFound() throws Exception {
		ResponseMessage responseMessage1= ResponseMessage.builder().message("book doesn't exist in db with book name "+"hello").status(204).build();
		when(bookSellerService.deleteBookByName("hello")).thenReturn(responseMessage1);
//		//when
		MvcResult result1 = mockMvc.perform(delete("/api/bookserller/v1/deleteBook/hello").contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		ResponseMessage testReturnResult1 = objectMapper.readValue(result1.getResponse().getContentAsString(), new TypeReference<ResponseMessage>() {});
		assertEquals("matched result","book doesn't exist in db with book name "+"hello", testReturnResult1.getMessage());
	}
}
