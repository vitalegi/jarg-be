package it.vitalegi.jarg.tile.model;

public class TileBuilder {
    public static Tile grass() {
        return new GrassTile();
    }

    public static Tile obstacle() {
        return new ObstacleTile();
    }
}
