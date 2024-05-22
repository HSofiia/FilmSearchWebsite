package be.kdg.film_project.controller.api.dto.film;

import be.kdg.film_project.domain.Film;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public class NewFilmDto {
    @NotBlank(message = "name is mandatory")
    private String filmName;

    @Enumerated(EnumType.STRING)
    private Film.Genre genre;

    @Positive(message = "The boxOffice should be positive")
    private Double boxOffice;

    @PastOrPresent(message = "release date should be in the past or present!")
    private LocalDate year;

    public NewFilmDto() {
    }

    public NewFilmDto(String filmName, LocalDate year, double boxOffice, Film.Genre genre) {
        this.filmName = filmName;
        this.year = year;
        this.boxOffice = boxOffice;
        this.genre = genre;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public LocalDate getYear() {
        return year;
    }

    public void setYear(LocalDate year) {
        this.year = year;
    }

    public double getBoxOffice() {
        return boxOffice;
    }

    public void setBoxOffice(double boxOffice) {
        this.boxOffice = boxOffice;
    }

    public Film.Genre getGenre() {
        return genre;
    }

    public void setGenre(Film.Genre genre) {
        this.genre = genre;
    }
}
