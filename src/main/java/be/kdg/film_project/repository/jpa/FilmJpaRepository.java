package be.kdg.film_project.repository.jpa;

import be.kdg.film_project.domain.Film;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Profile("jpa")
public interface FilmJpaRepository extends JpaRepository<Film, Integer> {
    @Query("SELECT f FROM Film f JOIN f.actors a WHERE LOWER(a.actorName) LIKE '%' || LOWER(?1) || '%'")
    List<Film> findFilmByActors(String actorName);

    List<Film> findByFilmName(String filmName);
}
