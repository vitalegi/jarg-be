package it.vitalegi.jarg.battle.model;

import it.vitalegi.jarg.being.model.Subject;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
public class TurnStatus {
    List<Subject> turns;

    public TurnStatus() {
        turns = new LinkedList<>();
    }

    public void addSubject(Subject subject) {
        turns.add(subject);
    }

    public void addSubjects(List<Subject> subjects) {

    }

    public Subject getFirst() {
        return turns.get(0);
    }

    public void next() {
        var subject = turns.remove(0);
        turns.add(subject);
    }
}
