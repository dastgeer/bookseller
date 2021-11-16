package com.bookseller;

import com.bookseller.dto.ResponseMessage;
import com.bookseller.entity.Book;
import com.bookseller.repository.BookRepository;
import com.bookseller.service.BookSellerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookSellerServiceTests {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookSellerService bookSellerService;


    ObjectMapper objectMapper = new ObjectMapper();


    @Test
    public void addBookTest(){

        Book book1= Book.builder().id(null).author("chetan bhagat").description("nobel").name("five point").price(300).isbn("ch001").type("fiction").build();

        //given
        List<Book> books = new ArrayList<>();
        books.add(book1);

        Book book3= Book.builder().id(Long.valueOf(1)).author("chetan bhagat").description("nobel").name("five point").price(300).isbn("ch001").type("fiction").build();
        List<Book> books2 = new ArrayList<>();
        books2.add(book3);
        given(bookRepository.save(book1)).willReturn(book3);
        List<Book> testBookList = bookSellerService.addBook(books);
        System.out.println("testBookList--->"+testBookList);
        assertEquals("size check",1,testBookList.size());
        assertEquals(Long.valueOf(1),testBookList.get(0).getId());
    }


    @Test
    public void getAllBooksTest() {
        Book book3= Book.builder().id(Long.valueOf(1)).author("chetan bhagat").description("nobel").name("five point").price(300).isbn("ch001").type("fiction").build();
        Book book4= Book.builder().id(Long.valueOf(2)).author("dastgeer").description("nobel").name("got").price(500).isbn("gt001").type("fiction").build();
        List<Book> books2 = new ArrayList<>();
        books2.add(book3);
        books2.add(book4);

        when(bookRepository.findAll()).thenReturn(books2);
        List<Book> testBookList = bookSellerService.getAllBooks();
        System.out.println("testBookList  "+testBookList);
        assertNotNull("not null check",testBookList);
        assertEquals("size check",2,testBookList.size());

    }

    @Test
    public void deleteBookByNameTest() {
        Book book3= Book.builder().id(Long.valueOf(1)).author("chetan bhagat").description("nobel").name("five point").price(300).isbn("ch001").type("fiction").build();
        when( bookRepository.findByName("chetan bhagat")).thenReturn(book3);
        ResponseMessage responseMessage = bookSellerService.deleteBookByName("chetan bhagat");
        assertEquals("status check",200,responseMessage.getStatus());
        assertEquals("content check","book deleted from db with book name chetan bhagat",responseMessage.getMessage());

    }
    @Test
    public void deleteBookByNameTestNotExist() {
        Book book3= Book.builder().id(Long.valueOf(1)).author("chetan bhagat").description("nobel").name("five point").price(300).isbn("ch001").type("fiction").build();
        when( bookRepository.findByName("chetan bhagat")).thenReturn(null);
        ResponseMessage responseMessage = bookSellerService.deleteBookByName("chetan bhagat");
        assertEquals("status check",204,responseMessage.getStatus());
        assertEquals("content check","book doesn't exist in db with book name chetan bhagat",responseMessage.getMessage());

    }

}
