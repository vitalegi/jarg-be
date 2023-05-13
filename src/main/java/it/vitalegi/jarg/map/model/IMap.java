package it.vitalegi.jarg.map.model;

import it.vitalegi.jarg.tile.model.Tile;

public interface IMap {
    int getHeight();

    Tile getTile(Coordinate coordinate);

    int getWidth();

    void setTile(Coordinate coordinate, Tile tile);

}
