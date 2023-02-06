package com.exam.tournament.service.impl.personal_result_service;

import com.exam.tournament.model.Game;
import com.exam.tournament.model.GameType;
import com.exam.tournament.model.Player;
import com.exam.tournament.model.Team;
import com.exam.tournament.model.container.player.BasketballPlayerInfoContainer;
import com.exam.tournament.model.personal.BasketBallPersonalResult;
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
public class BasketBallPersonalResultService implements PersonalResultService<BasketBallPersonalResult, BasketballPlayerInfoContainer> {
    private final GameService gameService;

    @Override
    public PersonalResult createPersonalResult(List<String> playerInfo) {
        return BasketBallPersonalResult.builder()
                .player(new Player(playerInfo.get(1)))
                .assists(Integer.parseInt(playerInfo.get(6)))
                .rebounds(Integer.parseInt(playerInfo.get(5)))
                .pointsScored(Integer.parseInt(playerInfo.get(4)))
                .build();
    }

    @Override
    public Integer calculateMVPPoints(Game game, Team team, BasketBallPersonalResult personalResult) {
        int pointScoredMultiplier = 2;
        int reboundMultiplier = 1;
        int assistMultiplier = 1;

        return pointScoredMultiplier * personalResult.getPointsScored() +
                reboundMultiplier * personalResult.getRebounds() +
                assistMultiplier * personalResult.getAssists() +
                gameService.getWinnerBonus(game, team);
    }

    @Override
    public GameType getGameType() {
        return GameType.BASKETBALL;
    }

    @Override
    public BasketBallPersonalResult createPersonalResult(BasketballPlayerInfoContainer container) {
        return BasketBallPersonalResult.builder()
                .player(new Player(container.getNickName()))
                .assists(parseInt(container.getAssists()))
                .pointsScored(parseInt(container.getScored()))
                .rebounds(parseInt(container.getRebounds()))
                .build();
    }

}
