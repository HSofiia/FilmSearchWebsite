package be.kdg.film_project.repository;

import be.kdg.film_project.domain.Actor;
import be.kdg.film_project.domain.Director;
import be.kdg.film_project.domain.Film;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@Profile("object")
public class DataFactory implements CommandLineRunner {
    public static List<Actor> actors = new ArrayList<>();
    public static List<Film> films = new ArrayList<>();
    public static List<Director> directors = new ArrayList<>();
    private ActorObjectRepository actorObjectRepository;
    private FilmObjectRepository filmObjectRepository;
    private DirectorObjectRepository directorObjectRepository;

    public DataFactory(ActorObjectRepository actorObjectRepository, FilmObjectRepository filmObjectRepository, DirectorObjectRepository directorObjectRepository) {
        this.actorObjectRepository = actorObjectRepository;
        this.filmObjectRepository = filmObjectRepository;
        this.directorObjectRepository = directorObjectRepository;
    }

    public void seed() {
        Actor killian = new Actor(1, "Killian Merphy", Actor.Gender.M, "Irish");
        Actor keira = new Actor(2, "Keira Knightley", Actor.Gender.F, "British");
        Actor leonardo = new Actor(3, "Leonardo DiCaprio", Actor.Gender.M, "American");
        Actor rami = new Actor(4, "Rami Malek", Actor.Gender.M, "American");
        Actor matt = new Actor(5, "Matt Damon", Actor.Gender.M, "American");

        actors.add(killian);
        actors.add(keira);
        actors.add(leonardo);
        actors.add(rami);
        actors.add(matt);

//      pirates
        Director joachim = new Director(1, "Joachim Rønning", 1972, "Academy Award for Best International Feature Film");
//       oppenheimer, inception
        Director christopher = new Director(2, "Christopher Nolan", 1970, "Academy Award for Best Picture");
//        nttd
        Director cary = new Director(3, "Cary Joji Fukunaga", 1977, "Primetime Emmy Award for Outstanding Directing for a Drama Series");
//        pride
        Director joe = new Director(4, "Joe Wright", 1972, "BAFTA Award for Outstanding Debut by a British Writer, Director or Producer");
//      peaky
        Director otto = new Director(5, "Otto Bathurst", 1971, "British Academy Television Award for Best Drama Serial,");

        directors.add(joachim);
        directors.add(christopher);
        directors.add(cary);
        directors.add(joe);
        directors.add(otto);

        Film oppenheimer = new Film(1, "Oppenheimer", LocalDate.of(2023, 7, 19), 934.9, Film.Genre.HISTORY);
        Film nttd = new Film(2, "No Time to Die", LocalDate.of(2021, 9, 30), 774.2, Film.Genre.SPY);
        Film inception = new Film(3, "Inception", LocalDate.of(2010, 7, 21), 837.0, Film.Genre.ACTION);
        Film pride = new Film(4, "Pride and Prejudice", LocalDate.of(2005, 10, 12), 121.6, Film.Genre.ROMANCE);
        Film peaky = new Film(5, "Peaky Blinders", LocalDate.of(2022, 4, 3), 62.0, Film.Genre.HISTORY);
        Film pirates = new Film(6, "Pirates of the Caribbean", LocalDate.of(2017, 5, 26), 795.9, Film.Genre.ACTION);

        films.add(oppenheimer);
        films.add(nttd);
        films.add(inception);
        films.add(pride);
        films.add(peaky);
        films.add(pirates);

//        adding films to directors
        joachim.addFilmD(pirates);
        christopher.addFilmD(oppenheimer);
        christopher.addFilmD(inception);
        otto.addFilmD(peaky);
        joe.addFilmD(pride);
        cary.addFilmD(nttd);

//        adding films to actors
        killian.addFilm(oppenheimer);
        killian.addFilm(peaky);
        killian.addFilm(inception);
        leonardo.addFilm(inception);
        matt.addFilm(inception);
        rami.addFilm(nttd);
        rami.addFilm(oppenheimer);
        keira.addFilm(pride);
        keira.addFilm(pirates);

//        adding actors to films
        oppenheimer.addActor(killian);
        oppenheimer.addActor(rami);
        nttd.addActor(rami);
        inception.addActor(killian);
        inception.addActor(matt);
        inception.addActor(leonardo);
        peaky.addActor(killian);
        pride.addActor(keira);
        pirates.addActor(keira);

        films.forEach(filmObjectRepository::createFilm);
        actors.forEach(actorObjectRepository::createActor);
        directors.forEach(directorObjectRepository::createDirector);
    }

    @Override
    public void run(String... args) throws Exception {
        seed();
    }
}
