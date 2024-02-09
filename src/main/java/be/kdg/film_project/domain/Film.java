package be.kdg.film_project.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private transient LocalDate year;

    @Column(name = "box_office")
    private double boxOffice;

    @Column(name = "genre")
    @Enumerated(EnumType.STRING)
    private Genre genre;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToMany
    @JoinTable(
            name = "film_actor",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id")
    )

    @Cascade(CascadeType.PERSIST)
    private Set<Actor> actors;

    public Film() {
        this.actors = new HashSet<>();
    }

    public Film(int id, String filmName, LocalDate year, double boxOffice, Genre genre) {
        this.id = id;
        this.filmName = filmName;
        this.year = year;
        this.boxOffice = boxOffice;
        this.genre = genre;
        this.actors = new HashSet<>();
    }

    public Film(String filmName, LocalDate year, double boxOffice, Genre genre) {
        this.filmName = filmName;
        this.year = year;
        this.boxOffice = boxOffice;
        this.genre = genre;
        this.actors = new HashSet<>();
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

    public Set<Actor> getActors() {
        return actors;
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

    public void setActors(Set<Actor> actors) {
        this.actors = actors;
    }

    public Director addDirector(Director director) {
        directors.add(director);
        return director;
    }

    public void addActor(Actor actor) {
        if (this.actors == null)
            this.actors = new HashSet<>();

        this.actors.add(actor);
        actor.getFilms().add(this);
    }


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


