package it.vitalegi.jarg.being.model;

import it.vitalegi.jarg.player.user.User;
import lombok.Data;

import java.util.Objects;
import java.util.UUID;

@Data
public class Team {
    UUID id;
    User owner;

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return Objects.equals(id, team.id);
    }
}
