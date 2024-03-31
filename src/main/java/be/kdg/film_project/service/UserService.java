package be.kdg.film_project.service;

import be.kdg.film_project.domain.User;

public interface UserService {
    User getUserByName(String username);
}
