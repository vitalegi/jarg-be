package it.vitalegi.jarg.tile.model;

import lombok.Data;
import org.fusesource.jansi.Ansi;

@Data
public class GrassTile implements Tile {


    @Override
    public boolean isBlocked() {
        return false;
    }

    public void render(Ansi ansi) {
        ansi.a(' ');
    }
}
