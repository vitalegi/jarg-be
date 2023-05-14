package it.vitalegi.jarg.resource;

import it.vitalegi.jarg.battle.service.BattleService;
import it.vitalegi.jarg.metrics.Performance;
import it.vitalegi.jarg.metrics.Type;
import it.vitalegi.jarg.player.service.PlayerService;
import it.vitalegi.jarg.player.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Performance(Type.ENDPOINT)
@RequestMapping("battle")
public class UserResource {
    @Autowired
    PlayerService playerService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public User createPlayer() {
        return playerService.player();
    }
}
