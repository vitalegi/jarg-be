package it.vitalegi.jarg.battle.service;

import it.vitalegi.jarg.battle.model.Battle;
import it.vitalegi.jarg.battle.repository.BattleRepository;
import it.vitalegi.jarg.being.model.Subject;
import it.vitalegi.jarg.map.model.Coordinate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BattleService {

    @Autowired
    BattleRepository battleRepository;

    public UUID addBattle(Battle battle) {
        return battleRepository.save(battle);
    }

    public Subject addSubject(UUID battleId, int width, int height, Subject subject) {
        return addSubject(battleId, new Coordinate(width, height), subject);
    }

    public Subject addSubject(UUID battleId, Coordinate coordinate, Subject subject) {
        var battle = getBattle(battleId);
        checkExistsCoordinate(battleId, coordinate);
        if (!canStand(battleId, coordinate, subject)) {
            throw new IllegalArgumentException("Subject " + subject.getId() + " can't stand on " + coordinate);
        }
        battle.getMapPlacement().addSubject(coordinate, subject);
        battle.getTurnStatus().addSubject(subject);
        return subject;
    }

    public boolean canStand(UUID battleId, Coordinate coordinate, Subject subject) {
        var battle = getBattle(battleId);
        var tile = battle.getMap().getTile(coordinate);
        return !tile.isBlocked();
    }

    public void checkExistsCoordinate(UUID battleId, Coordinate coordinate) {
        if (!existsCoordinate(battleId, coordinate)) {
            throw new IllegalArgumentException("Coordinate " + coordinate + " is empty");
        }
    }

    public boolean existsCoordinate(UUID battleId, Coordinate coordinate) {
        var battle = getBattle(battleId);
        return battle.getMap().getTile(coordinate) != null;
    }

    public Battle getBattle(UUID id) {
        return battleRepository.getById(id);
    }

}
