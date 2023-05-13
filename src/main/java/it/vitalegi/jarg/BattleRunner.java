package it.vitalegi.jarg;

import it.vitalegi.jarg.battle.model.Battle;
import it.vitalegi.jarg.battle.model.TurnStatus;
import it.vitalegi.jarg.battle.service.BattleService;
import it.vitalegi.jarg.being.model.MapPlacement;
import it.vitalegi.jarg.being.model.Monster;
import it.vitalegi.jarg.being.model.Stats;
import it.vitalegi.jarg.being.model.Team;
import it.vitalegi.jarg.being.model.Trainer;
import it.vitalegi.jarg.being.model.User;
import it.vitalegi.jarg.being.model.UserType;
import it.vitalegi.jarg.map.model.MapBuilder;
import it.vitalegi.jarg.render.RenderBattleMap;
import it.vitalegi.jarg.tile.model.TileBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class BattleRunner implements CommandLineRunner {

    @Autowired
    BattleService battleService;

    @Override
    public void run(String... args) throws Exception {

        var battle = new Battle();
        battle.setTurnStatus(new TurnStatus());
        battle.setMapPlacement(new MapPlacement());
        battle.setMap( //
                new MapBuilder() //
                                 .tiles(0, 20, 0, 6, TileBuilder.grass()) //
                                 .tiles(4, 5, 5, 6, TileBuilder.obstacle()) //
                                 .build());

        var id = battleService.addBattle(battle);

        var player = player();
        var team1 = team(player);
        var trainer1 = trainer(team1);
        trainer1.setName("T");
        battleService.addSubject(id, 0, 0, trainer1);
        battleService.addSubject(id, 0, 1, monster(team1, trainer1, "A", 20, 5, 3));
        battleService.addSubject(id, 3, 2, monster(team1, trainer1, "B", 20, 5, 3));
        battleService.addSubject(id, 0, 2, monster(team1, trainer1, "C", 20, 5, 3));

        var npc = npc();
        var team2 = team(npc);
        battleService.addSubject(id, 4, 4, monster(team2, null, "D", 20, 5, 3));
        battleService.addSubject(id, 8, 4, monster(team2, null, "E", 20, 5, 3));
        battleService.addSubject(id, 3, 6, monster(team2, null, "F", 20, 5, 3));

        RenderBattleMap renderer = new RenderBattleMap(battle, player);

        renderer.render();

        System.exit(0);
    }

    Monster monster(Team team, Trainer trainer, String name, int hp, int atk, int def) {
        var monster = new Monster();
        monster.setId(UUID.randomUUID());
        monster.setName(name);
        monster.setMaxHp(hp);
        monster.setStats(new Stats(hp, atk, def));
        monster.setTeam(team);
        monster.setTrainer(trainer);
        return monster;
    }

    User npc() {
        var user = new User();
        user.setId(UUID.randomUUID());
        user.setType(UserType.NPC);
        return user;
    }

    User player() {
        var user = new User();
        user.setId(UUID.randomUUID());
        user.setType(UserType.PLAYER);
        return user;
    }

    Team team(User owner) {
        var team = new Team();
        team.setId(UUID.randomUUID());
        team.setOwner(owner);
        return team;
    }

    Trainer trainer(Team team) {
        var trainer = new Trainer();
        trainer.setTeam(team);
        return trainer;
    }
}
