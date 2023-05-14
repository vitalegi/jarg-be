package it.vitalegi.jarg.ai;

import it.vitalegi.jarg.battle.model.Battle;
import it.vitalegi.jarg.battle.service.BattleService;
import it.vitalegi.jarg.being.model.Subject;
import it.vitalegi.jarg.being.model.Team;
import it.vitalegi.jarg.map.model.Coordinate;

import java.util.List;
import java.util.UUID;

public abstract class BattleAI {

    private BattleService battleService;
    private UUID battleId;
    private Team team;

    public BattleAI(BattleService battleService, UUID battleId, Team team) {
        this.battleService = battleService;
        this.battleId = battleId;
        this.team = team;
    }

    public boolean controls(Subject subject) {
        return team.equals(subject.getTeam());
    }

    public abstract void executeTurn(Subject subject);

    protected boolean canStand(Subject subject, Coordinate coordinate) {
        return getBattleService().canStand(getBattleId(), subject, coordinate);
    }

    protected Battle getBattle() {
        return getBattleService().getBattle(getBattleId());
    }

    protected UUID getBattleId() {
        return battleId;
    }

    protected BattleService getBattleService() {
        return battleService;
    }

    protected List<Coordinate> getNeighbors(Coordinate coordinate) {
        return getBattleService().getNeighbors(getBattleId(), coordinate);
    }

    protected Coordinate getPosition(Subject subject) {
        return getBattleService().getPosition(getBattleId(), subject);
    }

    protected Team getTeam() {
        return team;
    }

}
