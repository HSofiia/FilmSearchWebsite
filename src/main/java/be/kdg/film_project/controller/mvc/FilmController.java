package be.kdg.film_project.controller.mvc;

import be.kdg.film_project.domain.Film;
import be.kdg.film_project.presentation.exceptions.FilmException;
import be.kdg.film_project.controller.mvc.viewmodels.FilmViewModel;
import be.kdg.film_project.service.FilmService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class FilmController {
    private final FilmService filmService;
    private Logger logger = LoggerFactory.getLogger(FilmController.class);

    public FilmController(FilmService actorService) {
        this.filmService = actorService;
    }

    @GetMapping("/films")
    public ModelAndView allFilms(){
        var mav = new ModelAndView();
        mav.setViewName("films");
        mav.addObject("all_films",
                filmService.getFilms()
                        .stream()
                        .map(film -> new FilmViewModel(
                                film.getId(),
                                film.getFilmName(),
                                film.getGenre(),
                                film.getBoxOffice(),
                                film.getYear()
                        ))
                        .toList());
        return mav;
    }

    @GetMapping("/extraFilmInfo")
    public ModelAndView oneFilm(@RequestParam("id") int filmId) {
        var film = filmService.getFilm(filmId);
        var mav = new ModelAndView();
        mav.setViewName("extraFilmInfo");
        mav.addObject("one_film",
                new FilmViewModel(
                        film.getId(),
                        film.getFilmName(),
                        film.getGenre(),
                        film.getBoxOffice(),
                        film.getYear()
                ));
        return mav;
    }

//    @GetMapping("/extraFilmInfo")
//    public ModelAndView getFilmId(@RequestParam("id")int id) {
//        var film = filmService.getFilmWithActors(id);
//        var mav = new ModelAndView();
//        mav.setViewName("extraFilmInfo");
//        mav.addObject("one_film",
//                new FilmViewModel(
//                        film.getId(),
//                        film.getFilmName(),
//                        film.getGenre(),
//                        film.getBoxOffice(),
//                        film.getYear(),
//                        film.getCastings()
//                                .stream().map(
//                                        mapper ->
//                                                new ActorViewModel(
//                                                        mapper.getActor().getId(),
//                                                        mapper.getActor().getActorName(),
//                                                        mapper.getActor().getGender(),
//                                                        mapper.getActor().getNationality()
//                                                )
//                                ).toList()
//                ));
//        return mav;
//    }

    @GetMapping("/films/search")
    public ModelAndView searchFilms(@RequestParam(required = false, value = "actorsName") String actorsName) {
        var mav = new ModelAndView("films");
        List<Film> films;
        if (actorsName != null && !actorsName.isEmpty()) {
            films = filmService.getByActors(actorsName);
        } else {
            films = filmService.getFilms();
        }
        List<FilmViewModel> filmViewModels = films.stream()
                .map(film -> new FilmViewModel(
                        film.getId(),
                        film.getFilmName(),
                        film.getGenre(),
                        film.getBoxOffice(),
                        film.getYear()
                )).toList();

        mav.addObject("all_films", filmViewModels);
        return mav;
    }

    @GetMapping("/addFilm")
    public String getAddFilm(Model model) {
        model.addAttribute("film", new FilmViewModel());
        return "/addFilm";
    }

//    @PostMapping("/films/addFilm")
//    public String processAddFilmForm(@Valid @ModelAttribute("film")  FilmViewModel viewModel, BindingResult result) {
//        logger.info("Processing " + viewModel.toString());
//        if (result.hasErrors()) {
//            result.getAllErrors().forEach(e -> logger.warn(e.toString()));
//            return "addFilm";
//        } else {
//            logger.info("Successfully processed ");
//            filmService.addFilm(
//                    viewModel.getFilmName(),
//                    viewModel.getYear(),
//                    viewModel.getBoxOffice(),
//                    viewModel.getGenre());
//            return "redirect:/films";
//        }
//    }
//
//    @RequestMapping("/extraFilmInfo")
//    public String deleteFilm(@RequestParam("id") int id) {
//        filmService.deleteFilm(id);
//        return "redirect:/films";
//    }


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(FilmException.class)
    public String handlePageException(Exception e, Model model) {
        logger.error("Error in exceptionHandler" + e);
        model.addAttribute("error", "The page is not found ;(");
        return "error";
    }
}

