package be.kdg.film_project.controller.mvc;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Calendar;

@Controller
public class HomeController {
@RequestMapping("/home")
public String home(HttpSession session){
    session.setAttribute("today", Calendar.getInstance().getTime());
    return "home";
    }
}


