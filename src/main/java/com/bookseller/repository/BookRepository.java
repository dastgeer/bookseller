package com.bookseller.repository;

import com.bookseller.entity.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends CrudRepository<Book,Long> {
    public Book findByName(String name);

}


