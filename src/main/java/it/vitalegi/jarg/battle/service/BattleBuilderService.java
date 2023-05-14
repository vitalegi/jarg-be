package it.vitalegi.jarg.battle.service;

import it.vitalegi.jarg.battle.model.Battle;
import it.vitalegi.jarg.battle.model.TurnStatus;
import it.vitalegi.jarg.battleaction.model.BattleAction;
import it.vitalegi.jarg.battleaction.model.Move;
import it.vitalegi.jarg.battleaction.model.SimpleAttack;
import it.vitalegi.jarg.being.model.MapPlacement;
import it.vitalegi.jarg.being.model.Monster;
import it.vitalegi.jarg.being.model.Stats;
import it.vitalegi.jarg.being.model.Subject;
import it.vitalegi.jarg.being.model.Team;
import it.vitalegi.jarg.being.model.Trainer;
import it.vitalegi.jarg.map.model.MapBuilder;
import it.vitalegi.jarg.metrics.Performance;
import it.vitalegi.jarg.metrics.Type;
import it.vitalegi.jarg.player.user.User;
import it.vitalegi.jarg.player.user.UserType;
import it.vitalegi.jarg.tile.model.TileBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@Performance(Type.SERVICE)
public class BattleBuilderService {

    @Autowired
    BattleService battleService;

    public UUID createBattle(User player) {
        var battle = new Battle();
        battle.setEvents(new ArrayList<>());
        battle.setTurnStatus(new TurnStatus());
        battle.setMapPlacement(new MapPlacement());
        battle.setMap( //
                new MapBuilder() //
                                 .tiles(0, 20, 0, 10, TileBuilder.grass()) //
                                 .tiles(4, 5, 0, 3, TileBuilder.obstacle()) //
                                 .tiles(4, 5, 6, 9, TileBuilder.obstacle()) //
                                 .build());

        var id = battleService.addBattle(battle);

        var team1 = team(player);
        var trainer1 = trainer(team1, "Trainer", 10, 1, 1);
        battleService.addSubject(id, 0, 0, trainer1);
        battleService.addSubject(id, 0, 1, monster(team1, trainer1, "Abcdefghilj", 20, 5, 3));
        battleService.addSubject(id, 3, 2, monster(team1, trainer1, "B", 20, 5, 3));
        battleService.addSubject(id, 0, 2, monster(team1, trainer1, "C", 20, 5, 3));

        var npc = npc();
        var team2 = team(npc);
        battleService.addSubject(id, 6, 4, monster(team2, null, "E", 20, 5, 3));
        battleService.addSubject(id, 8, 4, monster(team2, null, "F", 20, 5, 3));
        battleService.addSubject(id, 6, 6, monster(team2, null, "G", 20, 5, 3));
        return id;
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

    BattleAction simpleAttack() {
        return new SimpleAttack();
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
