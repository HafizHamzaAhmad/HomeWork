package com.cognifide.home.work.service;

import com.cognifide.home.work.jpa.model.Author;
import com.cognifide.home.work.jpa.model.BookRecord;
import com.cognifide.home.work.jpa.repository.AuthorRepository;
import com.cognifide.home.work.jpa.repository.CategoryRepository;
import com.cognifide.home.work.model.AuthorUIModel;
import com.cognifide.home.work.model.BookUIRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class AuthorApiService {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorApiService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<AuthorUIModel> authorRatings() {
        List<AuthorUIModel> authorUIModels = new ArrayList<>();


        for (Author author : authorRepository.findAll()) {
            AuthorUIModel authorUIModel = new AuthorUIModel();
            authorUIModel.setAuthor(author.getName());

            Double rating = 0.0;
            if (!author.getBookRecords().isEmpty()) {
                for (BookRecord bookRecord : author.getBookRecords()) {
                    rating += bookRecord.getAverageRating();
                }

                rating = rating / author.getBookRecords().size();
            }

            authorUIModel.setAverageRating(rating);
            if (rating != 0.0) {
                authorUIModels.add(authorUIModel);
            }

        }

        authorUIModels.sort(AuthorUIModel::compareTo);

        return authorUIModels;
    }


}
