package it.vitalegi.jarg.ai;

import it.vitalegi.jarg.battle.service.BattleService;
import it.vitalegi.jarg.being.model.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BattleAiFactory {

    @Autowired
    BattleService battleService;

    public BattleAI berserker(UUID battleId, Team team) {
        return new BerserkerAI(battleService, battleId, team);
    }
}
