package it.vitalegi.jarg.being.model;

import lombok.Data;

import java.util.Objects;

@Data
public class Monster extends Subject {
    Trainer trainer;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Monster subject = (Monster) o;
        return Objects.equals(id, subject.id);
    }
    @Override
    public String toString() {
        return "Monster{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
