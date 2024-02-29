package be.kdg.film_project.service;

import be.kdg.film_project.domain.Actor;

import java.util.List;

public interface ActorService {
    Actor addActor(String name, Actor.Gender gender, String nationality);

    List<Actor> getActors();

    Actor getActor(int actorId);

    Actor getActorWithFilms(int id);

    boolean deleteActor(int actorId);

    List<Actor> getByGender(Actor.Gender gender);

    List<Actor> getByGenderAndNationality(Actor.Gender gender, String nationality);
}
