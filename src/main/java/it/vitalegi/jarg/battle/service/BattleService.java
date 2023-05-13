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
        checkCoordinate(battleId, coordinate);
        battle.getMapPlacement().addSubject(coordinate, subject);
        return subject;
    }

    public void checkCoordinate(UUID battleId, Coordinate coordinate) {
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
