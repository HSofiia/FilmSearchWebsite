package be.kdg.film_project.service;

import be.kdg.film_project.domain.Actor;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface ActorService {
    Actor addActor(String name, Actor.Gender gender, String nationality);

    List<Actor> getActors();

    Actor getActor(int actorId);

    Actor getActorWithFilms(int id);

    boolean deleteActor(int actorId);

    boolean updateActorInfo(int actorId, Actor.Gender gender, String nationality);

    List<Actor> getByGenderAndNationality(Actor.Gender gender, String nationality);
}
