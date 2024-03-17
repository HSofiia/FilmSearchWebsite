package be.kdg.film_project.controller.api.dto.film;

import be.kdg.film_project.domain.Film;

import java.time.LocalDate;

public class NewFilmDto {
    private String filmName;
    private LocalDate year;
    private double boxOffice;
    private Film.Genre genre;

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
