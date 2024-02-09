package be.kdg.film_project.controller;

import be.kdg.film_project.domain.Film;
import be.kdg.film_project.presentation.exceptions.FilmException;
import be.kdg.film_project.presentation.viewmodels.FilmViewModel;
import be.kdg.film_project.service.FilmService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/films")
public class FilmController {
    private final FilmService filmService;
    private Logger logger = LoggerFactory.getLogger(FilmController.class);

    public FilmController(FilmService actorService) {
        this.filmService = actorService;
    }

    @GetMapping
    public String getFilmsView(Model model) {
        List<Film> filmsList = filmService.getFilms();
        model.addAttribute("films", filmsList);
        return "films";
    }

    @GetMapping("/{id}")
    public String getFilmId(@PathVariable Integer id, Model model) {
        Film film = filmService.getFilmById(id);
        model.addAttribute("film", film);
        return "extraFilmInfo";
    }

    @GetMapping("/search")
    public String searchFilms(@RequestParam(required = false, value = "actorsName") String actorsName,
                              Model model) {
        List<Film> films;
        if (actorsName != null) {
            films = filmService.getByActors(actorsName);
        } else {
            films = filmService.getFilms();
        }
        model.addAttribute("films", films);
        return "films";
    }

    @GetMapping("/addFilm")
    public String showAddFilmForm(Model model) {
        model.addAttribute("film", new FilmViewModel());
        return "addFilm";
    }

    @PostMapping("/addFilm")
    public String processAddFilmForm(@ModelAttribute("film") @Valid FilmViewModel viewModel, BindingResult result) {
        logger.info("Processing " + viewModel.toString());
        if (result.hasErrors()) {
            result.getAllErrors().forEach(e -> logger.warn(e.toString()));
            return "addFilm";
        } else {
            logger.info("Successfully processed ");
            filmService.addFilm(
                    viewModel.getFilmName(),
                    viewModel.getYear(),
                    viewModel.getBoxOffice(),
                    viewModel.getGenre());
            return "redirect:/films";
        }
    }

    @RequestMapping("/{id}")
    public String deleteFilm(@PathVariable int id) {
        filmService.deleteFilm(id);
        return "redirect:/films";
    }


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(FilmException.class)
    public String handlePageException(Exception e, Model model) {
        logger.error("Error in exceptionHandler" + e);
        model.addAttribute("error", "The page is not found ;(");
        return "error";
    }
}

