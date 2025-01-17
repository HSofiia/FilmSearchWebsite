package be.kdg.film_project.controller.mvc.viewmodels;

import be.kdg.film_project.domain.Actor;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public class ActorViewModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "actorName should not be empty!")
    private String actorName;

    @Enumerated(EnumType.STRING)
    private Actor.Gender gender;

    @NotBlank(message = "Nationality is mandatory")
    @Size(min = 3, max = 70, message = "Name should have length between 3 and 70")
    private String nationality;

    private List<FilmViewModel> film;

    private boolean modificationAllowed;


    public ActorViewModel() {
    }

    public ActorViewModel(Integer id, String actorName, Actor.Gender gender, String nationality,boolean modificationAllowed) {
        this.id = id;
        this.actorName = actorName;
        this.gender = gender;
        this.nationality = nationality;
        this.modificationAllowed = modificationAllowed;

    }

    public ActorViewModel(Integer id, String actorName, Actor.Gender gender, String nationality,boolean modificationAllowed, List<FilmViewModel> film) {
        this.id = id;
        this.actorName = actorName;
        this.gender = gender;
        this.nationality = nationality;
        this.modificationAllowed = modificationAllowed;
        this.film = film;
    }

    public ActorViewModel(Integer id, String actorName, Actor.Gender gender, String nationality) {
        this.id = id;
        this.actorName = actorName;
        this.gender = gender;
        this.nationality = nationality;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getActorName() {
        return actorName;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    public Actor.Gender getGender() {
        return gender;
    }

    public void setGender(Actor.Gender gender) {
        this.gender = gender;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public List<FilmViewModel> getFilm() {
        return film;
    }

    public void setFilm(List<FilmViewModel> film) {
        this.film = film;
    }

    public boolean isModificationAllowed() {
        return modificationAllowed;
    }

    public void setModificationAllowed(boolean modificationAllowed) {
        this.modificationAllowed = modificationAllowed;
    }

    @Override
    public String toString() {
        return "ActorViewModel{" +
                "id=" + id +
                ", actorName='" + actorName + '\'' +
                ", gender=" + gender +
                ", nationality='" + nationality + '\'' +
                '}';
    }
}
