package be.kdg.film_project.service;

import be.kdg.film_project.domain.Actor;
import be.kdg.film_project.domain.Film;

import java.time.LocalDate;
import java.util.List;

public interface FilmService {
    Film addFilm(String filmName, LocalDate year, double boxOffice, Film.Genre genre);

    List<Film> getFilms();

    Film getFilm(int filmId);

    Film getFilmWithActors(int id);

    boolean deleteFilm(int filmId);

    List<Film> getByName(String name);

    List<Film> getByActors(String actorName);
}
