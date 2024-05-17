package be.kdg.film_project.service.impl;

import be.kdg.film_project.domain.Actor;
import be.kdg.film_project.repository.jpa.ActorJpaRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ActiveProfiles("test")
public class ActorJpaServiceUnitTest {
    @Autowired
    private ActorJpaService actorService;

    @MockBean
    private ActorJpaRepository actorRepository;

    @Test
    void updateInfoFailsWhenActorDoesntExist() {
        // Arrange
        given(actorRepository.findById(7777)).willReturn(Optional.empty());

        // Act
        var updateSucceeded = actorService.updateActorInfo(7777, Actor.Gender.M, "Ukrainian");

        // Assert
        assertFalse(updateSucceeded);
        verify(actorRepository, never()).save(any());
    }

    @Test
    void updateInfoSucceedsWhenActorExists() {
        // Arrange
        var actor = new Actor("John", Actor.Gender.M, "American");
        actor.setId(7777);
        given(actorRepository.findById(7777)).willReturn(Optional.of(actor));

        // Act
        var updateSucceeded = actorService.updateActorInfo(7777, Actor.Gender.F, "Ukrainian");

        // Assert
        assertTrue(updateSucceeded);
        ArgumentCaptor<Actor> actorCaptor = ArgumentCaptor.forClass(Actor.class);
        verify(actorRepository/*, times(1)*/).save(actorCaptor.capture());
        assertEquals(Actor.Gender.F, actorCaptor.getValue().getGender(), actorCaptor.getValue().getNationality());
    }
}
