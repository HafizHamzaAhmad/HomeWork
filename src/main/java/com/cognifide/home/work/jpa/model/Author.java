package com.cognifide.home.work.jpa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToMany;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Author implements Serializable{

    @JsonIgnore
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "authors")
    private List<BookRecord> bookRecords=new ArrayList<>();

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BookRecord> getBookRecords() {
        return bookRecords;
    }

    public void setBookRecords(List<BookRecord> bookRecords) {
        this.bookRecords = bookRecords;
    }
}
