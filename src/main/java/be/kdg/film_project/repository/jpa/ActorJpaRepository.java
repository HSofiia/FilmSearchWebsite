package be.kdg.film_project.repository.jpa;

import be.kdg.film_project.domain.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ActorJpaRepository extends JpaRepository<Actor, Integer> {
    List<Actor> findByGender(Actor.Gender gender);

    List<Actor> findByGenderAndNationality(Actor.Gender gender, String nationality);

    @Query("""
            select actor from Actor actor
            left join fetch actor.film filmCastings
            left join fetch filmCastings.film
            where actor.id = :actorId
            """)
    Optional<Actor> findByIdWithFilms(int actorId);

    @Query("""
           select actor from Actor actor
           left join fetch actor.film
           where actor.id = :actorId
           """)
    Optional<Actor> findByIdWithRelatedFilm(int actorId);
}

