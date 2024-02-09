package be.kdg.film_project.presentation.mapper;

import be.kdg.film_project.domain.Actor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ActorRowMapper implements RowMapper<Actor> {

    @Override
    public Actor mapRow(ResultSet rs, int rowNum) throws SQLException {
        Actor actor = new Actor();
        actor.setId(rs.getInt("ID"));
        actor.setActorName(rs.getString("ACTOR_NAME"));
        actor.setGender(Actor.Gender.valueOf(rs.getString("GENDER")));
        actor.setNationality(rs.getString("NATIONALITY"));
        return actor;
    }
}
