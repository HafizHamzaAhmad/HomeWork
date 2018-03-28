package com.cognifide.home.work.controller;


import com.cognifide.home.work.jpa.model.BookRecord;
import com.cognifide.home.work.jpa.model.Category;
import com.cognifide.home.work.model.AuthorUIModel;
import com.cognifide.home.work.model.BookUIRecord;
import com.cognifide.home.work.service.AuthorApiService;
import com.cognifide.home.work.service.BookApiService;
import com.cognifide.home.work.service.CategoryApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class Controller {
    private static final Logger logger = LoggerFactory.getLogger(Controller.class);

    private final BookApiService bookApiService;

    private final CategoryApiService categoryApiService;

    private final AuthorApiService authorApiService;

    @Autowired
    public Controller(BookApiService bookApiService, CategoryApiService categoryApiService, AuthorApiService authorApiService) {
        this.bookApiService = bookApiService;
        this.categoryApiService = categoryApiService;
        this.authorApiService = authorApiService;
    }

    @RequestMapping(value = "/book", method = RequestMethod.GET)
    public ResponseEntity<?> listAllBooks() {
        List<BookUIRecord> books = bookApiService.listAllBooks();
        if (books.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @RequestMapping(value = "/categories", method = RequestMethod.GET)
    public ResponseEntity<?> listAllCategories() {
        List<Category> categories = categoryApiService.listAllCategories();
        if (categories.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }


    @RequestMapping(value = "/book/{isbn}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getBookByIsbn(@PathVariable("isbn") String isbn) {
        logger.info("Fetching BookRecord with isbn {}", isbn);
        BookUIRecord book = bookApiService.getBookByIsbn(isbn);
        if (book == null) {
            logger.error("BookRecord with isbn {} not found.", isbn);
            return new ResponseEntity<>(new CustomErrorType(404,
                    "No Results found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(book, HttpStatus.OK);
    }


    @RequestMapping(value = "/category/{categoryName}/books", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getCategoriesByName(@PathVariable("categoryName") String catName) {
        List<BookUIRecord> books = categoryApiService.listBooks(catName);
        if (books.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @RequestMapping(value = "/rating", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getRating() {
        List<AuthorUIModel> authorUIModels = authorApiService.authorRatings();
        if (authorUIModels.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(authorUIModels, HttpStatus.OK);
    }


}
