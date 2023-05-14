package it.vitalegi.jarg.distance.model;

import it.vitalegi.jarg.map.model.Coordinate;
import lombok.Data;

@Data
public class DijkstraNode extends Node {
    boolean visited;

    public DijkstraNode(Coordinate coordinate) {
        super(coordinate);
        visited = false;
    }
}
