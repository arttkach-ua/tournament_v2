package com.exam.tournament.service;

import com.exam.tournament.dataProviders.FileDataProvider;
import com.exam.tournament.dataProviders.GameDataProvider;
import com.exam.tournament.exceptions.TournamentProcessingException;
import com.exam.tournament.model.Game;
import com.exam.tournament.model.GameTypes;
import com.exam.tournament.model.Team;
import com.exam.tournament.model.Tournament;
import com.exam.tournament.util.messages.ErrorMessages;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
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
    private PersonalResultServiceFactory factory;

    @Test
    void getGameTypeByNameHandBallTest() {
        GameTypes result = gameService.getGameTypeByName("HANDBALL");
        assertThat(result)
                .isEqualTo(GameTypes.HANDBALL);

        GameTypes result1 = gameService.getGameTypeByName("handball");
        assertThat(result1)
                .isEqualTo(GameTypes.HANDBALL);
    }

    @Test
    void getGameTypeByNameBasketBallTest() {
        GameTypes result = gameService.getGameTypeByName("BASKETBALL");
        assertThat(result)
                .isEqualTo(GameTypes.BASKETBALL);

        GameTypes result1 = gameService.getGameTypeByName("basketball");
        assertThat(result1)
                .isEqualTo(GameTypes.BASKETBALL);

        assertThat(gameService.getGameTypeByName("basKETball"))
                .isEqualTo(GameTypes.BASKETBALL);
    }

    @Test
    void getGameTypeByNameNULLTest() {
        assertThatThrownBy(()->gameService.getGameTypeByName(null)).
                isInstanceOf(TournamentProcessingException.class)
                .hasMessage(ErrorMessages.NULL_GAME_NAME);
    }
    @Test
    void getGameTypeByNameNotSupportedGameTest() {
        String game = "football";
        String expectedErrorMessage = "Game football is not supported. Please contact your administrator";
        assertThatThrownBy(()->gameService.getGameTypeByName(game)).
                isInstanceOf(TournamentProcessingException.class)
                .hasMessage(expectedErrorMessage);
    }

    @Test
    void getTeamScoresTest() {
        Team team1 = Team.builder().name("Team A").build();
        Team team2 = Team.builder().name("Team B").build();
        Game game = new Game();
        game.setTeams(Set.of(team1,team2));

        Map<Team, Integer> result = gameService.getTeamScores(game, gameDataProvider.getBasketBallDataAsFromFile());
        assertThat(result)
                .containsEntry(team1,25)
                .containsEntry(team2,32);
    }


    @Test
    void setGameTypeNormalCaseTest() {
        Game game = new Game();
        List<String> testData = List.of("BASKETBALL");
        gameService.setGameType(game, testData);
        assertThat(game).
                hasFieldOrPropertyWithValue("type", GameTypes.BASKETBALL);
    }
    @Test
    void setGameTypeToBigListCaseTest() {
        Game game = new Game();
        List<String> testData = List.of("BASKETBALL", "Some wrong data");
        String expectedErrorMessage = "Wrong file data";
        assertThatThrownBy(()->gameService.setGameType(game, testData))
                .isInstanceOf(TournamentProcessingException.class)
                .hasMessage(expectedErrorMessage);
    }
    @Test
    void setGameTypeWrongTextCaseTest() {
        String expectedErrorMessage = "Game BASSSKETBALL is not supported. Please contact your administrator";
                Game game = new Game();
        List<String> testData = List.of("BASSSKETBALL");
        assertThatThrownBy(()->gameService.setGameType(game, testData))
                .isInstanceOf(TournamentProcessingException.class)
                .hasMessage(expectedErrorMessage);
    }

    @Test
    void addGameToTournamentTest(){
        Tournament tournament = new Tournament();
        gameService.addGameToTournament(fileDataProvider.getBasketBallTestFile(), tournament,factory);
        assertThat(tournament.getGames())
                .size().isEqualTo(1);
    }
}