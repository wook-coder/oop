package com.pratice.oop.bookstore.data.repository;

import com.pratice.oop.bookstore.data.entity.Book;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

  List<Book> findBookByDeleteAtFalseOrderByIdDesc();

  Optional<Book> findByIdAndDeleteAtFalse(long id);
}
