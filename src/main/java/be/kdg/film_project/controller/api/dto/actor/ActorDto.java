package be.kdg.film_project.controller.api.dto.actor;

import be.kdg.film_project.domain.Actor;


public class ActorDto {
    private String actorName;
    private Actor.Gender gender;
    private String nationality;
    private int id;

    public ActorDto() {
    }

    public ActorDto(String actorName, Actor.Gender gender, String nationality, int id) {
        this.actorName = actorName;
        this.gender = gender;
        this.nationality = nationality;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
