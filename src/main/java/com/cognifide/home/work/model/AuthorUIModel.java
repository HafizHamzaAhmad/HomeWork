package com.cognifide.home.work.model;

public class AuthorUIModel implements Comparable<AuthorUIModel>{
    private String author;
    private Double averageRating;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    @Override
    public int compareTo(AuthorUIModel authorUIModel) {
        return authorUIModel.getAverageRating().compareTo(this.getAverageRating());
    }
}
