package be.kdg.film_project.controller;

import be.kdg.film_project.domain.Director;
import be.kdg.film_project.presentation.exceptions.ActorException;
import be.kdg.film_project.presentation.viewmodels.DirectorViewModel;
import be.kdg.film_project.service.DirectorService;
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
@RequestMapping("/directors")
public class DirectorController {
    private DirectorService directorService;
    private Logger logger = LoggerFactory.getLogger(DirectorController.class);

    public DirectorController(DirectorService directorService) {
        this.directorService = directorService;
    }

    @GetMapping
    public String getDirectorsView(Model model) {
        List<Director> directorList = directorService.getDirectors();
        model.addAttribute("directors", directorList);
        return "directors";
    }

    @GetMapping("/{id}")
    public String getDirectorId(@PathVariable Integer id, Model model) {
        Director director = directorService.getDirectorById(id);
        model.addAttribute("director", director);
        return "extraDirectorInfo";
    }

    @GetMapping("/search")
    public String searchDirectors(@RequestParam(required = false, value = "award") String award, Model model) {
        List<Director> directors;
        if (award != null) {
            directors = directorService.getByAward(award);
        } else {
            directors = directorService.getDirectors();
        }
        model.addAttribute("directors", directors);
        return "directors";
    }

    @GetMapping("/addDirector")
    public String getAddDirector(Model model) {
        model.addAttribute("director", new DirectorViewModel());
        return "addDirector";
    }

    @PostMapping("/addDirector")
    public String processAddDirectorForm(@Valid @ModelAttribute("director") DirectorViewModel viewModel, BindingResult result) {
        logger.info("Processing " + viewModel.toString());
        if (result.hasErrors()) {
            result.getAllErrors().forEach(e -> logger.warn(e.toString()));
            return "addDirector";
        } else {
            logger.info("Successfully processed ");
            directorService.addDirector(
                    viewModel.getDirectorName(),
                    viewModel.getBirth(),
                    viewModel.getAward());
            return "redirect:/directors";
        }
    }

    @RequestMapping("/{id}")
    public String deleteDirector(@PathVariable int id) {
        directorService.deleteDirector(id);
        return "redirect:/directors";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ActorException.class)
    public String handlePageException(Exception e, Model model) {
        logger.error("Error in exceptionHandler" + e);
        model.addAttribute("error", "The page is not found ;(");
        return "error";
    }
}
