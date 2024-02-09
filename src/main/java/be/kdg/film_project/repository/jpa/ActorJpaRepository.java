package be.kdg.film_project.repository.jpa;

import be.kdg.film_project.domain.Actor;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Profile("jpa")
public interface ActorJpaRepository extends JpaRepository<Actor, Integer> {
    List<Actor> findByGender(Actor.Gender gender);

    List<Actor> findByGenderAndNationality(Actor.Gender gender, String nationality);
}

