package be.kdg.film_project.presentation.viewmodels;

import be.kdg.film_project.domain.Film;
import be.kdg.film_project.domain.FilmCasting;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import nonapi.io.github.classgraph.json.Id;

import java.time.LocalDate;
import java.util.List;

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

    private List<ActorViewModel> castings;

    public FilmViewModel() {
    }

    public FilmViewModel(Integer filmId, String filmName, Film.Genre genre, Double boxOffice, LocalDate year) {
        this.filmId = filmId;
        this.filmName = filmName;
        this.genre = genre;
        this.boxOffice = boxOffice;
        this.year = year;
    }

    public FilmViewModel(Integer filmId, String filmName, Film.Genre genre, Double boxOffice, LocalDate year, List<ActorViewModel> castings) {
        this.filmId = filmId;
        this.filmName = filmName;
        this.genre = genre;
        this.boxOffice = boxOffice;
        this.year = year;
        this.castings = castings;
    }

    public FilmViewModel(String filmName, Film.Genre genre, Double boxOffice, LocalDate year) {
        this.filmName = filmName;
        this.genre = genre;
        this.boxOffice = boxOffice;
        this.year = year;
    }

    public Integer getFilmId() {
        return filmId;
    }

    public void setFilmId(Integer filmId) {
        this.filmId = filmId;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public Film.Genre getGenre() {
        return genre;
    }

    public void setGenre(Film.Genre genre) {
        this.genre = genre;
    }

    public Double getBoxOffice() {
        return boxOffice;
    }

    public void setBoxOffice(Double boxOffice) {
        this.boxOffice = boxOffice;
    }

    public LocalDate getYear() {
        return year;
    }

    public void setYear(LocalDate year) {
        this.year = year;
    }

    public List<ActorViewModel> getCastings() {
        return castings;
    }

    public void setCastings(List<ActorViewModel> castings) {
        this.castings = castings;
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
