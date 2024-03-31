package be.kdg.film_project.service.jpa;

import be.kdg.film_project.domain.Film;
import be.kdg.film_project.repository.jpa.FilmJpaRepository;
import be.kdg.film_project.service.FilmService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class FilmJpaService implements FilmService {
    private FilmJpaRepository filmJpaRepository;

    public FilmJpaService(FilmJpaRepository filmJpaRepository) {
        this.filmJpaRepository = filmJpaRepository;
    }

    @Override
    @Transactional
    public Film addFilm(String filmName, LocalDate year, double boxOffice, Film.Genre genre) {
        Film film = new Film(filmName, year, boxOffice, genre);
        return filmJpaRepository.save(film);
    }

    @Override
    public Film getFilm(int filmId) {
        return filmJpaRepository.findById(filmId).orElse(null);
    }

    @Override
    public List<Film> getFilms() {
        return filmJpaRepository.findAll();
    }

    @Override
    public Film getFilmWithActors(int id) {
        return filmJpaRepository.findByIdWithActors(id).orElse(null);
    }

    @Override
    @Transactional
    public boolean deleteFilm(int filmId) {
        var film = filmJpaRepository.findByIdWithCasting(filmId);
        if (film.isEmpty()) {
            return false;
        }
        filmJpaRepository.deleteById(filmId);
        return true;
    }

    @Override
    public boolean changeFilmInfo(int filmId, double boxOffice, Film.Genre genre, LocalDate year) {
        var film = filmJpaRepository.findById(filmId).orElse(null);
        if (film == null) {
            return false;
        }
        film.setBoxOffice(boxOffice);
        film.setGenre(genre);
        film.setYear(year);
        filmJpaRepository.save(film);
        return true;
    }

    @Override
    public void updateFilmInfo(int filmId, double boxOffice, Film.Genre genre, LocalDate year){
        var film = filmJpaRepository.findById(filmId).orElse(null);
        if (film != null) {
            film.setBoxOffice(boxOffice);
            film.setYear(year);
            film.setGenre(genre);
            filmJpaRepository.save(film);
        }
    }

    @Override
    public List<Film> getByName(String name) {
        return filmJpaRepository.findByFilmName(name);
    }

    @Override
    public List<Film> getByActors(String actorName) {
        return filmJpaRepository.findFilmsByActorName(actorName);
    }
}
