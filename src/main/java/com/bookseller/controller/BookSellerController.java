package com.bookseller.controller;

import com.bookseller.entity.Book;
import com.bookseller.service.BookSellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookserller/v1")
public class BookSellerController {

    @Autowired
    private BookSellerService bookSellerService;

    @PostMapping(path = "/addBooks")
    public ResponseEntity<?> addBook(@RequestBody List<Book> book){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(bookSellerService.addBook(book));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping(path = "/getAllBooks")
    public ResponseEntity<?> getAllBooks(){
            return ResponseEntity.status(HttpStatus.OK).body(bookSellerService.getAllBooks());
    }

    @PutMapping(path = "/updateBook")
    public ResponseEntity<?> updateBook(@RequestBody List<Book> book){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(bookSellerService.addBook(book));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping(path = "/deleteBook/{bookName}")
    public ResponseEntity<?> deleteBookById(@PathVariable String bookName){
        try {
            if(StringUtils.isEmpty(bookName)){
                throw new RuntimeException("bookName should not be empty or null");
            }
            return ResponseEntity.status(HttpStatus.OK).body(bookSellerService.deleteBookByName(bookName));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
