package be.kdg.film_project.domain;

import jakarta.persistence.*;

import java.util.List;

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

//    CASCADE??
    @OneToMany(mappedBy = "actor", cascade = CascadeType.REMOVE)
    private List<FilmCasting> film;

    public Actor() {
    }

    public Actor(String actorName, Gender gender, String nationality) {
        this.actorName = actorName;
        this.gender = gender;
        this.nationality = nationality;
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

    public List<FilmCasting> getFilm() {
        return film;
    }

    public void setFilm(List<FilmCasting> filmCastings) {
        this.film = filmCastings;
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

    @Override
    public String toString() {
        return "Actors: " +
                "actorName=" + actorName +
                ", gender=" + gender +
                ", nationality=" + nationality +
                ' ';
    }
}
