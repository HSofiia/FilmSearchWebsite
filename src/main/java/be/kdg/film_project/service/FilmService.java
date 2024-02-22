package be.kdg.film_project.service;

import be.kdg.film_project.domain.Film;

import java.time.LocalDate;
import java.util.List;

public interface FilmService {
    Film addFilm(String filmName, LocalDate year, double boxOffice, Film.Genre genre);

    List<Film> getFilms();

    Film getFilmWithActors(int id);

    void deleteFilm(int filmId);

    List<Film> getByName(String name);

    List<Film> getByActors(String actorName);
}
