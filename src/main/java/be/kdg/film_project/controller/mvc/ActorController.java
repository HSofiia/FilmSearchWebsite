package be.kdg.film_project.controller.mvc;

import be.kdg.film_project.domain.Actor;
import be.kdg.film_project.presentation.exceptions.ActorException;
import be.kdg.film_project.controller.mvc.viewmodels.ActorViewModel;
import be.kdg.film_project.controller.mvc.viewmodels.FilmViewModel;
import be.kdg.film_project.security.CustomUserDetails;
import be.kdg.film_project.service.ActorService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import static be.kdg.film_project.domain.UserRole.ADMIN;


import java.util.List;

@Controller
public class ActorController {
    private final ActorService actorService;
    private Logger logger = LoggerFactory.getLogger(ActorController.class);

    public ActorController(ActorService actorService) {
        this.actorService = actorService;
    }

    @GetMapping("/actors")
    public ModelAndView allActors(@AuthenticationPrincipal CustomUserDetails user,
                                  HttpServletRequest request) {
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
                                request.isUserInRole(ADMIN.getCode())
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

//    @PostMapping("/actors/addActor")
//    public String processAddActorForm(@Valid @ModelAttribute("actor") ActorViewModel viewModel, BindingResult result) {
//        logger.info("Processing " + viewModel.toString());
//        if (result.hasErrors()) {
//            result.getAllErrors().forEach(e -> logger.warn(e.toString()));
//            return "addActor";
//        } else {
//            logger.info("Successfully processed ");
//            actorService.addActor(
//                    viewModel.getActorName(),
//                    viewModel.getGender(),
//                    viewModel.getNationality());
//            return "redirect:/actors";
//        }
//    }

//    @RequestMapping("/extraActorInfo")
//    public String deleteActor(@RequestParam("id") int id) {
//        actorService.deleteActor(id);
//        return "redirect:/actors";
//    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ActorException.class)
    public String handlePageException(Exception e, Model model) {
        logger.error("Error in exceptionHandler" + e);
        model.addAttribute("error", "The page is not found ;(");
        return "error";
    }
}
