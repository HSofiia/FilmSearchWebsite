package be.kdg.film_project.controller;

import be.kdg.film_project.domain.Actor;
import be.kdg.film_project.presentation.exceptions.ActorException;
import be.kdg.film_project.presentation.viewmodels.ActorViewModel;
import be.kdg.film_project.service.ActorService;
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
@RequestMapping("/actors")
public class ActorController {
    private final ActorService actorService;
    private Logger logger = LoggerFactory.getLogger(ActorController.class);

    public ActorController(ActorService actorService) {
        this.actorService = actorService;
    }

    @GetMapping
    public String getActorsView(Model model) {
        List<Actor> actorsList = actorService.getActors();
        model.addAttribute("actors", actorsList);
        return "actors";
    }

    @GetMapping("/{id}")
    public String getActorId(@PathVariable Integer id, Model model) {
        Actor actor = actorService.getActorById(id);
        model.addAttribute("actor", actor);
        return "extraActorInfo";
    }

    @GetMapping("/search")
    public String searchActors(@RequestParam(required = false, value = "gender") Actor.Gender gender,
                               @RequestParam(required = false, value = "nationality") String nationality,
                               Model model) {
        List<Actor> actors;
        if (gender != null && nationality != null) {
            actors = actorService.getByGenderAndNationality(gender, nationality);
        } else {
            actors = actorService.getActors();
        }
        model.addAttribute("actors", actors);
        return "actors";
    }

    @GetMapping("/addActor")
    public String getAddActor(Model model) {
        model.addAttribute("actor", new ActorViewModel());
        return "addActor";
    }

    @PostMapping("/addActor")
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

    @RequestMapping("/{id}")
    public String deleteActor(@PathVariable int id) {
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
