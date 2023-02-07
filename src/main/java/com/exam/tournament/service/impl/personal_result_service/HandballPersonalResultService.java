package com.exam.tournament.service.impl.personal_result_service;

import com.exam.tournament.model.Game;
import com.exam.tournament.model.GameType;
import com.exam.tournament.model.Player;
import com.exam.tournament.model.Team;
import com.exam.tournament.model.container.player.HandballPlayerInfoContainer;
import com.exam.tournament.model.personal.HandBallPersonalResult;
import com.exam.tournament.model.personal.PersonalResult;
import com.exam.tournament.service.GameService;
import com.exam.tournament.service.PersonalResultService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.Integer.parseInt;

@Service
@Log4j2
@RequiredArgsConstructor
public class HandballPersonalResultService implements PersonalResultService<HandBallPersonalResult, HandballPlayerInfoContainer> {

    @Override
    public PersonalResult createPersonalResult(List<String> playerInfo) {
        return HandBallPersonalResult.builder()
                .player(new Player(playerInfo.get(1)))
                .goalsMade(Integer.parseInt(playerInfo.get(4)))
                .goalsReceived(Integer.parseInt(playerInfo.get(5)))
                .build();
    }

    @Override
    public Integer calculateMVPPoints(Game game, Team team, HandBallPersonalResult personalResult, GameService gameService) {
        int goalsMadeMultiplier = 2;
        int goalsReceivedMultiplier = -1;

        int points = goalsMadeMultiplier * personalResult.getGoalsMade() +
                goalsReceivedMultiplier * personalResult.getGoalsReceived() +
                gameService.getWinnerBonus(game, team);
        return Math.max(points, 0);
    }

    @Override
    public HandBallPersonalResult createPersonalResult(HandballPlayerInfoContainer container) {
        return HandBallPersonalResult.builder()
                .player(new Player(container.getNickName()))
                .goalsMade(parseInt(container.getGoalsMade()))
                .goalsReceived(parseInt(container.getGoalsReceived()))
                .build();
    }

    @Override
    public GameType getGameType() {
        return GameType.HANDBALL;
    }
}
