package it.vitalegi.jarg.resource;

import it.vitalegi.jarg.battle.model.Battle;
import it.vitalegi.jarg.battle.service.BattleBuilderService;
import it.vitalegi.jarg.battle.service.BattleService;
import it.vitalegi.jarg.metrics.Performance;
import it.vitalegi.jarg.metrics.Type;
import it.vitalegi.jarg.player.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@Performance(Type.ENDPOINT)
@RequestMapping("battle")
public class BattleResource {
    @Autowired
    BattleService battleService;

    @Autowired
    BattleBuilderService battleBuilderService;

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Battle getBattle(@PathVariable("id") UUID battleId) {
        return battleService.getBattle(battleId);
    }
}
