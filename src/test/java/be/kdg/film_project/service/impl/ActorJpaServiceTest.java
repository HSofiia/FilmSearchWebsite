package be.kdg.film_project.service.impl;

import be.kdg.film_project.domain.Actor;
import be.kdg.film_project.repository.jpa.ActorJpaRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ActorJpaServiceTest {

    @Autowired
    private ActorJpaService actorJpaService;

    @Autowired
    private ActorJpaRepository actorJpaRepository;

    private int testId;
    private Actor testActor;

    @BeforeAll
    void setup() {
        testActor = actorJpaRepository.save(new Actor("Brad Pitt", Actor.Gender.M, "American"));
        testId = testActor.getId();
    }

    @AfterAll
    void tearDown() {
        actorJpaRepository.deleteById(testId);
    }

    @Test
    void changeActorGenderAndNationalityShouldReturnTrueForExistingActorAndUpdateSaidActor() {
        // Arrange
        var createdActor = actorJpaRepository.save(testActor);

        // Act
        var result = actorJpaService.updateActorInfo(
                createdActor.getId(), Actor.Gender.F, "Ukrainian");

        // Assert
        assertTrue(result);
        assertEquals("Ukrainian",
                actorJpaRepository.findById(createdActor.getId()).get().getNationality());
        assertEquals(Actor.Gender.F,
                actorJpaRepository.findById(createdActor.getId()).get().getGender());

        actorJpaRepository.deleteById(createdActor.getId());
    }

    @Test
    void changeActorGenderAndNationalityShouldReturnFalseForNonExistingActor() {
        // Arrange

        // Act
        var result = actorJpaService.updateActorInfo(
                9999, Actor.Gender.F, "Ukrainian");

        // Assert
        assertFalse(result);
        assertTrue(actorJpaRepository.findById(9999).isEmpty());
    }


    @Test
    void addActorShouldBeAddedSuccessfully() {
        // Arrange
        String name = testActor.getActorName();
        Actor.Gender gender = testActor.getGender();
        String nationality = testActor.getNationality();

        // Act
        var addedActor = actorJpaService.addActor(name, gender, nationality);

        // Assert
        assertNotNull(addedActor);
        assertNotNull(addedActor.getId());
        assertEquals(name, addedActor.getActorName());
        assertEquals(gender, addedActor.getGender());
        assertEquals(nationality, addedActor.getNationality());

        // (cleanup)
        actorJpaRepository.deleteById(addedActor.getId());
    }

    @Test
    void addActorShouldFailOfNullName() {
        // Arrange
        Actor.Gender gender = testActor.getGender();
        String nationality = testActor.getNationality();

        Executable addedActor = () -> actorJpaService.addActor(null, gender, nationality);


        // Act & Assert
        assertThrows(IllegalArgumentException.class, addedActor);
    }

    @Test
    void addActor_Failure_NullGender() {
        // Arrange
        String name = testActor.getActorName();
        String nationality = testActor.getNationality();

        Executable addedActor = () -> actorJpaService.addActor(name, null, nationality);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, addedActor);
    }
}
