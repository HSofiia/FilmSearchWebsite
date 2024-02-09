package be.kdg.film_project.service;

import be.kdg.film_project.domain.Director;
import be.kdg.film_project.presentation.exceptions.ActorException;
import be.kdg.film_project.repository.DirectorObjectRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Profile("!jpa")
public class DirectorServiceImp implements DirectorService {
    private DirectorObjectRepository directorObjectRepository;

    public DirectorServiceImp(DirectorObjectRepository directorObjectRepository) {
        this.directorObjectRepository = directorObjectRepository;
    }

    @Override
    public Director addDirector(String name, int birth, String award) {
        return directorObjectRepository.createDirector(new Director(name, birth, award));
    }

    @Override
    public List<Director> getDirectors() {
        return directorObjectRepository.readDirector();
    }

    @Override
    public Director getDirectorById(int id) {
        List<Director> directors = directorObjectRepository.readDirector();
        if (directors == null) {
            throw new ActorException("Director list is empty");
        }

        for (Director director : directors) {
            if (director.getId() == id) {
                return director;
            }
        }
        throw new ActorException("Director not found with id: " + id);
    }

    @Override
    public void deleteDirector(int directorId) {
        directorObjectRepository.deleteDirector(directorId);
    }

    @Override
    public List<Director> getByAward(String award) {
        return directorObjectRepository.getByDirectorAward(award);
    }
}
