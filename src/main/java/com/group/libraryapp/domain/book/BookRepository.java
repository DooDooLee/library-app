package com.group.libraryapp.domain.book;

import com.group.libraryapp.dto.book.request.BookLoanRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByName(String bookName);


}