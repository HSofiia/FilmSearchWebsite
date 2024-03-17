package be.kdg.film_project.controller.api.dto.actor;

import be.kdg.film_project.domain.Actor;

public class UpdateActorDto {
    private Actor.Gender gender;
    private String nationality;

    public UpdateActorDto() {
    }

    public UpdateActorDto(Actor.Gender gender, String nationality) {
        this.gender = gender;
        this.nationality = nationality;
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
