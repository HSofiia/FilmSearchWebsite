package be.kdg.film_project.service;


import be.kdg.film_project.domain.Actor;
import be.kdg.film_project.presentation.exceptions.ActorException;
import be.kdg.film_project.repository.ActorObjectRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Profile("!jpa")
public class ActorServiceImp implements ActorService {
    private final ActorObjectRepository actorObjectRepository;

    public ActorServiceImp(ActorObjectRepository actorObjectRepository) {
        this.actorObjectRepository = actorObjectRepository;
    }

    @Override
    public Actor addActor(String name, Actor.Gender gender, String nationality) {
        return actorObjectRepository.createActor(new Actor(name, gender, nationality));
    }

    @Override
    public List<Actor> getActors() {
        return actorObjectRepository.readActor();
    }

    @Override
    public Actor getActorById(int id) {
        return actorObjectRepository.getActorById(id).orElseThrow(
                () -> new ActorException("Actor not found with id: " + id + "serviceimp"));
    }

    @Override
    public void deleteActor(int actorId) {
        actorObjectRepository.deleteActor(actorId);
    }

    @Override
    public List<Actor> getByGender(Actor.Gender gender) {
        return actorObjectRepository.getActorByGender(gender);
    }

    @Override
    public List<Actor> getByGenderAndNationality(Actor.Gender gender, String nationality) {
        return actorObjectRepository.getActorByGenderAndNationality(gender, nationality);
    }
}

