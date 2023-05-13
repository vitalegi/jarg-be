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

    public void addSubject(TurnStatus turnStatus, Subject subject) {
        turnStatus.getTurns().add(subject);
    }

    public void addSubjects(TurnStatus turnStatus, List<Subject> subjects) {

    }

    public Subject getFirst(TurnStatus turnStatus) {
        return turnStatus.getTurns().get(0);
    }

    public void next(TurnStatus turnStatus) {
        var subject = turnStatus.getTurns().remove(0);
        turnStatus.getTurns().add(subject);
    }
}
