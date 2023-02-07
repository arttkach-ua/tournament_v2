package com.exam.tournament.service;

import com.exam.tournament.exceptions.TournamentProcessingException;
import com.exam.tournament.model.Game;
import com.exam.tournament.model.GameType;
import com.exam.tournament.model.Team;
import com.exam.tournament.model.container.GameInfoContainer;
import com.exam.tournament.model.container.player.PlayerInfoContainer;
import com.exam.tournament.providers.PlayerInfoContainerServiceProvider;
import com.exam.tournament.util.messages.ErrorMessages;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class GameService {
    private static final Map<String, GameType> SUPPORTED_GAME_TYPES = Arrays.stream(GameType.values())
            .collect(Collectors.toMap(GameType::name,
                    Function.identity()));
    private final TeamService teamService;
    private final PlayerInfoContainerServiceProvider playerInfoContainerServiceProvider;

    public GameType getGameTypeByName(String name) {
        log.info("Game name is {}", name);
        if (name == null) {
            throw new TournamentProcessingException(ErrorMessages.NULL_GAME_NAME);
        }
        return Optional
                .ofNullable(SUPPORTED_GAME_TYPES.get(name.toUpperCase()))
                .orElseThrow(() -> constructTournamentProcessingException(name));
    }

    private TournamentProcessingException constructTournamentProcessingException(String name) {
        return new TournamentProcessingException(String.format(ErrorMessages.GAME_NOT_SUPPORTED, name));
    }

    public int getWinnerBonus(Game game, Team team) {
        int bonus = 0;
        if (team.equals(game.getWinner())) {
            bonus = 10;
        }
        return bonus;
    }

    public Game createGame(GameInfoContainer gameInfoContainer) {

        GameType type = getGameTypeByName(gameInfoContainer.getGameType());
        Set<Team> teams = teamService.createTeams(type, gameInfoContainer.getPlayersInfo());
        return Game.builder()
                .type(type)
                .teams(teams)
                .winner(getWinner(teams, type, gameInfoContainer.getPlayersInfo()))
                .build();
    }

    private Team getWinner(final Set<Team> teams, final GameType gameType, final Set<PlayerInfoContainer> containers) {
        PlayerInfoContainerService service = playerInfoContainerServiceProvider.getService(gameType);
        Map<Team, Integer> teamScores = getTeamScores(service, teams, containers);
        return Collections.max(teamScores.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    public Map<Team, Integer> getTeamScores(final PlayerInfoContainerService service, final Set<Team> teams, final Set<PlayerInfoContainer> containers) {
        return containers.stream()
                .collect(Collectors.groupingBy(d -> teamService.getExistingTeam(teams, d.getTeam()),
                        Collectors.summingInt(d -> service.getScores(d))));
    }
}
