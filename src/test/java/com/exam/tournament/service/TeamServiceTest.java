package com.exam.tournament.service;

import com.exam.tournament.exceptions.TournamentProcessingException;
import com.exam.tournament.model.Game;
import com.exam.tournament.model.Team;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class TeamServiceTest {

    @Autowired
    private TeamService teamService;

    @Test
    void createTeamTest() {
        Team result = teamService.createTeam("test team");
        assertThat(result.getName())
                .isEqualToIgnoringCase("test team");
    }

    @Test
    void getTeamFromGameTeamExistsTest() {
        Game game = new Game();
        Team team1 = Team.builder().name("test team 1").build();
        Team team2 = Team.builder().name("test team 2").build();
        game.setTeams(Set.of(team1,team2));
        Team resultTeam = teamService.getTeamFromGame(game, "test team 1");
        assertThat(resultTeam)
                .isEqualTo(team1);
        assertThat(resultTeam.getName())
                .isEqualTo("test team 1");
    }

    @Test
    void getTeamFromGameTeamDoesNotExistTest() {
        Game game = new Game();
        Team team1 = Team.builder().name("test team 1").build();
        Team team2 = Team.builder().name("test team 2").build();
        game.setTeams(Set.of(team1,team2));
        assertThatThrownBy(()->teamService.getTeamFromGame(game, "test team 3")).
            isInstanceOf(TournamentProcessingException.class)
                .hasMessage("Team test team 3 not found");
    }
}