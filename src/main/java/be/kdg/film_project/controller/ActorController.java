package be.kdg.film_project.controller;

import be.kdg.film_project.domain.Actor;
import be.kdg.film_project.presentation.exceptions.ActorException;
import be.kdg.film_project.presentation.viewmodels.ActorViewModel;
import be.kdg.film_project.presentation.viewmodels.FilmViewModel;
import be.kdg.film_project.service.ActorService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ActorController {
    private final ActorService actorService;
    private Logger logger = LoggerFactory.getLogger(ActorController.class);

    public ActorController(ActorService actorService) {
        this.actorService = actorService;
    }

    @GetMapping("/actors")
    public ModelAndView allActors() {
        var mav = new ModelAndView();
        mav.setViewName("actors");
        mav.addObject("all_actors",
                actorService.getActors()
                        .stream()
                        .map(actor -> new ActorViewModel(
                                actor.getId(),
                                actor.getActorName(),
                                actor.getGender(),
                                actor.getNationality()
                        ))
                        .toList());
        return mav;
    }

    @GetMapping("/extraActorInfo")
    public ModelAndView oneActor(@RequestParam("id") int id) {
        var actor = actorService.getActorWithFilms(id);
        var mav = new ModelAndView();
        mav.setViewName("extraActorInfo");
        mav.addObject("one_actor",
                new ActorViewModel(
                        actor.getId(),
                        actor.getActorName(),
                        actor.getGender(),
                        actor.getNationality(),
                        actor.getFilm()
                                .stream().map(
                                        mapper ->
                                                new FilmViewModel(
                                                        mapper.getFilm().getFilmName(),
                                                        mapper.getFilm().getGenre(),
                                                        mapper.getFilm().getBoxOffice(),
                                                        mapper.getFilm().getYear()
                                                )
                                ).toList()
                ));
        return mav;
    }

    @GetMapping("/actors/search")
    public ModelAndView searchActors(@RequestParam(required = false, value = "gender") Actor.Gender gender,
                               @RequestParam(required = false, value = "nationality") String nationality) {
        var mav = new ModelAndView("actors"); // Return the actors.html template
        List<Actor> actors;
        if (gender != null && nationality != null) {
            actors = actorService.getByGenderAndNationality(gender, nationality);
        } else {
            actors = actorService.getActors();
        }
        mav.addObject("all_actors",actors);
        return mav;
    }

    @GetMapping("/actors/addActor")
    public ModelAndView getAddActor() {
        var mav = new ModelAndView();
        mav.setViewName("addActor");
        mav.addObject("actor", new ActorViewModel());
        return mav;
    }

    @PostMapping("/actors/addActor")
    public String processAddActorForm(@Valid @ModelAttribute("actor") ActorViewModel viewModel, BindingResult result) {
        logger.info("Processing " + viewModel.toString());
        if (result.hasErrors()) {
            result.getAllErrors().forEach(e -> logger.warn(e.toString()));
            return "addActor";
        } else {
            logger.info("Successfully processed ");
            actorService.addActor(
                    viewModel.getActorName(),
                    viewModel.getGender(),
                    viewModel.getNationality());
            return "redirect:/actors";
        }
    }

    @RequestMapping("/extraActorInfo")
    public String deleteActor(@RequestParam("id") int id) {
        actorService.deleteActor(id);
        return "redirect:/actors";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ActorException.class)
    public String handlePageException(Exception e, Model model) {
        logger.error("Error in exceptionHandler" + e);
        model.addAttribute("error", "The page is not found ;(");
        return "error";
    }
}
