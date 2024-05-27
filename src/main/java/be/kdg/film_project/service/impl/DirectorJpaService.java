package be.kdg.film_project.service.impl;

import be.kdg.film_project.domain.Director;
import be.kdg.film_project.repository.jpa.DirectorJpaRepository;
import be.kdg.film_project.service.DirectorService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
    @CacheEvict(value = "searchDirectors", allEntries = true)
    public Director addDirector(String name, int birth, String award) {
        Director director = new Director(name, birth, award);
        return directorJpaRepository.save(director);
    }

    @Override
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
    @CacheEvict(value = "searchDirectors", allEntries = true)
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
    @CacheEvict(value = "searchDirectors", allEntries = true)
    public boolean deleteDirector(int directorId) {
        var director = directorJpaRepository.findById(directorId);
        if (director.isEmpty()) {
            return false;
        }
        directorJpaRepository.deleteById(directorId);
        return true;
    }

    @Override
    @CacheEvict(value = "searchDirectors", allEntries = true)
    public boolean updateDirectorInfo(int directorId, String award, int birth) {
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
    @Cacheable(value = "searchDirectors")
    public List<Director> getByAward(String award) {
        return directorJpaRepository.findDirectorByAward(award);
    }
}
