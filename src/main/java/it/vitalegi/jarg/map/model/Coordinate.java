package it.vitalegi.jarg.map.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Coordinate {
    int width;
    int height;

    @Override
    public int hashCode() {
        return Objects.hash(width, height);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return width == that.width && height == that.height;
    }

    public String toPrettyString() {
        return "(" + getWidth() + ", " + getHeight() + ")";
    }
}
