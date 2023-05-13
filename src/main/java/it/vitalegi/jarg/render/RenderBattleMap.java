package it.vitalegi.jarg.render;

import it.vitalegi.jarg.battle.model.Battle;
import it.vitalegi.jarg.map.model.Coordinate;

public class RenderBattleMap {

    Battle battle;

    public RenderBattleMap(Battle battle) {
        this.battle = battle;
    }

    public void render() {

        StringBuilder sb = new StringBuilder();

        topFrame(sb);
        for (int height = 0; height <= battle.getMap().getHeight(); height++) {
            leftFrame(sb);
            for (int width = 0; width <= battle.getMap().getWidth(); width++) {
                sb.append(drawTile(width, height));
            }
            rightFrame(sb);
        }

        bottomFrame(sb);
        System.out.println(sb.toString());
    }

    protected void bottomFrame(StringBuilder sb) {
        for (int width = 0; width <= battle.getMap().getWidth() + 2; width++) {
            sb.append("#");
        }
        sb.append("\n");
    }

    protected char drawTile(int width, int height) {
        var c = new Coordinate(width, height);
        var subjects = battle.getMapPlacement().getSubjects(c);
        if (subjects.isEmpty()) {
            var tile = battle.getMap().getTile(c);
            return ' ';
        } else {
            return subjects.get(0).getName().charAt(0);
        }
    }

    protected void leftFrame(StringBuilder sb) {
        sb.append("#");
    }

    protected void rightFrame(StringBuilder sb) {
        sb.append("#\n");
    }

    protected void topFrame(StringBuilder sb) {
        for (int width = 0; width <= battle.getMap().getWidth() + 2; width++) {
            sb.append("#");
        }
        sb.append("\n");
    }
}
