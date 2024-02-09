package be.kdg.film_project.presentation.converter;

import be.kdg.film_project.domain.Actor;
import org.springframework.core.convert.converter.Converter;

public class StringToGenderTypeConverter implements Converter<String, Actor.Gender> {
    @Override
    public Actor.Gender convert(String source) {
        return switch (source.substring(0, Math.min(source.length(), 3)).toUpperCase()) {
            case "M" -> Actor.Gender.M;
            case "F" -> Actor.Gender.F;
            default -> Actor.Gender.N;
        };
    }
}
