package it.vitalegi.jarg.util;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ArrayUtil {

    public static <E> List<E> shuffle(List<E> list) {
        var copy = list.stream().collect(Collectors.toList());
        Collections.shuffle(copy);
        return copy;
    }

}
