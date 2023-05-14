package it.vitalegi.jarg.being.model;

import it.vitalegi.jarg.battleaction.model.BattleAction;
import lombok.Data;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Data
public class Subject {
    UUID id;
    Team team;
    String name;
    int maxHp;
    Stats stats;

    List<BattleAction> actions;

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

    @Override
    public String toString() {
        return "Subject{" + "id=" + id + ", name='" + name + '\'' + '}';
    }
}
