package com.exam.tournament.service;

import com.exam.tournament.model.Game;
import com.exam.tournament.model.GameType;
import com.exam.tournament.model.Team;
import com.exam.tournament.model.container.GameInfoContainer;
import com.exam.tournament.model.container.player.PlayerInfoContainer;
import com.exam.tournament.model.personal.PersonalResult;
import com.exam.tournament.providers.PersonalResultServiceProvider;
import com.exam.tournament.providers.PlayerInfoContainerServiceProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toSet;

@Service
@RequiredArgsConstructor
public class TempService {
    private final GameService gameService;
    private final TeamService teamService;
    private final PlayerInfoContainerServiceProvider playerInfoContainerServiceProvider;
    private final PersonalResultServiceProvider factory;

    public Game createGame(GameInfoContainer gameInfoContainer) {

        GameType type = gameService.getGameTypeByName(gameInfoContainer.getGameType());
        Set<Team> teams = createTeams(type, gameInfoContainer.getPlayersInfo());
        return Game.builder()
                .type(type)
                .teams(teams)
                .winner(getWinner(teams, type, gameInfoContainer.getPlayersInfo()))
                .build();
    }

    private Set<Team> createTeams(final GameType gameType, final Set<PlayerInfoContainer> containers) {
        PersonalResultService service = factory.getPersonalResultService(gameType);

        Map<String, Set<PersonalResult>> teamsInfo = containers.stream()
                .collect(Collectors.groupingBy(PlayerInfoContainer::getTeam,
                        mapping(service::createPersonalResult
                                , toSet())));

        return teamsInfo.entrySet()
                .stream()
                .map(entry -> teamService.createTeam(entry.getKey(), entry.getValue()))
                .collect(toSet());
    }

    private Team getWinner(final Set<Team> teams,final GameType gameType,final Set<PlayerInfoContainer> containers) {
        PlayerInfoContainerService service = playerInfoContainerServiceProvider.getService(gameType);
        Map<Team, Integer> teamScores = containers.stream()
                .collect(Collectors.groupingBy(d -> teamService.getExistingTeam(teams, d.getTeam()),
                        Collectors.summingInt(d -> service.getScores(d))));
        return Collections.max(teamScores.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

}
