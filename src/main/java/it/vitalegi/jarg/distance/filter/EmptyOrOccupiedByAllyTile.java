package it.vitalegi.jarg.distance.filter;

import it.vitalegi.jarg.battle.model.Battle;
import it.vitalegi.jarg.being.model.Subject;
import it.vitalegi.jarg.map.model.Coordinate;

import java.util.function.Function;

public class EmptyOrOccupiedByAllyTile implements Function<Coordinate, Boolean> {

    Battle battle;
    Subject subject;

    public EmptyOrOccupiedByAllyTile(Battle battle, Subject subject) {
        this.battle = battle;
        this.subject = subject;
    }

    @Override
    public Boolean apply(Coordinate coordinate) {
        var occupiers = battle.getMapPlacement().getSubjects(coordinate);
        if (occupiers.isEmpty()) {
            return true;
        }
        return subject.getTeam().equals(occupiers.get(0).getTeam());
    }
}
