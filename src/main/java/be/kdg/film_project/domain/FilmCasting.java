package be.kdg.film_project.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "film_id", "actor_id" }) })
public class FilmCasting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "film_id")
    private Film film;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "actor_id")
    private Actor actor;


    public FilmCasting() {
    }

    public FilmCasting(int film_casting_id, Film film, Actor actor) {
        this.id = film_casting_id;
        this.film = film;
        this.actor = actor;
    }

    public FilmCasting(Film film, Actor actor) {
        this.film = film;
        this.actor = actor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

}
