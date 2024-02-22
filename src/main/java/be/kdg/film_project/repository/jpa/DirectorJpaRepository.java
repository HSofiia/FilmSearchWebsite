package be.kdg.film_project.repository.jpa;

import be.kdg.film_project.domain.Director;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DirectorJpaRepository extends JpaRepository<Director, Integer> {
    List<Director> findDirectorByAward(String award);
}
