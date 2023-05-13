package it.vitalegi.jarg.tile.model;

public class TileBuilder {
    public static Tile grassTile() {
        Tile tile = new Tile();
        tile.setCanStandOver(true);
        tile.setCanTraverseByFoot(true);
        return tile;
    }
}
