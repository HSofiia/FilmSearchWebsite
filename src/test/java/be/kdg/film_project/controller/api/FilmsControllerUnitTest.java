package be.kdg.film_project.controller.api;

import be.kdg.film_project.controller.api.dto.film.UpdateFilmDto;
import be.kdg.film_project.domain.Film;
import be.kdg.film_project.domain.UserRole;
import be.kdg.film_project.security.CustomUserDetails;
import be.kdg.film_project.service.FilmService;
import be.kdg.film_project.service.impl.UserJpaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class FilmsControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FilmService filmService;
    @Autowired
    private UserJpaService userJpaService;

    @Test
    public void deleteFilmShouldBeUnauthorizedIfNotSignedIn() throws Exception {
        mockMvc.perform(delete("/api/extraFilmInfo/{id}", 7777)
                        .with(csrf()))
                .andExpect(status().isUnauthorized());

        verify(filmService, never()).deleteFilm(7777);
    }

    @Test
    public void deleteFilmShouldBeAllowedIfAdminIsAssigned() throws Exception {
        int filmId = 7777;
        int userId = 88888;
        var authorities = List.of(new SimpleGrantedAuthority("ROLE_ADMIN")); // Added role
        var userDetails = new CustomUserDetails("admin", "$2a$10$N3TGXdcRDhTBQbPxD1BFjO2c0/eV7mAZKl5bpgakNn4tRET53Kgbi", authorities, userId);

        given(filmService.deleteFilm(filmId)).willReturn(true);

        mockMvc.perform(delete("/api/extraFilmInfo/{id}", filmId)
                        .with(user(userDetails))
                        .with(csrf()))
                .andExpect(status().isNoContent());

        verify(filmService).deleteFilm(filmId);
    }

    @Test
    public void deleteFilmShouldNotBeAllowedIfNotAssigned() throws Exception {
        int filmId = 7777;
        int userId = 88888;
        var authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
        var userDetails = new CustomUserDetails("user", "$2a$10$N3TGXdcRDhTBQbPxD1BFjO2c0/eV7mAZKl5bpgakNn4tRET53Kgbi", authorities, userId);

        mockMvc.perform(delete("/api/extraFilmInfo/{id}", filmId)
                        .with(user(userDetails))
                        .with(csrf()))
                .andExpect(status().isForbidden());

        verify(filmService, never()).deleteFilm(filmId);
    }

    @Test
    public void deleteFilmShouldBeNotFoundIfFilmDoesntExist() throws Exception {
        int filmId = 7777;
        int userId = 88888;
        var authorities = List.of(new SimpleGrantedAuthority("ROLE_ADMIN")); // Added role
        var userDetails = new CustomUserDetails("admin", "$2a$10$N3TGXdcRDhTBQbPxD1BFjO2c0/eV7mAZKl5bpgakNn4tRET53Kgbi", authorities, userId);

        given(filmService.deleteFilm(filmId)).willReturn(false);

        mockMvc.perform(delete("/api/extraFilmInfo/{id}", filmId)
                        .with(user(userDetails))
                        .with(csrf()))
                .andExpect(status().isNotFound());

        verify(filmService).deleteFilm(filmId);
    }
}