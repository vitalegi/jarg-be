package it.vitalegi.jarg.tile.model;

import lombok.Data;
import org.fusesource.jansi.Ansi;

@Data
public class ObstacleTile implements Tile {
    @Override
    public boolean isBlocked() {
        return true;
    }

    public void render(Ansi ansi) {
        ansi.bgRgb(0, 0, 0).a(' ').reset();
    }
}
