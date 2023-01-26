package com.exam.tournament.service.impl;

import com.exam.tournament.dataProviders.GameDataProvider;
import com.exam.tournament.model.Game;
import com.exam.tournament.model.GameTypes;
import com.exam.tournament.model.Player;
import com.exam.tournament.model.Team;
import com.exam.tournament.model.personal.HandBallPersonalResult;
import com.exam.tournament.model.personal.PersonalResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class HandballPersonalResultServiceTest {
    @Autowired
    private HandballPersonalResultService personalResultService;
    @Autowired
    private GameDataProvider gameDataProvider;

    @Test
    void createPersonalResult() {
        PersonalResult pr = personalResultService.createPersonalResult(gameDataProvider.getHandBallDataAsFromFile().get(1));
        assertThat(pr)
                .hasFieldOrPropertyWithValue("goalsMade",0)
                .hasFieldOrPropertyWithValue("goalsReceived",20);
        assertThat(pr.getPlayer())
                .hasFieldOrPropertyWithValue("nickName","nick1");
    }

    @Test
    void calculateMVPPointsTest() {

        Player player = new Player("Some player");
        HandBallPersonalResult pr = HandBallPersonalResult.builder()
                .player(player)
                .goalsMade(20)
                .goalsReceived(10)
                .build();
        Team team1 = Team.builder()
                .name("LA")
                .personalResults(Set.of(pr))
                .build();
        Team team2 = Team.builder()
                .name("Fil")
                .personalResults(new HashSet<>())
                .build();
        Game game = new Game();
        game.setTeams(Set.of(team1,team2));
        game.setType(GameTypes.HANDBALL);
        game.setWinner(team1);
        Integer points = personalResultService.calculateMVPPoints(game, team1, pr);
        assertThat(points).
                isEqualTo(40);
    }
}