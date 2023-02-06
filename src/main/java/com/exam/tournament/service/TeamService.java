package com.exam.tournament.service;

import com.exam.tournament.exceptions.TournamentProcessingException;
import com.exam.tournament.model.Game;
import com.exam.tournament.model.Team;
import com.exam.tournament.model.personal.PersonalResult;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Set;

import static com.exam.tournament.util.messages.ErrorMessages.TEAM_NOT_FOUND;

@Service
@Log4j2
public class TeamService {
    public Team createTeam(String teamName) {
        return Team.builder()
                .name(teamName)
                .build();
    }

    public Team getTeamFromGame(Game game, String name) {
        return game.getTeams().stream()
                .filter(a -> a.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new TournamentProcessingException(String.format(TEAM_NOT_FOUND, name)));
    }

    public Team getExistingTeam(final Set<Team> teams, final String name){
        return teams.stream()
                .filter(a -> a.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new TournamentProcessingException(String.format(TEAM_NOT_FOUND, name)));
    }
    public Team createTeam(String teamName, Set<PersonalResult> personalResults) {
        return Team.builder()
                .name(teamName)
                .personalResults(personalResults)
                .build();
    }


}
