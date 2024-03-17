package be.kdg.film_project.service.jpa;

import be.kdg.film_project.domain.Director;
import be.kdg.film_project.repository.jpa.DirectorJpaRepository;
import be.kdg.film_project.service.DirectorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DirectorJpaService implements DirectorService {
    private DirectorJpaRepository directorJpaRepository;

    public DirectorJpaService(DirectorJpaRepository directorJpaRepository) {
        this.directorJpaRepository = directorJpaRepository;
    }

    @Override
    @Transactional
    public Director addDirector(String name, int birth, String award) {
        Director director = new Director(name, birth, award);
        return directorJpaRepository.save(director);
    }

    @Override
    @Transactional
    public List<Director> getDirectors() {
        return directorJpaRepository.findAll();
    }

    @Override
    public Director getDirectorById(int id) {
        List<Director> directors = directorJpaRepository.findAll();
        for (Director director : directors) {
            if (director.getId() == id) {
                return director;
            }
        }
        return null;
    }

    @Override
    public boolean changeDirectorInfo(int directorId, int birth, String award) {
        var director = directorJpaRepository.findById(directorId).orElse(null);
        if (director == null) {
            return false;
        }
        director.setAward(award);
        director.setBirth(birth);
        directorJpaRepository.save(director);
        return true;
    }

    @Override
    @Transactional
    public void deleteDirector(int directorId) {
        directorJpaRepository.deleteById(directorId);
    }

    @Override
    public List<Director> getByAward(String award) {
        return directorJpaRepository.findDirectorByAward(award);
    }
}
