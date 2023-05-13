package it.vitalegi.jarg.being.model;

import lombok.Data;

@Data
public class Monster extends Subject {
    Trainer trainer;
    Team team;
}
