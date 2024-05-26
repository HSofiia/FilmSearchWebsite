package be.kdg.film_project.repository.jpa;

import be.kdg.film_project.domain.Actor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

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
        var actorOptional = actorJpaRepository.findByIdWithFilms(1);

        // Assert
        assertTrue(actorOptional.isPresent());
        var actor = actorOptional.get();
        assertEquals(1, actor.getId());
        assertEquals(Actor.Gender.M, actor.getGender());
        var film = actor.getFilm()
                .stream()
                .sorted((a1, a2) -> (int) (a1.getId() - a2.getId()))
                .toList();
        assertEquals("Oppenheimer",
                film.get(0).getFilm().getFilmName());
        assertEquals("Inception",
                film.get(1).getFilm().getFilmName());
        assertEquals("Peaky Blinders",
                film.get(2).getFilm().getFilmName());
    }

    @Test
    public void findByGenderAndNationalityShouldReturnMatchingActors() {
        // Arrange
        Actor.Gender gender = Actor.Gender.F;
        String nationality = "British";

        // Act
        List<Actor> actors = actorJpaRepository.findByGenderOrNationality(gender, nationality);

        // Assert
        assertTrue(!actors.isEmpty(), "List of actors should not be empty");

        for (Actor actor : actors) {
            assertEquals(gender, actor.getGender(), "Actor's gender should match the expected gender");
            assertEquals(nationality, actor.getNationality(), "Actor's nationality should match the expected nationality");
        }
    }
}
