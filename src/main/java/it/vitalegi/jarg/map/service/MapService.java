package it.vitalegi.jarg.map.service;

import it.vitalegi.jarg.map.model.IMap;
import it.vitalegi.jarg.tile.model.Tile;
import org.springframework.stereotype.Service;

@Service
public class MapService {

    public int getHeight(IMap map) {
        return map.getHeight();
    }

    public Tile getTile(IMap map, int x, int y) {
        return null;
    }

    public int getWidth(IMap map) {
        return map.getWidth();
    }

}
