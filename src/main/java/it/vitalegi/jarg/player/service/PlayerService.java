package it.vitalegi.jarg.player.service;

import it.vitalegi.jarg.metrics.Performance;
import it.vitalegi.jarg.metrics.Type;
import it.vitalegi.jarg.player.user.User;
import it.vitalegi.jarg.player.user.UserType;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Performance(Type.SERVICE)
public class PlayerService {

    public User player() {
        var user = new User();
        user.setId(UUID.randomUUID());
        user.setType(UserType.PLAYER);
        return user;
    }
}
