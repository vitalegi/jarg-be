package it.vitalegi.jarg.tile.model;

import lombok.Data;
import org.fusesource.jansi.Ansi;

public interface Tile {
    boolean isBlocked();

    void render(Ansi ansi);
}
