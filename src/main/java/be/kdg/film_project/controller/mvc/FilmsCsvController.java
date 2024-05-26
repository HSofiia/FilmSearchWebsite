package be.kdg.film_project.controller.mvc;

import be.kdg.film_project.service.FilmService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Controller
@RequestMapping("/films-csv")
public class FilmsCsvController {
    private final FilmService service;

    public FilmsCsvController(FilmService service) {
        this.service = service;
    }

    @GetMapping
    public ModelAndView uploadCsv() {
        var mav = new ModelAndView("films-csv");
        mav.addObject("inProgress", false);
        return mav;
    }

    @PostMapping
    public ModelAndView uploadCsv(
            @RequestParam("films-csv") MultipartFile file) throws IOException {
        var mav = new ModelAndView("films-csv");
        service.handleFilmsCsv(file.getInputStream());
        mav.addObject("inProgress", true);
        return mav;
    }

}
