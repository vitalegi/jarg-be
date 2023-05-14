package it.vitalegi.jarg.distance.filter;

import it.vitalegi.jarg.battle.model.Battle;
import it.vitalegi.jarg.map.model.Coordinate;

import java.util.function.Function;

public class TraversableTile implements Function<Coordinate, Boolean> {
    Battle battle;

    public TraversableTile(Battle battle) {
        this.battle = battle;
    }

    @Override
    public Boolean apply(Coordinate coordinate) {
        var tile = battle.getMap().getTile(coordinate);
        return !tile.isBlocked();
    }
}
