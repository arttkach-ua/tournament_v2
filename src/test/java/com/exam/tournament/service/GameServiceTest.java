package com.exam.tournament.service;

import com.exam.tournament.dataProviders.FileDataProvider;
import com.exam.tournament.dataProviders.GameDataProvider;
import com.exam.tournament.exceptions.TournamentProcessingException;
import com.exam.tournament.model.Game;
import com.exam.tournament.model.GameType;
import com.exam.tournament.model.Team;
import com.exam.tournament.providers.PersonalResultServiceProvider;
import com.exam.tournament.service.impl.player_info_container_service.player_info_container_service.BasketBallPlayerInfoContainerService;
import com.exam.tournament.util.messages.ErrorMessages;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class GameServiceTest {
    @Autowired
    private GameService gameService;
    @Autowired
    private FileDataProvider fileDataProvider;
    @Autowired
    private GameDataProvider gameDataProvider;
    @Autowired
    private PersonalResultServiceProvider factory;
    @Autowired
    private BasketBallPlayerInfoContainerService basketBallPlayerInfoContainerService;

    @Test
    void getGameTypeByNameHandBallTest() {
        GameType result = gameService.getGameTypeByName("HANDBALL");
        assertThat(result)
                .isEqualTo(GameType.HANDBALL);

        GameType result1 = gameService.getGameTypeByName("handball");
        assertThat(result1)
                .isEqualTo(GameType.HANDBALL);
    }

    @Test
    void getGameTypeByNameBasketBallTest() {
        GameType result = gameService.getGameTypeByName("BASKETBALL");
        assertThat(result)
                .isEqualTo(GameType.BASKETBALL);

        GameType result1 = gameService.getGameTypeByName("basketball");
        assertThat(result1)
                .isEqualTo(GameType.BASKETBALL);

        assertThat(gameService.getGameTypeByName("basKETball"))
                .isEqualTo(GameType.BASKETBALL);
    }

    @Test
    void getGameTypeByNameNULLTest() {
        assertThatThrownBy(() -> gameService.getGameTypeByName(null)).
                isInstanceOf(TournamentProcessingException.class)
                .hasMessage(ErrorMessages.NULL_GAME_NAME);
    }

    @Test
    void getGameTypeByNameNotSupportedGameTest() {
        String game = "football";
        String expectedErrorMessage = "Game football is not supported. Please contact your administrator";
        assertThatThrownBy(() -> gameService.getGameTypeByName(game)).
                isInstanceOf(TournamentProcessingException.class)
                .hasMessage(expectedErrorMessage);
    }

    @Test
    void getTeamScoresTest() {
        Team team1 = Team.builder().name("Team A").build();
        Team team2 = Team.builder().name("Team B").build();
        Game game = Game.builder()
                .teams(Set.of(team1, team2))
                .build();

        Map<Team, Integer> result = gameService.getTeamScores(basketBallPlayerInfoContainerService, Set.of(team1, team2), gameDataProvider.getBasketBallInfoContainers());
        assertThat(result)
                .containsEntry(team1, 25)
                .containsEntry(team2, 32);
    }
}