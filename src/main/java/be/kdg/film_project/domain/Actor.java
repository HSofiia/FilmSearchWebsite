package be.kdg.film_project.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "actor")
public class Actor {
    public enum Gender {
        M, F, N;
    }

    @Column(name = "actor_name")
    private String actorName;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "nationality")
    private String nationality;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToMany(mappedBy = "actors", fetch = FetchType.EAGER) // lazy is a better use
    @Cascade(org.hibernate.annotations.CascadeType.PERSIST)
    private Set<Film> films;

    public Actor() {
        this.films = new HashSet<>();
    }

    public Actor(int id, String actorName, Gender gender, String nationality) {
        this.id = id;
        this.actorName = actorName;
        this.gender = gender;
        this.nationality = nationality;
        this.films = new HashSet<>();
    }

    public Actor(String actorName, Gender gender, String nationality) {
        this.actorName = actorName;
        this.gender = gender;
        this.nationality = nationality;
        this.films = new HashSet<>();
    }

    public String getActorName() {
        return actorName;
    }

    public Gender getGender() {
        return gender;
    }

    public String getNationality() {
        return nationality;
    }

    public int getId() {
        return id;
    }

    public Set<Film> getFilms() {
        return films;
    }

    public void setFilms(Set<Film> films) {
        this.films = films;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Film addFilm(Film film) {
        films.add(film);
        return film;
    }


    @Override
    public String toString() {
        return "Actors: " +
                "actorName=" + actorName +
                ", gender=" + gender +
                ", nationality=" + nationality +
                ' ';
    }
}
