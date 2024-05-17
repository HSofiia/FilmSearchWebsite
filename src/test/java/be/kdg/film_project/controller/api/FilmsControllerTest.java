package be.kdg.film_project.controller.api;

import be.kdg.film_project.controller.api.dto.film.UpdateFilmDto;
import be.kdg.film_project.domain.Film;
import be.kdg.film_project.domain.FilmCasting;
import be.kdg.film_project.repository.jpa.ActorJpaRepository;
import be.kdg.film_project.repository.jpa.FilmCastingRepository;
import be.kdg.film_project.repository.jpa.FilmJpaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class FilmsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FilmJpaRepository filmRepository;

    @Autowired
    private FilmCastingRepository filmCastingRepository;

    @Autowired
    private ActorJpaRepository actorRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private int createdFilmId;
    private int createdFilmWithoutActorsId;
    private int createdFilmCastingId;

    @BeforeEach
    public void setupEach() {
        var createdFilm = filmRepository.save(
                new Film("Dummy text", LocalDate.of(2020, 2, 2), 29.4, Film.Genre.ROMANCE));
        createdFilmId = createdFilm.getId();

        var createdFilmWithoutActors = filmRepository.save(
                new Film("Dummy text", LocalDate.of(2020, 2, 2), 29.4, Film.Genre.ROMANCE));
        createdFilmWithoutActorsId = createdFilmWithoutActors.getId();

        var createdAssignment = filmCastingRepository.save(
                new FilmCasting(createdFilm,
                        actorRepository.findById(1).orElseThrow())
        );
        createdFilmCastingId = createdAssignment.getId();
    }

    @AfterEach
    public void tearDownEach() {
        filmCastingRepository.deleteById(createdFilmCastingId);
        filmRepository.deleteById(createdFilmId);
    }

    @Test
    public void getActorsOfFilmsShouldReturnNotFoundForNonExistentIssue() throws Exception {
        mockMvc.perform(
                        get("/api/extraFilmInfo/{id}/actors", 100)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getActorsOfFilmsShouldReturnNoContentIfNoAssignedActors() throws Exception {
        mockMvc.perform(
                        get("/api/extraFilmInfo/{id}/actors", createdFilmWithoutActorsId)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void getActorsOfFilmsShouldReturnOkWithActors() throws Exception {
        mockMvc.perform(
                        get("/api/extraFilmInfo/{id}/actors", 1)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[*].actorName",
                        Matchers.containsInAnyOrder("Rami Malek", "Killian Merphy")))
                .andDo(print());
    }

    @Test
    public void changeFilmIsNotAllowedIfNotSignedIn() throws Exception {
        mockMvc.perform(
                        patch("/api/extraFilmInfo/{id}", createdFilmId)
                                .with(csrf()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails("user")
    public void changeFilmWithUserRoleReturnsForbidden() throws Exception {
        mockMvc.perform(patch("/api/extraFilmInfo/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")) // Empty JSON body for simplicity
                .andExpect(status().isForbidden());
    }


    @Test
    @WithUserDetails("admin")
    public void changeFilmWithAdminRoleShouldReturnNoContent() throws Exception {
        mockMvc.perform(patch("/api/extraFilmInfo/{id}", createdFilmId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(new UpdateFilmDto(
                                LocalDate.of(2022, 3, 3), 12.3, Film.Genre.FANTASY
                        ))))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithUserDetails("admin")
    public void changeFilmShouldReturnNotFoundIfFilmDoesNotExist() throws Exception {
        mockMvc.perform(
                        patch("/api/extraFilmInfo/{id}", 100000000)
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(csrf())
                                .content(objectMapper.writeValueAsString(new UpdateFilmDto(
                                        LocalDate.of(2022, 3, 3), 12.3, Film.Genre.FANTASY
                                ))))
                .andExpect(status().isNotFound());
    }
}
