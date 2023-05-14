package it.vitalegi.jarg.ai;

import it.vitalegi.jarg.ai.model.Target;
import it.vitalegi.jarg.battle.service.BattleService;
import it.vitalegi.jarg.being.model.Subject;
import it.vitalegi.jarg.being.model.Team;
import it.vitalegi.jarg.distance.model.Node;
import it.vitalegi.jarg.map.model.Coordinate;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
public class BerserkerAI extends BattleAI {
    public BerserkerAI(BattleService battleService, UUID battleId, Team team) {
        super(battleService, battleId, team);
    }

    @Override
    public void executeTurn(Subject subject) {
        log.info("{} ({}): Find nearest enemy, go next it and attack", subject.getName(), getPosition(subject));
        var paths = getBattleService().getWalkablePaths(getBattleId(), subject);
        Target nearestTarget = getNearestEnemy(paths, subject);
        if (nearestTarget == null) {
            log.info("No enemy nearby");
            return;
        }
        var enemyPosition = getPosition(nearestTarget.getTarget());
        var targetPosition = nearestTarget.getSourceTargetPosition();
        log.info("Nearest enemy: {}", nearestTarget.getTarget().getName(), enemyPosition);
        log.info("Target position: {}", targetPosition);
        var distanceToTarget = paths.get(targetPosition).getDistance();
        if (distanceToTarget >= 1) {
            log.info("Position to reach: {}, distance: {}", targetPosition, distanceToTarget);
            var path = getBattleService().getMinimumPath(paths, getPosition(subject), targetPosition);
            log.info("Path between {} and {}: {}", getPosition(subject), targetPosition, path);
            move(subject, path);
        }
        paths = getBattleService().getWalkablePaths(getBattleId(), subject);
        var newEnemyDistance = paths.get(enemyPosition).getDistance();
        if (newEnemyDistance == 1) {
            log.info("Enemy in melee range!");
            attack(subject, nearestTarget.getTarget());
        } else {
            log.info("Enemy is {} feet away", newEnemyDistance);
        }
    }

    protected void attack(Subject source, Subject target) {
        getBattleService().attack(getBattleId(), source, target);
    }

    protected List<Coordinate> getAvailableLocationsAdjacentEnemy(Subject enemy, Subject subject) {
        return getNeighbors(getPosition(enemy)).stream() //
                                               .distinct() //
                                               .filter(c -> canStand(subject, c)) //
                                               .collect(Collectors.toList());
    }


    protected Target getNearestEnemy(Map<Coordinate, ? extends Node> paths, Subject subject) {
        var enemies = getBattleService().getEnemies(getBattleId(), subject);
        int minDistance = Integer.MAX_VALUE;
        Subject nearest = null;
        Coordinate nearestCoordinate = null;

        for (var enemy : enemies) {
            var coordinates = getAvailableLocationsAdjacentEnemy(enemy, subject);
            for (var coordinate : coordinates) {
                var distance = paths.get(coordinate).getDistance();
                if (distance < minDistance) {
                    minDistance = distance;
                    nearest = enemy;
                    nearestCoordinate = coordinate;
                }
            }
        }
        if (nearest == null) {
            return null;
        }
        return new Target(nearest, nearestCoordinate);
    }

    protected void move(Subject subject, List<Coordinate> path) {
        getBattleService().move(getBattleId(), subject, path);
    }


}
