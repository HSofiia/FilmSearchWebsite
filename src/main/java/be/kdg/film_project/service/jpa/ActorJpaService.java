package be.kdg.film_project.service.jpa;

import be.kdg.film_project.domain.Actor;
import be.kdg.film_project.repository.jpa.ActorJpaRepository;
import be.kdg.film_project.service.ActorService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Profile("jpa")
public class ActorJpaService implements ActorService {
    private final ActorJpaRepository actorJpaRepository;

    public ActorJpaService(ActorJpaRepository actorJpaRepository) {
        this.actorJpaRepository = actorJpaRepository;
    }

    @Override
    @Transactional
    public Actor addActor(String name, Actor.Gender gender, String nationality) {
        Actor actor = new Actor(name, gender, nationality);
        return actorJpaRepository.save(actor);
    }

    @Override
    @Transactional
    public List<Actor> getActors() {
        return actorJpaRepository.findAll();
    }

    @Override
    public Actor getActorById(int id) {
        List<Actor> actors = actorJpaRepository.findAll();
        for (Actor actor : actors) {
            if (actor.getId() == id) {
                return actor;
            }
        }
        return null;
    }

    @Override
    @Transactional
    public void deleteActor(int actorId) {
        actorJpaRepository.deleteById(actorId);
    }

    @Override
    public List<Actor> getByGender(Actor.Gender gender) {
        return actorJpaRepository.findByGender(gender);
    }

    @Override
    public List<Actor> getByGenderAndNationality(Actor.Gender gender, String nationality) {
        return actorJpaRepository.findByGenderAndNationality(gender, nationality);
    }
}

