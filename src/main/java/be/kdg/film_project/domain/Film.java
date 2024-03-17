package be.kdg.film_project.domain;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Table(name = "film")
@Entity
public class Film {
    public enum Genre {
        HISTORY, SPY, FANTASY, ROMANCE, ACTION
    }

    private transient final List<Director> directors = new ArrayList<>();

    @Column(name = "film_name")
    private String filmName;

    @Column(name = "release_year")
    private LocalDate year;

    @Column(name = "box_office")
    private double boxOffice;

    @Column(name = "genre")
    @Enumerated(EnumType.STRING)
    private Genre genre;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(mappedBy = "film")
    private List<FilmCasting> castings;

    public Film() {
    }

    public Film(String filmName, LocalDate year, double boxOffice, Genre genre) {
        this.filmName = filmName;
        this.year = year;
        this.boxOffice = boxOffice;
        this.genre = genre;
    }

    public int getId() {
        return id;
    }

    public LocalDate getYear() {
        return year;
    }

    public String getFilmName() {
        return filmName;
    }

    public double getBoxOffice() {
        return boxOffice;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public void setYear(LocalDate year) {
        this.year = year;
    }

    public void setBoxOffice(double boxOffice) {
        this.boxOffice = boxOffice;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public List<FilmCasting> getCastings() {
        return castings;
    }

    public void setCastings(List<FilmCasting> castings) {
        this.castings = castings;
    }

//    public Director addDirector(Director director) {
//        directors.add(director);
//        return director;
//    }

    @Override
    public String toString() {
        return "Films: " +
                "filmName=" + filmName +
                ", year=" + year +
                ", boxOffice=" + boxOffice +
                ", genre=" + genre +
                ' ';
    }
}


