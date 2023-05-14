package it.vitalegi.jarg;

import it.vitalegi.jarg.ai.BattleAI;
import it.vitalegi.jarg.ai.BattleAiFactory;
import it.vitalegi.jarg.battle.model.Battle;
import it.vitalegi.jarg.battle.model.TurnStatus;
import it.vitalegi.jarg.battle.service.BattleService;
import it.vitalegi.jarg.battleaction.model.BattleAction;
import it.vitalegi.jarg.battleaction.model.Move;
import it.vitalegi.jarg.battleaction.model.SimpleAttack;
import it.vitalegi.jarg.being.model.MapPlacement;
import it.vitalegi.jarg.being.model.Monster;
import it.vitalegi.jarg.being.model.Stats;
import it.vitalegi.jarg.being.model.Subject;
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
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Profile("run")
@Slf4j
@Component
public class BattleRunner implements CommandLineRunner {

    @Autowired
    BattleService battleService;
    @Autowired
    BattleAiFactory battleAiFactory;

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

        var battleAIs = new ArrayList<BattleAI>();
        var player = player();
        var team1 = team(player);
        battleAIs.add(battleAiFactory.berserker(id, team1));
        var trainer1 = trainer(team1, "Trainer", 10, 1, 1);
        battleService.addSubject(id, 0, 0, trainer1);
        battleService.addSubject(id, 0, 1, monster(team1, trainer1, "Abcdefghilj", 20, 5, 3));
        battleService.addSubject(id, 3, 2, monster(team1, trainer1, "B", 20, 5, 3));
        battleService.addSubject(id, 0, 2, monster(team1, trainer1, "C", 20, 5, 3));
        battleService.addSubject(id, 4, 1, monster(team1, trainer1, "D", 20, 5, 3));

        var npc = npc();
        var team2 = team(npc);
        battleAIs.add(battleAiFactory.berserker(id, team2));
        battleService.addSubject(id, 4, 4, monster(team2, null, "E", 20, 5, 3));
        battleService.addSubject(id, 8, 4, monster(team2, null, "F", 20, 5, 3));
        battleService.addSubject(id, 3, 6, monster(team2, null, "G", 20, 5, 3));

        RenderBattleMap renderer = new RenderBattleMap(battle, player);

        renderer.render();

        for (int i = 0; i < 100; i++) {
            var activeSubject = battle.getTurnStatus().getFirst();
            var ai = getBattleAI(battleAIs, activeSubject);
            ai.executeTurn(activeSubject);
            renderer.render();

            sleep();
            battle.getTurnStatus().next();
        }

        System.exit(0);
    }

    protected BattleAI getBattleAI(List<BattleAI> battleAIs, Subject active) {
        return battleAIs.stream().filter(e -> e.controls(active)).findFirst().orElseThrow();
    }

    List<BattleAction> actions(BattleAction... actions) {
        return Arrays.asList(actions);
    }

    <E extends Subject> E character(E character, Team team, String name, int hp, int atk, int def,
                                    List<BattleAction> actions) {
        character.setId(UUID.randomUUID());
        character.setName(name);
        character.setMaxHp(hp);
        character.setStats(new Stats(hp, atk, def));
        character.setTeam(team);
        character.setActions(actions);
        return character;
    }

    Monster monster(Team team, Trainer trainer, String name, int hp, int atk, int def) {
        var monster = character(new Monster(), team, name, hp, atk, def, actions(move(3), simpleAttack()));
        monster.setTrainer(trainer);
        return monster;
    }

    BattleAction move(int space) {
        return new Move(space);
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

    BattleAction simpleAttack() {
        return new SimpleAttack();
    }

    void sleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    Team team(User owner) {
        var team = new Team();
        team.setId(UUID.randomUUID());
        team.setOwner(owner);
        return team;
    }

    Trainer trainer(Team team, String name, int hp, int atk, int def) {
        var trainer = character(new Trainer(), team, name, hp, atk, def, actions(move(5)));
        return trainer;
    }
}
