package com.exam.tournament.service;

import com.exam.tournament.exceptions.TournamentProcessingException;
import com.exam.tournament.model.Game;
import com.exam.tournament.model.Team;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import static com.exam.tournament.util.messages.ErrorMessages.TEAM_NOT_FOUND;

@Service
@Log4j2
public class TeamService {
    /**
     * Creates team entity
     * @param teamName
     * @return new team entity
     */
    public Team createTeam(String teamName){
        return Team.builder()
                .name(teamName)
                .build();
    }

    /**
     * Gets team entity from game by team name.(teams were created before)
     * @param game
     * @param name
     * @return
     */
    public Team getTeamFromGame(Game game, String name){
        return game.getTeams().stream()
                .filter(a->a.getName().equals(name))
                .findFirst()
                .orElseThrow(()->new TournamentProcessingException(String.format(TEAM_NOT_FOUND, name)));
    }

}
