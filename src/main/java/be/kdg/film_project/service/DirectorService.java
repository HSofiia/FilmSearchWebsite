package be.kdg.film_project.service;

import be.kdg.film_project.domain.Director;

import java.util.List;

public interface DirectorService {
    Director addDirector(String name, int birth, String award);

    List<Director> getDirectors();

    Director getDirectorById(int id);

    boolean changeDirectorInfo(int directorId, int birth, String award);

    void deleteDirector(int directorId);

    List<Director> getByAward(String award);

}
