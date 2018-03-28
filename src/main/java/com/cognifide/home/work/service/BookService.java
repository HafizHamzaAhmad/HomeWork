package com.cognifide.home.work.service;

import com.cognifide.home.work.jpa.model.Author;
import com.cognifide.home.work.jpa.model.BookRecord;
import com.cognifide.home.work.jpa.model.Category;
import com.cognifide.home.work.jpa.repository.AuthorRepository;
import com.cognifide.home.work.jpa.repository.BookRecordRepository;
import com.cognifide.home.work.jpa.repository.CategoryRepository;
import com.cognifide.home.work.model.IndustryIdentifier;
import com.cognifide.home.work.model.Item;
import com.cognifide.home.work.model.VolumeInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BookService {

    private final BookRecordRepository bookRecordRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public BookService(BookRecordRepository bookRecordRepository, AuthorRepository authorRepository, CategoryRepository categoryRepository) {
        this.bookRecordRepository = bookRecordRepository;
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
    }


    public BookRecord saveBookrecord(Item item) {

        BookRecord bookRecord = new BookRecord();
        VolumeInfo volumeInfo = item.getVolumeInfo();
        bookRecord.setIsbn(getIsbn(volumeInfo.getIndustryIdentifiers()));
        bookRecord.setTitle(volumeInfo.getTitle());
        bookRecord.setSubtitle(volumeInfo.getSubtitle());
        bookRecord.setPublisher(volumeInfo.getPublisher());
        bookRecord.setPublishedDate(volumeInfo.getPublishedDate() != null ? parseDate(volumeInfo.getPublishedDate()) : null);
        bookRecord.setDescription(volumeInfo.getDescription());
        bookRecord.setPageCount(volumeInfo.getPageCount());
        bookRecord.setThumbnailUrl(volumeInfo.getImageLinks().getThumbnail());
        bookRecord.setLanguage(volumeInfo.getLanguage());
        bookRecord.setPreviewLink(volumeInfo.getPreviewLink());
        bookRecord.setAverageRating(volumeInfo.getAverageRating());

        bookRecord = bookRecordRepository.save(bookRecord);

        bookRecord.setAuthors(buildAuthors(volumeInfo.getAuthors(), bookRecord));
        bookRecord.setCategories(buildCategories(volumeInfo.getCategories(), bookRecord));

        bookRecord = bookRecordRepository.save(bookRecord);

        return bookRecord;
    }

    private Long parseDate(String publishedDate) {
        Date date;
        long unixTimeStamp = 0;
        final String[] formats = {"yyyy-MM-dd", "yyyy"};
        for (String parse : formats) {
            try {
                date = new SimpleDateFormat(parse).parse(publishedDate);
                unixTimeStamp = date.getTime() / 1000;
                break;
            } catch (ParseException e) {
            }
        }
        return unixTimeStamp;
    }


    private String getIsbn(List<IndustryIdentifier> industryIdentifierList) {
        String isbn = "";
        for (IndustryIdentifier identifier : industryIdentifierList) {
            isbn = identifier.getType().equals("ISBN_13") ? identifier.getIdentifier() : "";
        }
        return isbn;
    }

    private List<Category> buildCategories(List<String> categories, BookRecord bookRecord) {
        if (categories == null) {
            return new ArrayList<>();
        }

        List<Category> categoriesList = new ArrayList<>();

        for (String item : categories) {

            Category category = categoryRepository.findByName(item);

            if (category == null) {
                category = new Category();
                category.setName(item);
                category = categoryRepository.save(category);
            }
            category.getBookRecords().add(bookRecord);
            categoriesList.add(category);
        }

        return categoriesList;
    }

    private List<Author> buildAuthors(List<String> authors, BookRecord bookRecord) {
        if (authors == null) {
            return new ArrayList<>();
        }
        List<Author> authorsList = new ArrayList<>();
        for (String item : authors) {

            Author author = authorRepository.findByName(item);
            if (author == null) {
                author = new Author();
                author.setName(item);
                author = authorRepository.save(author);
            }
            author.getBookRecords().add(bookRecord);
            authorsList.add(author);
        }

        return authorsList;
    }


}
