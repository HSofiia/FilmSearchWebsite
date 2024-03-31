package be.kdg.film_project.repository.jpa;

import be.kdg.film_project.domain.FilmCasting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FilmCastingRepository extends JpaRepository<FilmCasting, Integer> {
    Optional<FilmCasting> findByFilmIdAndActorId(int filmID, int actorID);
}
