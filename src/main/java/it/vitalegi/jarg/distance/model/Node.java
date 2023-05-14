package it.vitalegi.jarg.distance.model;

import it.vitalegi.jarg.map.model.Coordinate;
import lombok.Data;

@Data
public class Node {
    Coordinate coordinate;
    Node previousNode;
    int distance;

    public Node(Coordinate coordinate) {
        this.coordinate = coordinate;
        distance = Integer.MAX_VALUE;
    }
}
