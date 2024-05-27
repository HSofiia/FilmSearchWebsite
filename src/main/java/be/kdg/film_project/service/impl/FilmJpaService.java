package be.kdg.film_project.service.impl;

import be.kdg.film_project.domain.Film;
import be.kdg.film_project.repository.jpa.FilmJpaRepository;
import be.kdg.film_project.service.FilmService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

@Service
public class FilmJpaService implements FilmService {
    private FilmJpaRepository filmJpaRepository;
    private static final Logger LOGGER = Logger.getLogger(FilmService.class.getName());


    public FilmJpaService(FilmJpaRepository filmJpaRepository) {
        this.filmJpaRepository = filmJpaRepository;
    }

    @Override
    @CacheEvict(value = "search-films", allEntries = true)
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
    @CacheEvict(value = "search-films", allEntries = true)
    public boolean deleteFilm(int filmId) {
        var film = filmJpaRepository.findByIdWithCasting(filmId);
        if (film.isEmpty()) {
            return false;
        }
        filmJpaRepository.deleteById(filmId);
        return true;
    }

    @Override
    @CacheEvict(value = "search-films", allEntries = true)
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
    @Cacheable(value = "search-films")
    public List<Film> getByActors(String actorName) {
        return filmJpaRepository.findFilmsByActorName(actorName);
    }

    @Async
    @CacheEvict(value = "search-films", allEntries = true)
    public void handleFilmsCsv(InputStream inputStream) {
        try (Scanner scanner = new Scanner(inputStream)) {
            List<Film> films = new ArrayList<>();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split(",");
                if (data.length != 4) {
                    LOGGER.warning("Invalid line: " + line);
                    continue;
                }
                try {
                    String filmName = data[0];
                    LocalDate year = LocalDate.parse(data[1]);
                    double boxOffice = Double.parseDouble(data[2]);
                    Film.Genre genre = Film.Genre.valueOf(data[3].toUpperCase());

                    Film film = new Film(filmName, year, boxOffice, genre);
                    films.add(film);
                } catch (Exception e) {
                    LOGGER.warning("Failed to parse line: " + line + ". Error: " + e.getMessage());
                }
            }
            filmJpaRepository.saveAll(films);
            LOGGER.info("Successfully processed and saved " + films.size() + " films");
        } catch (Exception e) {
            LOGGER.severe("Error processing CSV file: " + e.getMessage());
        }
    }
}
