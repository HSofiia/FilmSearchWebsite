package be.kdg.film_project.controller.mvc;

import be.kdg.film_project.controller.api.dto.actor.ActorDto;
import be.kdg.film_project.controller.mvc.viewmodels.ActorViewModel;
import be.kdg.film_project.domain.Actor;
import be.kdg.film_project.presentation.exceptions.ActorException;
import be.kdg.film_project.security.CustomUserDetails;
import be.kdg.film_project.service.ActorService;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static be.kdg.film_project.domain.UserRole.ADMIN;
import static be.kdg.film_project.domain.UserRole.USER;

@Controller
public class ActorController {
    private final ActorService actorService;
    private Logger logger = LoggerFactory.getLogger(ActorController.class);
    private final ModelMapper modelMapper;

    public ActorController(ActorService actorService, ModelMapper modelMapper) {
        this.actorService = actorService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/actors")
    public ModelAndView allActors(HttpServletRequest request) {
        var mav = new ModelAndView();
        mav.setViewName("actors");
        mav.addObject("all_actors",
                actorService.getActors()
                        .stream()
                        .map(actor -> new ActorViewModel(
                                actor.getId(),
                                actor.getActorName(),
                                actor.getGender(),
                                actor.getNationality(),
                                request.isUserInRole(ADMIN.getCode()) || request.isUserInRole(USER.getCode())
                        ))
                        .toList());
        return mav;
    }

    @GetMapping("/extraActorInfo")
    public ModelAndView oneActor(@RequestParam("id") int id,
                                 @AuthenticationPrincipal CustomUserDetails user,
                                 HttpServletRequest request) {
        var actor = actorService.getActor(id);
        var mav = new ModelAndView();
        mav.setViewName("extraActorInfo");
        mav.addObject("one_actor",
                new ActorViewModel(
                        actor.getId(),
                        actor.getActorName(),
                        actor.getGender(),
                        actor.getNationality(),
                        user != null && (request.isUserInRole(ADMIN.getCode())
                        )));
        return mav;
    }

    @GetMapping("/actors/search")
    public ModelAndView searchActors(@RequestParam(required = false, value = "gender") Actor.Gender gender,
                                     @RequestParam(required = false, value = "nationality") String nationality) {
        var mav = new ModelAndView("actors");
        List<Actor> actors;
        if (gender != null && nationality != null) {
            actors = actorService.getByGenderAndNationality(gender, nationality);
        } else {
            actors = actorService.getActors();
        }
        List<ActorViewModel> actorViewModels = actors.stream()
                .map(actor -> new ActorViewModel(
                        actor.getId(),
                        actor.getActorName(),
                        actor.getGender(),
                        actor.getNationality()
                )).toList();
        mav.addObject("all_actors",actorViewModels);
        return mav;
    }

    @GetMapping("/addActor")
    public String getAddActor(Model model) {
        model.addAttribute("actor", new ActorViewModel());
        return "/addActor";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ActorException.class)
    public String handlePageException(Exception e, Model model) {
        logger.error("Error in exceptionHandler" + e);
        model.addAttribute("error", "The page is not found ;(");
        return "error";
    }
}
