package com.exam.tournament.service.impl;

import com.exam.tournament.dataProviders.GameDataProvider;
import com.exam.tournament.model.Game;
import com.exam.tournament.model.GameType;
import com.exam.tournament.model.Player;
import com.exam.tournament.model.Team;
import com.exam.tournament.model.personal.HandBallPersonalResult;
import com.exam.tournament.model.personal.PersonalResult;
import com.exam.tournament.service.GameService;
import com.exam.tournament.service.impl.personal_result_service.HandballPersonalResultService;
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

    @Autowired
    private GameService gameService;

    @Test
    void createPersonalResult() {
        PersonalResult pr = personalResultService.createPersonalResult(gameDataProvider.getHandBallDataAsFromFile().get(1));
        assertThat(pr)
                .hasFieldOrPropertyWithValue("goalsMade", 0)
                .hasFieldOrPropertyWithValue("goalsReceived", 20);
        assertThat(pr.getPlayer())
                .hasFieldOrPropertyWithValue("nickName", "nick1");
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
        Game game = Game.builder()
                .teams(Set.of(team1, team2))
                .type(GameType.HANDBALL)
                .winner(team1)
                .build();
        Integer points = personalResultService.calculateMVPPoints(game, team1, pr, gameService);
        assertThat(points).
                isEqualTo(40);
    }
}