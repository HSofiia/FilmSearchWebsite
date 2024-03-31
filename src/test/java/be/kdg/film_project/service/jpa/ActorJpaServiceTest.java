package be.kdg.film_project.service.jpa;

import be.kdg.film_project.domain.Actor;
import be.kdg.film_project.repository.jpa.ActorJpaRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
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

    @BeforeAll
    void setup() {
        // Create an issue to be used in this test class.
        // Such an issue (BeforeAll) should not be modified from within tests.
        // NOTE: I'm NOT actually using this record. This is just a demo.
        var testActor = actorJpaRepository.save(new Actor("Brad Pitt", Actor.Gender.M, "American"));
        testId = testActor.getId();
    }

    @AfterAll
    void tearDown() {
        actorJpaRepository.deleteById(testId);
    }

    @Test
    void changeActorGenderAndNationalityShouldReturnTrueForExistingActorAndUpdateSaidActor() {
        // Arrange
        var createdActor = actorJpaRepository.save(new Actor("Brad Pitt", Actor.Gender.M, "American"));

        // Act
        var result = actorJpaService.updateActorInfo(
                createdActor.getId(), Actor.Gender.F, "Ukrainian");

        // Assert
        assertTrue(result);
        assertEquals("Ukrainian",
                actorJpaRepository.findById(createdActor.getId()).get().getNationality());
        assertEquals(Actor.Gender.F,
                actorJpaRepository.findById(createdActor.getId()).get().getGender());

        // (cleanup)
        actorJpaRepository.deleteById(createdActor.getId());
    }

    @Test
    void changeIssueDescriptionShouldReturnFalseForNonExistingIssue() {
        // Arrange

        // Act
        var result = actorJpaService.updateActorInfo(
                9999, Actor.Gender.F, "Ukrainian");

        // Assert
        assertFalse(result);
        // This is a bit over the top (my "assumptions" were clear).
        assertTrue(actorJpaRepository.findById(9999).isEmpty());
    }
}