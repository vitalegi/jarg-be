package it.vitalegi.jarg.render;

import it.vitalegi.jarg.battle.model.Battle;
import it.vitalegi.jarg.being.model.Subject;
import it.vitalegi.jarg.being.model.User;
import it.vitalegi.jarg.map.model.Coordinate;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import static org.fusesource.jansi.Ansi.ansi;

public class RenderBattleMap {

    Battle battle;
    User player;

    public RenderBattleMap(Battle battle, User player) {
        this.battle = battle;
        this.player = player;
    }

    public void render() {
        AnsiConsole.systemInstall();

        Ansi ansi = ansi();

        topFrame(ansi);
        for (int height = 0; height <= battle.getMap().getHeight(); height++) {
            leftFrame(ansi);
            for (int width = 0; width <= battle.getMap().getWidth(); width++) {
                drawTile(ansi, width, height);
            }
            rightFrame(ansi);
        }

        bottomFrame(ansi);

        System.out.println(ansi);
        AnsiConsole.systemUninstall();
    }

    protected void bottomFrame(Ansi ansi) {
        for (int width = 0; width <= battle.getMap().getWidth() + 2; width++) {
            ansi.append("#");
        }
        ansi.append("\n");
    }

    protected void drawTile(Ansi ansi, int width, int height) {
        var c = new Coordinate(width, height);
        var subjects = battle.getMapPlacement().getSubjects(c);
        if (subjects.isEmpty()) {
            var tile = battle.getMap().getTile(c);
            tile.render(ansi);
        } else {
            var subject = subjects.get(0);
            if (isAlly(subject)) {
                ansi.bgGreen();
            } else {
                ansi.bgRed();
            }
            ansi.a(subject.getName().charAt(0));
            ansi.reset();
        }
    }

    protected boolean isAlly(Subject subject) {
        if (subject.getTeam() == null) {
            return false;
        }
        if (subject.getTeam().getOwner() == null) {
            return false;
        }
        return subject.getTeam().getOwner().getId().equals(player.getId());
    }

    protected void leftFrame(Ansi ansi) {
        ansi.append("#");
    }

    protected void rightFrame(Ansi ansi) {
        ansi.append("#\n");
    }

    protected void topFrame(Ansi ansi) {
        for (int width = 0; width <= battle.getMap().getWidth() + 2; width++) {
            ansi.append("#");
        }
        ansi.append("\n");
    }
}
