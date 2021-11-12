package com.bookseller.service;

import com.bookseller.dto.ResponseMessage;
import com.bookseller.entity.Book;
import com.bookseller.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookSellerService {

    @Autowired
    private BookRepository bookRepository;

    /**
     * it will add & update the book in database
     * @param books
     * @return
     */
    @Transactional
    public List<Book> addBook(List<Book> books) {
        for (Book book : books){
            bookRepository.save(book);
        }
        return  books;
    }


    /**
     * it will return all books
     * @return
     */
    public List<Book> getAllBooks() {
        return (List<Book>)bookRepository.findAll();
    }


    /**
     * it will delete the book by bookName,because book id primary key & book name is unique
     * @param bookName
     * @return
     */
    @Transactional
    public ResponseMessage deleteBookByName(String bookName){
        Book book= bookRepository.findByName(bookName);
        if(book!=null) {
            bookRepository.delete(book);
            return ResponseMessage.builder().message("book deleted from db with book name "+bookName).status(200).build();
        }
        return ResponseMessage.builder().message("book doesn't exist in db with book name "+bookName).status(204).build();
    }
}
