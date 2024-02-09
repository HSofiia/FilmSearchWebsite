package be.kdg.film_project.service.jpa;

import be.kdg.film_project.domain.Film;
import be.kdg.film_project.repository.jpa.FilmJpaRepository;
import be.kdg.film_project.service.FilmService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Profile("jpa")
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
    @Transactional
    public List<Film> getFilms() {
        return filmJpaRepository.findAll();
    }

    @Override
    public Film getFilmById(Integer id) {
        List<Film> films = filmJpaRepository.findAll();
        for (Film film : films) {
            if (film.getId() == id) {
                return film;
            }
        }
        return null;
    }

    @Override
    @Transactional
    public void deleteFilm(int filmId) {
        filmJpaRepository.deleteById(filmId);
    }

    @Override
    public List<Film> getByName(String name) {
        return filmJpaRepository.findByFilmName(name);
    }

    @Override
    public List<Film> getByActors(String actorName) {
        return filmJpaRepository.findFilmByActors(actorName);
    }

}
