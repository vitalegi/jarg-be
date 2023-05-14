package it.vitalegi.jarg.render;

import it.vitalegi.jarg.battle.model.Battle;
import it.vitalegi.jarg.being.model.Subject;
import it.vitalegi.jarg.player.user.User;
import it.vitalegi.jarg.map.model.Coordinate;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import java.util.Comparator;
import java.util.stream.Collectors;

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
        System.out.print("\033[H\033[2J");
        System.out.flush();

        Ansi ansi = ansi();

        topFrame(ansi);
        for (int height = 0; height <= battle.getMap().getHeight(); height++) {
            leftFrame(ansi);
            for (int width = 0; width <= battle.getMap().getWidth(); width++) {
                drawTile(ansi, width, height);
            }
            rightFrame(ansi);
            printCharacter(ansi, height);
            ansi.a("\n");
        }

        bottomFrame(ansi);
        renderEvents(ansi);
        System.out.println(ansi);
        AnsiConsole.systemUninstall();
    }

    protected void bottomFrame(Ansi ansi) {
        for (int width = 0; width <= battle.getMap().getWidth() + 2; width++) {
            ansi.bgRgb(255, 255, 255).a(' ').reset();
        }
        ansi.a("\n");
    }

    protected void drawTile(Ansi ansi, int width, int height) {
        var c = new Coordinate(width, height);
        var subjects = battle.getMapPlacement().getSubjects(c);
        if (subjects.isEmpty()) {
            battle.getMap().getTile(c).render(ansi);
        } else {
            renderSubject(ansi, subjects.get(0));
        }
    }

    protected String fixedLengthString(String str, int len) {
        if (str.length() > len) {
            return str.substring(0, len);
        }
        while (str.length() < len) {
            str += " ";
        }
        return str;
    }

    protected boolean isActiveCharacter(Subject subject) {
        var active = battle.getTurnStatus().getFirst();
        return active != null && active.getId().equals(subject.getId());
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
        ansi.bgRgb(255, 255, 255).a(' ').reset();
    }

    protected void printCharacter(Ansi ansi, int line) {
        ansi.a(" ");

        var subjects = battle.getMapPlacement().getSubjects().stream().sorted(Comparator.comparing(Subject::getName))
                             .collect(Collectors.toList());
        if (line < subjects.size()) {
            var subject = subjects.get(line);
            if (isActiveCharacter(subject)) {
                ansi.a("> ");
                setSubjectColor(ansi, subject);
                ansi.a(fixedLengthString(subject.getName(), 8));
            } else {
                setSubjectColor(ansi, subject);
                ansi.a(fixedLengthString(subject.getName(), 10));
            }
            ansi.reset();
            ansi.a(" " + subject.getStats().getHp() + "/" + subject.getMaxHp());
            ansi.a(" " + battle.getMapPlacement().getSubjectPlacement(subject));
        }
    }

    protected void renderEvents(Ansi ansi) {
        ansi.a("\n");
        battle.getEvents().stream().limit(10).forEach(e -> ansi.append(e.getMessage() + "\n"));
    }

    protected void renderSubject(Ansi ansi, Subject subject) {
        setSubjectColor(ansi, subject);
        ansi.a(subject.getName().charAt(0));
        ansi.reset();
    }

    protected void rightFrame(Ansi ansi) {
        ansi.bgRgb(255, 255, 255).a(' ').reset();
    }

    protected void setSubjectColor(Ansi ansi, Subject subject) {
        if (isAlly(subject)) {
            ansi.bgGreen();
        } else {
            ansi.bgRed();
        }
    }

    protected void topFrame(Ansi ansi) {
        ansi.bgRgb(255, 255, 255);
        for (int width = 0; width <= battle.getMap().getWidth() + 2; width++) {
            ansi.a(' ');
        }
        ansi.reset();
        ansi.a(" Characters");
        ansi.a("\n");
    }
}
