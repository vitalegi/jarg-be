package it.vitalegi.jarg.battle.service;

import it.vitalegi.jarg.battle.model.Battle;
import it.vitalegi.jarg.battle.model.Event;
import it.vitalegi.jarg.battle.repository.BattleRepository;
import it.vitalegi.jarg.being.model.Subject;
import it.vitalegi.jarg.distance.filter.EmptyOrOccupiedByAllyTile;
import it.vitalegi.jarg.distance.filter.TraversableTile;
import it.vitalegi.jarg.distance.model.Node;
import it.vitalegi.jarg.distance.service.DijkstraService;
import it.vitalegi.jarg.map.model.Coordinate;
import it.vitalegi.jarg.util.NeighborsUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BattleService {

    @Autowired
    BattleRepository battleRepository;

    @Autowired
    DijkstraService dijkstraService;

    public UUID addBattle(Battle battle) {
        return battleRepository.save(battle);
    }

    public Subject addSubject(UUID battleId, int width, int height, Subject subject) {
        return addSubject(battleId, new Coordinate(width, height), subject);
    }

    public Subject addSubject(UUID battleId, Coordinate coordinate, Subject subject) {
        var battle = getBattle(battleId);
        checkExistsCoordinate(battleId, coordinate);
        if (!canStand(battleId, subject, coordinate)) {
            throw new IllegalArgumentException("Subject " + subject.getId() + " can't stand on " + coordinate);
        }
        battle.getMapPlacement().addSubject(coordinate, subject);
        battle.getTurnStatus().addSubject(subject);
        return subject;
    }

    public void attack(UUID battleId, Subject source, Subject target) {
        var targetStats = target.getStats();
        int damage = 1 + (int) (5 * Math.random());
        int remainingHp = targetStats.getHp() - damage;
        log.info("{} attacks {}, -{}HP, remaining {}HP", source.getName(), target.getName(), damage, remainingHp);
        if (remainingHp > 0) {
            targetStats.setHp(remainingHp);
            addEvent(battleId, source.getName() + " deals " + damage + "HP to " + target.getName());
        } else {
            log.info("{} is dead.", target.getName());
            addEvent(battleId, source.getName() + " kills " + target.getName());
            removeSubject(battleId, target);
        }
    }

    public boolean canStand(UUID battleId, Subject subject, Coordinate coordinate) {
        var battle = getBattle(battleId);

        if (!battle.getMap().hasTile(coordinate)) {
            return false;
        }
        if (battle.getMap().getTile(coordinate).isBlocked()) {
            return false;
        }
        var occupier = battle.getMapPlacement().getSubjects(coordinate);
        if (occupier.isEmpty()) {
            return true;
        }
        return occupier.contains(subject);
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

    public int getDistance(UUID battleId, Coordinate source, Coordinate target, Subject subject) {
        return 1;
    }

    public List<Subject> getEnemies(UUID battleId, Subject subject) {
        var battle = getBattle(battleId);
        return battle.getMapPlacement().getSubjects().stream().filter(s -> isEnemy(subject, s))
                     .collect(Collectors.toList());
    }

    public List<Coordinate> getMinimumPath(Map<Coordinate, ? extends Node> graph, Coordinate source,
                                           Coordinate target) {
        return dijkstraService.getMinimumPath(graph, source, target);
    }

    public List<Coordinate> getNeighbors(UUID battleId, Coordinate coordinate) {
        var battle = getBattle(battleId);
        var map = battle.getMap();
        return NeighborsUtil.getNeighbors(coordinate).stream().filter(map::hasTile).collect(Collectors.toList());
    }

    public Coordinate getPosition(UUID battleId, Subject subject) {
        return getBattle(battleId).getMapPlacement().getSubjectPlacement(subject);
    }

    public Map<Coordinate, ? extends Node> getWalkablePaths(UUID battleId, Subject subject) {
        var battle = getBattle(battleId);
        var traversableTile = new TraversableTile(battle);
        var emptyOrOccupiedByAlly = new EmptyOrOccupiedByAllyTile(battle, subject);
        Function<Coordinate, Boolean> accept = (c) -> traversableTile.apply(c) && emptyOrOccupiedByAlly.apply(c);

        return dijkstraService.getGraph(battle.getMap(), battle.getMapPlacement().getSubjectPlacement(subject), accept);
    }

    public boolean isAlly(Subject subject1, Subject subject2) {
        return subject1.getTeam().equals(subject2.getTeam());
    }

    public boolean isEnemy(Subject subject1, Subject subject2) {
        return !isAlly(subject1, subject2);
    }

    public void move(UUID battleId, Subject subject, List<Coordinate> path) {
        if (path.size() <= 1) {
            return;
        }
        var destination = path.get(1);
        getBattle(battleId).getMapPlacement().moveSubject(destination, subject);
        addEvent(battleId, subject.getName() + " moves to " + destination.toPrettyString());
    }

    protected void addEvent(UUID battleId, String event) {
        getBattle(battleId).getEvents()
                           .add(0, new Event(event));
    }

    protected void removeSubject(UUID battleId, Subject subject) {
        var battle = getBattle(battleId);
        battle.getMapPlacement().removeSubject(subject);
        battle.getTurnStatus().removeSubject(subject);
    }
}
