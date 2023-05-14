package it.vitalegi.jarg.ai.model;

import it.vitalegi.jarg.being.model.Subject;
import it.vitalegi.jarg.map.model.Coordinate;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Target {
    Subject target;
    Coordinate sourceTargetPosition;
}
