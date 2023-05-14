package it.vitalegi.jarg.being.model;

import it.vitalegi.jarg.map.model.Coordinate;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class MapPlacement {
    protected List<Subject> subjects;
    protected Map<Coordinate, List<Subject>> coordinatePlacements;
    protected Map<Subject, Coordinate> subjectsPlacement;

    public MapPlacement() {
        subjects = new ArrayList<>();
        coordinatePlacements = new HashMap<>();
        subjectsPlacement = new HashMap<>();
    }

    public void addSubject(Coordinate coordinate, Subject subject) {
        subjects.add(subject);
        if (!coordinatePlacements.containsKey(coordinate)) {
            coordinatePlacements.put(coordinate, new ArrayList<>());
        }
        coordinatePlacements.get(coordinate).add(subject);
        subjectsPlacement.put(subject, coordinate);
    }

    public Coordinate getSubjectPlacement(Subject subject) {
        return subjectsPlacement.get(subject);
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public List<Subject> getSubjects(Coordinate coordinate) {
        if (coordinatePlacements.containsKey(coordinate)) {
            return coordinatePlacements.get(coordinate);
        }
        return Collections.emptyList();
    }

    public void moveSubject(Coordinate newCoordinate, Subject subject) {
        var oldCoordinate = subjectsPlacement.get(subject);
        subjectsPlacement.put(subject, newCoordinate);
        coordinatePlacements.get(oldCoordinate).remove(subject);
        if (!coordinatePlacements.containsKey(newCoordinate)) {
            coordinatePlacements.put(newCoordinate, new ArrayList<>());
        }
        coordinatePlacements.get(newCoordinate).add(subject);
        log.info("old ({}): {}, new ({}): {}", oldCoordinate, coordinatePlacements.get(oldCoordinate).stream()
                                                                                  .map(Subject::getId)
                                                                                  .collect(Collectors.toList()),
                newCoordinate, coordinatePlacements.get(newCoordinate)
                                                   .stream()
                                                   .map(Subject::getId)
                                                   .collect(Collectors.toList()));
    }

    public void removeSubject(Subject subject) {
        subjects.remove(subject);
        var coordinate = subjectsPlacement.remove(subject);
        coordinatePlacements.get(coordinate).remove(subject);
    }
}
