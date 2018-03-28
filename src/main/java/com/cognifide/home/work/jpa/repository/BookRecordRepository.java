package com.cognifide.home.work.jpa.repository;

import com.cognifide.home.work.jpa.model.BookRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRecordRepository extends JpaRepository<BookRecord, Long> {

    List<BookRecord> findByIsbn(String isbn);

}
