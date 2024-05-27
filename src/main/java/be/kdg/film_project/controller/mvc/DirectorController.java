package be.kdg.film_project.controller.mvc;

import be.kdg.film_project.controller.mvc.viewmodels.ActorViewModel;
import be.kdg.film_project.domain.Director;
import be.kdg.film_project.presentation.exceptions.ActorException;
import be.kdg.film_project.controller.mvc.viewmodels.DirectorViewModel;
import be.kdg.film_project.security.CustomUserDetails;
import be.kdg.film_project.service.DirectorService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static be.kdg.film_project.domain.UserRole.ADMIN;
import static be.kdg.film_project.domain.UserRole.USER;

@Controller
public class DirectorController {
    private DirectorService directorService;
    private Logger logger = LoggerFactory.getLogger(DirectorController.class);

    public DirectorController(DirectorService directorService) {
        this.directorService = directorService;
    }

    @GetMapping("/directors")
    public ModelAndView allDirectors(HttpServletRequest request) {
        var mav = new ModelAndView();
        mav.setViewName("directors");
        mav.addObject("all_directors",
                directorService.getDirectors()
                        .stream()
                        .map(actor -> new DirectorViewModel(
                                actor.getId(),
                                actor.getDirectorName(),
                                actor.getBirth(),
                                actor.getAward(),
                                request.isUserInRole(ADMIN.getCode()) || request.isUserInRole(USER.getCode())
                        ))
                        .toList());
        return mav;
    }

    @GetMapping("/extraDirectorInfo")
    public ModelAndView oneDirector(@RequestParam("id") int id,
                                    @AuthenticationPrincipal CustomUserDetails user,
                                    HttpServletRequest request) {
        var director = directorService.getDirectorById(id);
        var mav = new ModelAndView();
        mav.setViewName("extraDirectorInfo");
        mav.addObject("one_director",
                new DirectorViewModel(
                        director.getId(),
                        director.getDirectorName(),
                        director.getBirth(),
                        director.getAward(),
                        user != null && (request.isUserInRole(ADMIN.getCode())
                        )));
        return mav;
    }

    @GetMapping("/directors/search")
    public ModelAndView searchDirectors(@RequestParam(required = false, value = "award") String award) {
        var mav = new ModelAndView("directors");
        List<Director> directors;
        if (award != null) {
            directors = directorService.getByAward(award);
        } else {
            directors = directorService.getDirectors();
        }
        List<DirectorViewModel> directorViewModels = directors.stream()
                .map(director -> new DirectorViewModel(
                        director.getId(),
                        director.getDirectorName(),
                        director.getBirth(),
                        director.getAward()
                )).toList();

        mav.addObject("all_directors", directorViewModels);
        return mav;
    }

    @GetMapping("/addDirector")
    public String getAddDirector(Model model) {
        model.addAttribute("director", new DirectorViewModel());
        return "/addDirector";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ActorException.class)
    public String handlePageException(Exception e, Model model) {
        logger.error("Error in exceptionHandler" + e);
        model.addAttribute("error", "The page is not found ;(");
        return "error";
    }
}
