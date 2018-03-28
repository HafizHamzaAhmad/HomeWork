package com.cognifide.home.work;

import com.cognifide.home.work.jpa.model.BookRecord;
import com.cognifide.home.work.jpa.repository.BookRecordRepository;
import com.cognifide.home.work.model.Book;
import com.cognifide.home.work.model.Item;
import com.cognifide.home.work.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Service
    class ApplicationLoader implements CommandLineRunner {
        private Logger logger = LoggerFactory.getLogger(ApplicationLoader.class);
        private final ObjectMapper objectMapper;

        private final BookService bookService;

        private final BookRecordRepository bookRecordRepository;

        @Autowired
        public ApplicationLoader(ObjectMapper objectMapper, BookService bookService, BookRecordRepository bookRecordRepository) {
            this.objectMapper = objectMapper;
            this.bookService = bookService;
            this.bookRecordRepository = bookRecordRepository;
        }

        @Override
        public void run(String... args) throws Exception {
            String dataSource=System.getProperty("datasource");

            Book book;
            if (dataSource != null && !dataSource.isEmpty()) {
                book = objectMapper.readValue(new File(dataSource), Book.class);
            } else {
                book = objectMapper.readValue(new ClassPathResource("books.json").getFile(), Book.class);
            }


            for (Item item : book.getItems()) {
                bookService.saveBookrecord(item);
            }

            logger.info(bookRecordRepository.findAll().size() + " record saved");


        }
    }
}
