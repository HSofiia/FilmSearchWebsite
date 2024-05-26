package be.kdg.film_project.service.impl;

import be.kdg.film_project.domain.Actor;
import be.kdg.film_project.repository.jpa.ActorJpaRepository;
import be.kdg.film_project.service.ActorService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActorJpaService implements ActorService {
    private final ActorJpaRepository actorJpaRepository;

    public ActorJpaService(ActorJpaRepository actorJpaRepository) {
        this.actorJpaRepository = actorJpaRepository;
    }

    @Override
    @CacheEvict(value = "searchActors", allEntries = true)
    public Actor addActor(String name, Actor.Gender gender, String nationality) {
        if (name == null || gender == null || nationality == null) {
            throw new IllegalArgumentException("Name, gender, and nationality cannot be null");
        }
        Actor actor = new Actor(name, gender, nationality);
        return actorJpaRepository.save(actor);
    }

    @Override
    public Actor getActor(int actorId) {
        return actorJpaRepository.findById(actorId).orElse(null);
    }

    @Override
    public List<Actor> getActors() {
        return actorJpaRepository.findAll();
    }

    @Override
    public Actor getActorWithFilms(int id) {
        return actorJpaRepository.findByIdWithFilms(id).orElse(null);
    }

    @Override
    @CacheEvict(value = "searchActors", allEntries = true)
    public boolean deleteActor(int actorId) {
        var actor = actorJpaRepository.findByIdWithRelatedFilm(actorId);
        if (actor.isEmpty()) {
            return false;
        }
        actorJpaRepository.deleteById(actorId);
        return true;
    }

    @Override
    @CacheEvict(value = "searchActors", allEntries = true)
    public boolean updateActorInfo(int actorId, Actor.Gender gender, String nationality) {
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
    @Cacheable(value = "searchActors")
    public List<Actor> getByGenderAndNationality(Actor.Gender gender, String nationality) {
        return actorJpaRepository.findByGenderAndNationality(gender, nationality);
    }

}

