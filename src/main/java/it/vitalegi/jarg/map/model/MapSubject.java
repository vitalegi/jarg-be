package it.vitalegi.jarg.map.model;

import it.vitalegi.jarg.being.model.Subject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MapSubject {
    Coordinate coordinate;
    Subject subject;
}
