package it.vitalegi.jarg.player.user;

import lombok.Data;

import java.util.Objects;
import java.util.UUID;

@Data
public class User {
    UUID id;
    UserType type;

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }
}
