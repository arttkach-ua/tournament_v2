package com.exam.tournament.service.impl;

import com.exam.tournament.dataProviders.GameDataProvider;
import com.exam.tournament.model.Game;
import com.exam.tournament.model.GameType;
import com.exam.tournament.model.Player;
import com.exam.tournament.model.Team;
import com.exam.tournament.model.personal.BasketBallPersonalResult;
import com.exam.tournament.model.personal.PersonalResult;
import com.exam.tournament.service.GameService;
import com.exam.tournament.service.impl.personal_result_service.BasketBallPersonalResultService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RequiredArgsConstructor
class BasketBallPersonalResultServiceTest {
    @Autowired
    private BasketBallPersonalResultService personalResultService;
    @Autowired
    private GameDataProvider gameDataProvider;
    @Autowired
    private GameService gameService;

    @Test
    void createPersonalResultTest() {
        PersonalResult pr = personalResultService.createPersonalResult(gameDataProvider.getBasketBallDataAsFromFile().get(1));
        assertThat(pr)
                .hasFieldOrPropertyWithValue("pointsScored", 10)
                .hasFieldOrPropertyWithValue("rebounds", 2)
                .hasFieldOrPropertyWithValue("assists", 7);
        assertThat(pr.getPlayer())
                .hasFieldOrPropertyWithValue("nickName", "nick1");
    }

    @Test
    void calculateMVPPointsTest() {

        Player player = new Player("Black mamba");
        BasketBallPersonalResult pr = BasketBallPersonalResult.builder()
                .player(player)
                .pointsScored(80)
                .rebounds(10)
                .assists(5)
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
                .type(GameType.BASKETBALL)
                .winner(team1)
                .build();
        Integer points = personalResultService.calculateMVPPoints(game, team1, pr, gameService);
        assertThat(points).
                isEqualTo(185);
    }
}