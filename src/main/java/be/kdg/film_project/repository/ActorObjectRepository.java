package be.kdg.film_project.repository;

import be.kdg.film_project.domain.Actor;

import java.util.List;
import java.util.Optional;

public interface ActorObjectRepository {
    Actor createActor(Actor actor);

    List<Actor> readActor();

    void deleteActor(int actorId);

    Optional<Actor> getActorById(Integer id);

    List<Actor> getActorByGender(Actor.Gender gender);

    List<Actor> getActorByGenderAndNationality(Actor.Gender gender, String nationality);
}
