package be.kdg.film_project.presentation.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @Value("${sql-exception.message}")
    private String SQL_EXCEPTION_MESSAGE;
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(FilmException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleFilmException(FilmException ex, Model model) {
        logger.error("FilmException occurred: " + ex.getMessage(), ex);
        model.addAttribute("error", ex.getMessage());
        return "error";
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleGeneralException(Exception ex, Model model) {
        logger.error("Unexpected exception occurred: " + ex.getMessage(), ex);
        model.addAttribute("error", "An unexpected error occurred.");
        return "error";
    }


    @ExceptionHandler(value = {SQLException.class})
    private ModelAndView handleSQLException(SQLException ex, WebRequest request) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.getModel().put("serverError", HttpStatus.INTERNAL_SERVER_ERROR);
        modelAndView.getModel().put("exceptionMessage", SQL_EXCEPTION_MESSAGE);

        modelAndView.setViewName("error");
        logger.error("An exception occurred: " + ex.getMessage());
        return modelAndView;
    }
}
