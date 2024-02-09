package be.kdg.film_project.presentation.viewmodels;

import be.kdg.film_project.domain.Film;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import nonapi.io.github.classgraph.json.Id;

import java.time.LocalDate;

public class FilmViewModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer filmId;

    @NotBlank(message = "name is mandatory")
    private String filmName;

    @Enumerated(EnumType.STRING)
    private Film.Genre genre;

    @Positive(message = "The boxOffice should be positive")
    private Double boxOffice;

    @PastOrPresent(message = "release date should be in the past or present!")
    private LocalDate year;

    public FilmViewModel(Integer filmId, String filmName, Film.Genre genre, Double boxOffice, LocalDate year) {
        this.filmId = filmId;
        this.filmName = filmName;
        this.genre = genre;
        this.boxOffice = boxOffice;
        this.year = year;
    }

    public FilmViewModel() {
    }

    public void setFilmId(Integer filmId) {
        this.filmId = filmId;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public void setGenre(Film.Genre genre) {
        this.genre = genre;
    }

    public void setBoxOffice(Double boxOffice) {
        this.boxOffice = boxOffice;
    }

    public void setYear(LocalDate year) {
        this.year = year;
    }

    public Integer getFilmId() {
        return filmId;
    }

    public String getFilmName() {
        return filmName;
    }

    public Film.Genre getGenre() {
        return genre;
    }

    public Double getBoxOffice() {
        return boxOffice;
    }

    public LocalDate getYear() {
        return year;
    }

    @Override
    public String toString() {
        return "FilmViewModel{" +
                "filmId=" + filmId +
                ", filmName='" + filmName + '\'' +
                ", genre=" + genre +
                ", boxOffice=" + boxOffice +
                ", year=" + year +
                '}';
    }
}
