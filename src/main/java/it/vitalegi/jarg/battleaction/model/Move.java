package it.vitalegi.jarg.battleaction.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Move extends BattleAction {
    int spaces;
}
