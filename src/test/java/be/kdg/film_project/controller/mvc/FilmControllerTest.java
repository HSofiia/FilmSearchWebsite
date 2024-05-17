package be.kdg.film_project.controller.mvc;

import be.kdg.film_project.controller.mvc.viewmodels.FilmViewModel;
import be.kdg.film_project.domain.Film;
import be.kdg.film_project.repository.jpa.FilmJpaRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class FilmControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private FilmJpaRepository filmRepository;

    private int createdFilmId;

    @BeforeEach
    public void setupEach() {
        var createdFilm = filmRepository.save(
                new Film("Oppenheimer", LocalDate.of(2023, 7, 19), 934.9, Film.Genre.HISTORY));
        createdFilmId = createdFilm.getId();
    }

    @AfterEach
    public void tearDownEach() {
        filmRepository.deleteById(createdFilmId);
    }

    @Test
    @WithUserDetails("user")
    public void filmViewShouldBeRenderedWithFilmAndActorData() throws Exception {
        var mvcResult = mockMvc.perform(
                        get("/extraFilmInfo")
                                .queryParam("id", String.valueOf(createdFilmId))
                )
                .andExpect(status().isOk())
                .andExpect(view().name("extraFilmInfo"))
                .andExpect(model().attribute("one_film",
                        Matchers.samePropertyValuesAs(new FilmViewModel(
                                createdFilmId, "Oppenheimer", Film.Genre.HISTORY, 934.9, LocalDate.of(2023, 7, 19), false
                        ), "actor")
                ))
                .andReturn();
    }

    @Test
    @WithUserDetails("admin")
    public void filmViewShouldAllowModificationIfAdminSignedIn() throws Exception {
        var mvcResult = mockMvc.perform(
                        get("/extraFilmInfo")
                                .queryParam("id", String.valueOf(createdFilmId))
                )
                .andExpect(status().isOk())
                .andExpect(view().name("extraFilmInfo"))
                .andExpect(model().attribute("one_film",
                        Matchers.samePropertyValuesAs(new FilmViewModel(
                                createdFilmId, "Oppenheimer", Film.Genre.HISTORY, 934.9, LocalDate.of(2023, 7, 19), true
                        ), "actor")
                ))
                .andReturn();
    }
}