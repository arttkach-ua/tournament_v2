package com.exam.tournament.service;

import com.exam.tournament.exceptions.TournamentProcessingException;
import com.exam.tournament.model.Game;
import com.exam.tournament.model.GameType;
import com.exam.tournament.model.Team;
import com.exam.tournament.model.container.player.PlayerInfoContainer;
import com.exam.tournament.model.personal.PersonalResult;
import com.exam.tournament.providers.PersonalResultServiceProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.exam.tournament.util.messages.ErrorMessages.TEAM_NOT_FOUND;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toSet;

@Service
@RequiredArgsConstructor
@Log4j2
public class TeamService {
    private final PersonalResultServiceProvider factory;

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

    public Team getExistingTeam(final Set<Team> teams, final String name) {
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

    public Set<Team> createTeams(final GameType gameType, final Set<PlayerInfoContainer> containers) {
        PersonalResultService service = factory.getPersonalResultService(gameType);

        Map<String, Set<PersonalResult>> teamsInfo = containers.stream()
                .collect(Collectors.groupingBy(PlayerInfoContainer::getTeam,
                        mapping(service::createPersonalResult
                                , toSet())));

        return teamsInfo.entrySet()
                .stream()
                .map(entry -> createTeam(entry.getKey(), entry.getValue()))
                .collect(toSet());
    }


}
