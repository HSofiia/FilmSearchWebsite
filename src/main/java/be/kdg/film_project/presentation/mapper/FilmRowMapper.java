package be.kdg.film_project.presentation.mapper;

import be.kdg.film_project.domain.Film;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class FilmRowMapper implements RowMapper<Film> {

    @Override
    public Film mapRow(ResultSet rs, int rowNum) throws SQLException {
        Film film = new Film();
        film.setId(rs.getInt("ID"));
        film.setFilmName(rs.getString("FILM_NAME"));
        film.setYear(rs.getDate("RELEASE_YEAR").toLocalDate());
        film.setBoxOffice(rs.getDouble("BOX_OFFICE"));
        film.setGenre(Film.Genre.valueOf(rs.getString("GENRE")));
        return film;
    }
}
