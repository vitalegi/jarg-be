package it.vitalegi.jarg.battle.repository;

import it.vitalegi.jarg.battle.model.Battle;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class BattleRepository {

    Map<UUID, Battle> battles;

    public BattleRepository() {
        battles = new HashMap<>();
    }

    public Battle getById(UUID id) {
        return battles.get(id);
    }

    public UUID save(Battle battle) {
        var id = UUID.randomUUID();
        battles.put(id, battle);
        return id;
    }
}
