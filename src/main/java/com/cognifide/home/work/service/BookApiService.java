package com.cognifide.home.work.service;

import com.cognifide.home.work.jpa.model.Author;
import com.cognifide.home.work.jpa.model.BookRecord;
import com.cognifide.home.work.jpa.model.Category;
import com.cognifide.home.work.jpa.repository.BookRecordRepository;
import com.cognifide.home.work.model.BookUIRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookApiService {

    private final BookRecordRepository bookRecordRepository;

    @Autowired
    public BookApiService(BookRecordRepository bookRecordRepository) {
        this.bookRecordRepository = bookRecordRepository;
    }

    public List<BookUIRecord> listAllBooks() {
        List<BookUIRecord> books = new ArrayList<>();
        for (BookRecord bookRecord : bookRecordRepository.findAll()) {
            books.add(buildBookUIRecord(bookRecord));
        }

        return books;
    }

    public BookUIRecord getBookByIsbn(String isbn) {
        for (BookRecord bookRecord : bookRecordRepository.findByIsbn(isbn)) {
           return buildBookUIRecord(bookRecord);
        }

        return null;
    }


    public BookUIRecord buildBookUIRecord(BookRecord bookRecord) {

        BookUIRecord bookUIRecord = new BookUIRecord(bookRecord.getIsbn(), bookRecord.getTitle(),
                bookRecord.getSubtitle(), bookRecord.getPublisher(), bookRecord.getPublishedDate(), bookRecord.getDescription()
                , bookRecord.getPageCount(), bookRecord.getThumbnailUrl(), bookRecord.getLanguage(),
                bookRecord.getPreviewLink(), bookRecord.getAverageRating());

        for (Category category : bookRecord.getCategories()) {
            bookUIRecord.getCategories().add(category.getName());
        }

        for (Author author : bookRecord.getAuthors()) {
            bookUIRecord.getAuthors().add(author.getName());
        }

        return bookUIRecord;

    }

}
