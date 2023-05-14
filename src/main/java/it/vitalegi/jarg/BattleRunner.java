package it.vitalegi.jarg;

import it.vitalegi.jarg.ai.BattleAI;
import it.vitalegi.jarg.ai.BattleAiFactory;
import it.vitalegi.jarg.battle.service.BattleBuilderService;
import it.vitalegi.jarg.battle.service.BattleService;
import it.vitalegi.jarg.being.model.Subject;
import it.vitalegi.jarg.player.service.PlayerService;
import it.vitalegi.jarg.render.RenderBattleMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Profile("run")
@Slf4j
@Component
public class BattleRunner implements CommandLineRunner {

    @Autowired
    BattleService battleService;

    @Autowired
    PlayerService playerService;
    @Autowired
    BattleBuilderService battleBuilderService;

    @Autowired
    BattleAiFactory battleAiFactory;

    @Override
    public void run(String... args) throws Exception {
        var player = playerService.player();
        var id = battleBuilderService.createBattle(player);
        var battle = battleService.getBattle(id);

        RenderBattleMap renderer = new RenderBattleMap(battle, player);

        renderer.render();
        var battleAIs = new ArrayList<BattleAI>();
        battle.getTurnStatus().getTurns().stream().map(Subject::getTeam).distinct()
              .forEach(team -> battleAIs.add(battleAiFactory.berserker(id, team)));

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


    void sleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
