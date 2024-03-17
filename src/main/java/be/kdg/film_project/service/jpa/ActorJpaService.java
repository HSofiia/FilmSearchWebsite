package be.kdg.film_project.service.jpa;

import be.kdg.film_project.domain.Actor;
import be.kdg.film_project.repository.jpa.ActorJpaRepository;
import be.kdg.film_project.service.ActorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
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
    public Actor getActor(int actorId) {
        return actorJpaRepository.findById(actorId).orElse(null);
    }

    @Override
    @Transactional
    public List<Actor> getActors() {
        return actorJpaRepository.findAll();
    }

    @Override
    public Actor getActorWithFilms(int id) {
        return actorJpaRepository.findByIdWithFilms(id).orElse(null);
    }

    @Override
    @Transactional
    public boolean deleteActor(int actorId) {
        var actor = actorJpaRepository.findByIdWithRelatedFilm(actorId);
        if (actor.isEmpty()) {
            return false;
        }
        actorJpaRepository.deleteById(actorId);
        return true;
    }

    @Override
    public boolean changeActorInfo(int actorId, Actor.Gender gender, String nationality) {
        var actor = actorJpaRepository.findById(actorId).orElse(null);
        if (actor == null) {
            return false;
        }
        actor.setGender(gender);
        actor.setNationality(nationality);
        actorJpaRepository.save(actor);
        return true;
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

