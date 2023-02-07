package com.exam.tournament.service;

import com.exam.tournament.model.Game;
import com.exam.tournament.model.GameType;
import com.exam.tournament.model.Team;
import com.exam.tournament.model.container.player.PlayerInfoContainer;
import com.exam.tournament.model.personal.PersonalResult;

import java.util.List;

public interface PersonalResultService<T extends PersonalResult, S extends PlayerInfoContainer> {

    PersonalResult createPersonalResult(List<String> playerInfo);

    Integer calculateMVPPoints(Game game, Team team, T personalResult, GameService gameService);

    GameType getGameType();

    T createPersonalResult(S container);
}
