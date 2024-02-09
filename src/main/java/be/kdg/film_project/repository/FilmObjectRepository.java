package be.kdg.film_project.repository;

import be.kdg.film_project.domain.Film;

import java.util.List;
import java.util.Optional;

public interface FilmObjectRepository {
    Film createFilm(Film film);

    List<Film> readFilms();

    void deleteFilm(int filmId);

    Optional<Film> getFilmById(Integer id);

    List<Film> getFilmByActors(String actorName);

    List<Film> getByFilmName(String filmName);
}
