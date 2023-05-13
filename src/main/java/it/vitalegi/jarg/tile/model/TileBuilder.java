package it.vitalegi.jarg.tile.model;

public class TileBuilder {
    public static Tile grass() {
        Tile tile = new Tile();
        tile.tile = ' ';
        tile.setBlocked(false);
        return tile;
    }

    public static Tile obstacle() {
        Tile tile = new Tile();
        tile.tile = '#';
        tile.setBlocked(true);
        return tile;
    }
}
