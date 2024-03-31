package be.kdg.film_project.controller.mvc.viewmodels;

import be.kdg.film_project.domain.Film;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;

public class FilmViewModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "name is mandatory")
    private String filmName;

    @Enumerated(EnumType.STRING)
    private Film.Genre genre;

    @Positive(message = "The boxOffice should be positive")
    private Double boxOffice;

    @PastOrPresent(message = "release date should be in the past or present!")
    private LocalDate year;

    private List<ActorViewModel> castings;

    private boolean modificationAllowed;

    public FilmViewModel() {
    }

    public FilmViewModel(Integer filmId, String filmName, Film.Genre genre, Double boxOffice, LocalDate year, boolean modificationAllowed) {
        this.id = filmId;
        this.filmName = filmName;
        this.genre = genre;
        this.boxOffice = boxOffice;
        this.year = year;
        this.modificationAllowed = modificationAllowed;
    }

    public FilmViewModel(Integer filmId, String filmName, Film.Genre genre, Double boxOffice, LocalDate year,boolean modificationAllowed, List<ActorViewModel> castings) {
        this.id = filmId;
        this.filmName = filmName;
        this.genre = genre;
        this.boxOffice = boxOffice;
        this.year = year;
        this.modificationAllowed = modificationAllowed;
        this.castings = castings;
    }

    public FilmViewModel(String filmName, Film.Genre genre, Double boxOffice, LocalDate year) {
        this.filmName = filmName;
        this.genre = genre;
        this.boxOffice = boxOffice;
        this.year = year;
    }

    public FilmViewModel(Integer id, String filmName, Film.Genre genre, Double boxOffice, LocalDate year) {
        this.id = id;
        this.filmName = filmName;
        this.genre = genre;
        this.boxOffice = boxOffice;
        this.year = year;
    }

    public Integer getFilmId() {
        return id;
    }

    public void setFilmId(Integer filmId) {
        this.id = filmId;
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

    public boolean isModificationAllowed() {
        return modificationAllowed;
    }

    public void setModificationAllowed(boolean modificationAllowed) {
        this.modificationAllowed = modificationAllowed;
    }

    @Override
    public String toString() {
        return "FilmViewModel{" +
                "filmId=" + id +
                ", filmName='" + filmName + '\'' +
                ", genre=" + genre +
                ", boxOffice=" + boxOffice +
                ", year=" + year +
                '}';
    }
}
