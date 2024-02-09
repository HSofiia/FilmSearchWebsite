package be.kdg.film_project.repository;

import be.kdg.film_project.domain.Director;

import java.util.List;

public interface DirectorObjectRepository {
    Director createDirector(Director director);

    List<Director> readDirector();

    void deleteDirector(int directorId);

    List<Director> getByDirectorAward(String award);
}
