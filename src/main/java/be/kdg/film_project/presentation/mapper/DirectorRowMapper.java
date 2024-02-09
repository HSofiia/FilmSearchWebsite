package be.kdg.film_project.presentation.mapper;

import be.kdg.film_project.domain.Director;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class DirectorRowMapper implements RowMapper<Director> {
    @Override
    public Director mapRow(ResultSet rs, int rowNum) throws SQLException {
        Director director = new Director();
        director.setId(rs.getInt("ID"));
        director.setDirectorName(rs.getString("DIRECTOR_NAME"));
        director.setBirth(rs.getInt("BIRTH"));
        director.setAward(rs.getString("AWARD"));
        return director;
    }
}
