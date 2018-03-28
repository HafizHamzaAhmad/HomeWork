package com.cognifide.home.work.service;

import com.cognifide.home.work.jpa.model.Author;
import com.cognifide.home.work.jpa.model.BookRecord;
import com.cognifide.home.work.jpa.model.Category;
import com.cognifide.home.work.jpa.repository.BookRecordRepository;
import com.cognifide.home.work.jpa.repository.CategoryRepository;
import com.cognifide.home.work.model.BookUIRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryApiService {
    private final CategoryRepository categoryRepository;

    private final BookApiService bookApiService;

    @Autowired
    public CategoryApiService(CategoryRepository categoryRepository, BookApiService bookApiService) {
        this.categoryRepository = categoryRepository;
        this.bookApiService = bookApiService;
    }

    public List<BookUIRecord> listBooks(String name) {
        List<BookUIRecord> books = new ArrayList<>();
        Category category = categoryRepository.findByName(name);
        if(category == null)
            return books;
        for (BookRecord bookRecord : category.getBookRecords()) {
            books.add(bookApiService.buildBookUIRecord(bookRecord));
        }
        return books;
    }

    public List<Category> listAllCategories() {
        return categoryRepository.findAll();
    }


}
