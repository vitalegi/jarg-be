package it.vitalegi.jarg.map.model;

import it.vitalegi.jarg.tile.model.Tile;

import java.util.stream.Stream;

public interface IMap {
    Stream<Coordinate> getCoordinates();

    int getHeight();

    Tile getTile(Coordinate coordinate);

    int getWidth();

    boolean hasTile(Coordinate coordinate);

    void setTile(Coordinate coordinate, Tile tile);
}
