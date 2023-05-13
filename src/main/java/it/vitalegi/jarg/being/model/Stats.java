package it.vitalegi.jarg.being.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Stats {
    int hp;
    int attack;
    int defence;

    public Stats(int hp, int attack, int defence) {
        this.hp = hp;
        this.attack = attack;
        this.defence = defence;
    }
}
