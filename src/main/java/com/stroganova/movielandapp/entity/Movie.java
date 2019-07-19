package com.stroganova.movielandapp.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.stroganova.movielandapp.web.json.serializer.DoubleSerializer;
import com.stroganova.movielandapp.web.json.serializer.YearLocalDateSerializer;

import java.time.LocalDate;

public class Movie {
    private long id;
    private String nameRussian;
    private String nameNative;
    @JsonSerialize(using = YearLocalDateSerializer.class)
    private LocalDate yearOfRelease;
    @JsonSerialize(using = DoubleSerializer.class)
    private double rating;
    @JsonSerialize(using = DoubleSerializer.class)
    private double price;
    private String picturePath;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNameRussian() {
        return nameRussian;
    }

    public void setNameRussian(String nameRussian) {
        this.nameRussian = nameRussian;
    }

    public String getNameNative() {
        return nameNative;
    }

    public void setNameNative(String nameNative) {
        this.nameNative = nameNative;
    }

    public LocalDate getYearOfRelease() {
        return yearOfRelease;
    }

    public void setYearOfRelease(LocalDate yearOfRelease) {
        this.yearOfRelease = yearOfRelease;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", nameRussian='" + nameRussian + '\'' +
                ", nameNative='" + nameNative + '\'' +
                ", yearOfRelease=" + yearOfRelease +
                ", rating=" + rating +
                ", price=" + price +
                ", picturePath='" + picturePath + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Movie)) return false;

        Movie movie = (Movie) o;

        return (Double.compare(movie.rating, rating) == 0)
                && (Double.compare(movie.price, price) == 0)
                && (nameRussian != null ? nameRussian.equals(movie.nameRussian) : movie.nameRussian == null)
                && (nameNative != null ? nameNative.equals(movie.nameNative) : movie.nameNative == null)
                && (yearOfRelease != null ? yearOfRelease.equals(movie.yearOfRelease) : movie.yearOfRelease == null)
                && (picturePath != null ? picturePath.equals(movie.picturePath) : movie.picturePath == null);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = nameRussian != null ? nameRussian.hashCode() : 0;
        result = 31 * result + (nameNative != null ? nameNative.hashCode() : 0);
        result = 31 * result + (yearOfRelease != null ? yearOfRelease.hashCode() : 0);
        temp = Double.doubleToLongBits(rating);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (picturePath != null ? picturePath.hashCode() : 0);
        return result;
    }
}
