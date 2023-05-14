package it.vitalegi.jarg.battle.model;

import it.vitalegi.jarg.map.model.IMap;
import it.vitalegi.jarg.being.model.MapPlacement;
import lombok.Data;

import java.util.List;

@Data
public class Battle {
    List<Event> events;
    IMap map;
    MapPlacement mapPlacement;
    TurnStatus turnStatus;
}
