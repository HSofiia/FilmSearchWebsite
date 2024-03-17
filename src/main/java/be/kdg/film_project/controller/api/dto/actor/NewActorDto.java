package be.kdg.film_project.controller.api.dto.actor;

import be.kdg.film_project.domain.Actor;

public class NewActorDto {
    private String actorName;
    private Actor.Gender gender;
    private String nationality;

    public NewActorDto() {
    }

    public NewActorDto(String actorName, Actor.Gender gender, String nationality) {
        this.actorName = actorName;
        this.gender = gender;
        this.nationality = nationality;
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
}
