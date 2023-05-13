package it.vitalegi.jarg.map.model;

import it.vitalegi.jarg.tile.model.Tile;

public class MapBuilder {
    IMap map;

    public MapBuilder(IMap map) {
        this.map = map;
    }

    public IMap build() {
        return map;
    }

    public MapBuilder tile(Coordinate coordinate, Tile tile) {
        map.setTile(coordinate, tile);
        return this;
    }

    public MapBuilder tiles(int fromWidth, int toWidth, int fromHeight, int toHeight, Tile tile) {
        for (int width = fromWidth; width < toWidth; width++) {
            for (int height = fromHeight; height < toHeight; height++) {
                tile(new Coordinate(width, height), tile);
            }
        }
        return this;
    }

}
