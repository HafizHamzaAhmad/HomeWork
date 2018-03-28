package com.cognifide.home.work.jpa.repository;

import com.cognifide.home.work.jpa.model.Author;
import com.cognifide.home.work.jpa.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByName(String name);
}
