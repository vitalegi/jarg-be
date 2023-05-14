package it.vitalegi.jarg.map.model;

import it.vitalegi.jarg.tile.model.Tile;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class DefaultMap implements IMap {
    protected int width;
    protected int height;
    protected Map<Coordinate, Tile> tiles;

    public DefaultMap() {
        tiles = new HashMap<>();
    }

    public int getHeight() {
        return height;
    }

    public Tile getTile(Coordinate coordinate) {
        return tiles.get(coordinate);
    }

    public int getWidth() {
        return width;
    }

    public void setTile(Coordinate coordinate, Tile tile) {
        if (tile == null) {
            tiles.remove(coordinate);
        } else {
            tiles.put(coordinate, tile);
        }
        computeSize();
    }

    @Override
    public Stream<Coordinate> getCoordinates() {
        return tiles.keySet().stream();
    }

    public boolean hasTile(Coordinate coordinate) {
        return tiles.containsKey(coordinate);
    }

    protected void computeSize() {
        width = tiles.keySet().stream().mapToInt(Coordinate::getWidth).max().orElse(0);
        height = tiles.keySet().stream().mapToInt(Coordinate::getHeight).max().orElse(0);
    }
}