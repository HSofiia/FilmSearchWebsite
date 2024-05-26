package be.kdg.film_project.service;

import be.kdg.film_project.domain.Actor;
import be.kdg.film_project.domain.Film;
import org.springframework.cache.annotation.CacheEvict;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;

public interface FilmService {
    List<Film> getFilms();
    Film addFilm(String filmName, LocalDate year, double boxOffice, Film.Genre genre);

    Film getFilm(int filmId);

    Film getFilmWithActors(int id);

    boolean deleteFilm(int filmId);

    boolean changeFilmInfo(int filmId,double boxOffice, Film.Genre genre, LocalDate year);

    void updateFilmInfo(int filmId, double boxOffice, Film.Genre genre, LocalDate year);

    List<Film> getByName(String name);

    List<Film> getByActors(String actorName);

    void handleFilmsCsv(InputStream inputStream);
}
