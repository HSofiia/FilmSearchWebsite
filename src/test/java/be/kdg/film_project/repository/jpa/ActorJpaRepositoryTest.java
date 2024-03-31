package be.kdg.film_project.repository.jpa;

import be.kdg.film_project.domain.Actor;
import be.kdg.film_project.domain.Film;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class ActorJpaRepositoryTest {
    @Autowired
    private ActorJpaRepository actorJpaRepository;

    @Test
    public void findByIdWithFilmsShouldFetchRelatedData() {
        // Arrange

        // Act
        var actorOptional = actorJpaRepository.findByIdWithRelatedFilm(1);

        // Assert
        assertTrue(actorOptional.isPresent());
        var actor = actorOptional.get();
        assertEquals(1, actor.getId());
        assertEquals(Actor.Gender.M, actor.getGender());
        // There are other ways to compare lists in tests (Hamcrest, AssertJ, ...)
        var film = actor.getFilm()
                .stream()
                .sorted((a1, a2) -> (int) (a1.getId() - a2.getId()))
                .toList();
        assertEquals("Oppenheimer",
                film.get(0).getFilm().getFilmName());
        assertEquals("No Time to Die",
                film.get(1).getFilm().getFilmName());
        assertEquals("Inception",
                film.get(2).getFilm().getFilmName());
        assertEquals("Pride and Prejudice",
                film.get(3).getFilm().getFilmName());
        assertEquals("Peaky Blinders",
                film.get(4).getFilm().getFilmName());
        assertEquals("Pirates of the Caribbean",
                film.get(5).getFilm().getFilmName());
    }

    @Test
    public void filmEntitiesShouldBeUnique() {
        // Arrange
        actorJpaRepository.save(new Actor("Johny Depp", Actor.Gender.M, "American"));

        // Act
        Executable executable = () -> actorJpaRepository.save(new Actor(
                "Johny Depp", Actor.Gender.M, "American"
        ));

        // Assert
        assertThrows(DataIntegrityViolationException.class, executable);
    }
}