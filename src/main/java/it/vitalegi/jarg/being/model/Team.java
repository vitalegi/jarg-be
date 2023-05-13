package it.vitalegi.jarg.being.model;

import lombok.Data;

import java.util.UUID;

@Data
public class Team {
    UUID id;
    User owner;
}
