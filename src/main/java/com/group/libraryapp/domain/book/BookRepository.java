package com.group.libraryapp.domain.book;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.group.libraryapp.domain.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

  Optional<Book> findByName(String bookName);

}
