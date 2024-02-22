package be.kdg.film_project.repository.jpa;

import be.kdg.film_project.domain.Actor;
import be.kdg.film_project.domain.Film;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FilmJpaRepository extends JpaRepository<Film, Integer> {
    @Query("SELECT DISTINCT fc.film FROM FilmCasting fc JOIN fc.actor a WHERE LOWER(a.actorName) LIKE %:actorName%")
    List<Film> findFilmsByActorName(@Param("actorName") String actorName);

    List<Film> findByFilmName(String filmName);

    @Query("""
            select film from Film film
            left join fetch film.castings filmCastings
            left join fetch filmCastings.actor
            where film.id = :filmId
            """)
    Optional<Film> findByIdWithActors(long filmId);
}
