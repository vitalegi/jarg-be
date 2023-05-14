package it.vitalegi.jarg.util;

import it.vitalegi.jarg.map.model.Coordinate;

import java.util.Arrays;
import java.util.List;

public class NeighborsUtil {

    public static List<Coordinate> getNeighbors(Coordinate coordinate) {
        return ArrayUtil.shuffle(Arrays.asList(new Coordinate(coordinate.getWidth() - 1, coordinate.getHeight()),
                new Coordinate(coordinate.getWidth() + 1, coordinate.getHeight()),
                new Coordinate(coordinate.getWidth(), coordinate.getHeight() - 1),
                new Coordinate(coordinate.getWidth(), coordinate.getHeight() + 1)));
    }
}
