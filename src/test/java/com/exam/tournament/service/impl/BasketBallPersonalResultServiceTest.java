package com.exam.tournament.service.impl;

import com.exam.tournament.dataProviders.GameDataProvider;
import com.exam.tournament.model.Game;
import com.exam.tournament.model.GameTypes;
import com.exam.tournament.model.Player;
import com.exam.tournament.model.Team;
import com.exam.tournament.model.personal.BasketBallPersonalResult;
import com.exam.tournament.model.personal.PersonalResult;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RequiredArgsConstructor
class BasketBallPersonalResultServiceTest {

    @Autowired
    private BasketBallPersonalResultService personalResultService;

    @Autowired
    private GameDataProvider gameDataProvider;

    @Test
    void createPersonalResultTest() {
        PersonalResult pr = personalResultService.createPersonalResult(gameDataProvider.getBasketBallDataAsFromFile().get(1));
        assertThat(pr)
                .hasFieldOrPropertyWithValue("pointsScored",10)
                .hasFieldOrPropertyWithValue("rebounds",2)
                .hasFieldOrPropertyWithValue("assists",7);
        assertThat(pr.getPlayer())
                .hasFieldOrPropertyWithValue("nickName","nick1");
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
        Game game = new Game();
        game.setTeams(Set.of(team1,team2));
        game.setType(GameTypes.BASKETBALL);
        game.setWinner(team1);
        Integer points = personalResultService.calculateMVPPoints(game, team1, pr);
        assertThat(points).
                isEqualTo(185);
    }
}