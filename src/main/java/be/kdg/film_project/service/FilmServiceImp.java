package be.kdg.film_project.service;

import be.kdg.film_project.domain.Film;
import be.kdg.film_project.presentation.exceptions.FilmException;
import be.kdg.film_project.repository.FilmObjectRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Profile("!jpa")
public class FilmServiceImp implements FilmService {
    private final FilmObjectRepository filmRepository;
    private FilmException filmException;

    public FilmServiceImp(FilmObjectRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    @Override
    public Film addFilm(String filmName, LocalDate year, double boxOffice, Film.Genre genre) {
        return filmRepository.createFilm(new Film(filmName, year, boxOffice, genre));
    }

    @Override
    public List<Film> getFilms() {
        return filmRepository.readFilms();
    }

    @Override
    public Film getFilmById(Integer id) {
        return filmRepository.getFilmById(id).orElseThrow(
                () -> new FilmException("Can not find film with provided id (serviceimp)"));
    }

    @Override
    public void deleteFilm(int filmId) {
        filmRepository.deleteFilm(filmId);
    }

    @Override
    public List<Film> getByName(String name) {
        return filmRepository.getByFilmName(name);
    }

    @Override
    public List<Film> getByActors(String actorName) {
        return filmRepository.getFilmByActors(actorName);
    }
}
