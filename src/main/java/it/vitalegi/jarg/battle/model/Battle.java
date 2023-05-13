package it.vitalegi.jarg.battle.model;

import it.vitalegi.jarg.map.model.IMap;
import it.vitalegi.jarg.being.model.MapPlacement;
import lombok.Data;

@Data
public class Battle {
    IMap map;
    MapPlacement mapPlacement;
    TurnStatus turnStatus;
}
