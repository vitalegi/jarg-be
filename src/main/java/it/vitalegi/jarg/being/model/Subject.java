package it.vitalegi.jarg.being.model;

import lombok.Data;

import java.util.Objects;
import java.util.UUID;

@Data
public class Subject {
    UUID id;
    Team team;
    String name;
    int maxHp;
    Stats stats;

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subject subject = (Subject) o;
        return Objects.equals(id, subject.id);
    }
}
