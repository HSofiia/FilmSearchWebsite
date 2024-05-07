package be.kdg.film_project.controller.api.dto.film;

import be.kdg.film_project.domain.Film;

import java.time.LocalDate;

public class UpdateFilmDto {
    private LocalDate year;
    private double boxOffice;
    private Film.Genre genre;

    public UpdateFilmDto() {
    }

    public UpdateFilmDto(LocalDate year, double boxOffice, Film.Genre genre) {
        this.year = year;
        this.boxOffice = boxOffice;
        this.genre = genre;
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
