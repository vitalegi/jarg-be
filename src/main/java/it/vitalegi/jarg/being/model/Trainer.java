package it.vitalegi.jarg.being.model;

import lombok.Data;

import java.util.Objects;

@Data
public class Trainer extends Subject {
    Team team;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trainer subject = (Trainer) o;
        return Objects.equals(id, subject.id);
    }
    @Override
    public String toString() {
        return "Trainer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
