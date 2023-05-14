package it.vitalegi.jarg.distance.service;

import it.vitalegi.jarg.distance.model.DijkstraNode;
import it.vitalegi.jarg.distance.model.Node;
import it.vitalegi.jarg.map.model.Coordinate;
import it.vitalegi.jarg.map.model.IMap;
import it.vitalegi.jarg.util.NeighborsUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class DijkstraService {


    public Map<Coordinate, ? extends Node> getGraph(IMap map, Coordinate source, Function<Coordinate, Boolean> accept) {
        var graph = graph(map);
        graph.get(source).setDistance(0);

        var unvisited = new ArrayList<DijkstraNode>();
        unvisited.add(graph.get(source));
        while (!unvisited.isEmpty()) {
            // find next node to visit
            var currentNode = getLowestDistanceNode(unvisited);
            currentNode.setVisited(true);
            unvisited.remove(currentNode);

            // retrieve node neighbors
            getNeighbors(graph, currentNode).forEach(neighbor -> {
                int newDistance = currentNode.getDistance() + 1;
                if (newDistance < neighbor.getDistance()) {
                    neighbor.setDistance(newDistance);
                    neighbor.setPreviousNode(currentNode);
                }

                if (notVisited(neighbor, unvisited) && accept.apply(neighbor.getCoordinate())) {
                    unvisited.add(neighbor);
                }
            });
        }
        return graph;
    }

    public List<Coordinate> getMinimumPath(Map<Coordinate, ? extends Node> graph, Coordinate source,
                                           Coordinate target) {
        LinkedList<Coordinate> coordinates = new LinkedList<>();
        Node current = graph.get(target);
        if (source == null) {
            throw new NullPointerException("Source is null");
        }
        if (current == null) {
            throw new NullPointerException("Current is null");
        }
        while (current != null && !source.equals(current.getCoordinate())) {
            coordinates.addFirst(current.getCoordinate());
            current = current.getPreviousNode();
        }
        coordinates.addFirst(source);
        return coordinates;
    }

    protected DijkstraNode getLowestDistanceNode(List<DijkstraNode> unvisited) {
        DijkstraNode lowestDistanceNode = null;
        int lowestDistance = Integer.MAX_VALUE;
        for (var node : unvisited) {
            if (node.getDistance() < lowestDistance) {
                lowestDistanceNode = node;
                lowestDistance = node.getDistance();
            }
        }
        return lowestDistanceNode;
    }

    protected Stream<DijkstraNode> getNeighbors(Map<Coordinate, DijkstraNode> graph, DijkstraNode source) {
        return NeighborsUtil.getNeighbors(source.getCoordinate()).stream().filter(graph::containsKey).map(graph::get);
    }

    protected Map<Coordinate, DijkstraNode> graph(IMap map) {
        return map.getCoordinates().collect(Collectors.toMap(Function.identity(), DijkstraNode::new));
    }

    protected boolean notVisited(DijkstraNode neighbor, List<DijkstraNode> unvisitedNodes) {
        if (neighbor.isVisited()) {
            return false;
        }
        return unvisitedNodes.stream().noneMatch(u -> u.getCoordinate().equals(neighbor.getCoordinate()));
    }
}
