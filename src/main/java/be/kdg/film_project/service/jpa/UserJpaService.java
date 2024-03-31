package be.kdg.film_project.service.jpa;

import be.kdg.film_project.domain.User;
import be.kdg.film_project.repository.jpa.UserRepository;
import be.kdg.film_project.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserJpaService implements UserService {
    private final UserRepository userRepository;

    public UserJpaService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUserByName(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }
}
