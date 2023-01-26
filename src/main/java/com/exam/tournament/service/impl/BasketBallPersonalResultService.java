package com.exam.tournament.service.impl;

import com.exam.tournament.model.Game;
import com.exam.tournament.model.Player;
import com.exam.tournament.model.Team;
import com.exam.tournament.model.personal.BasketBallPersonalResult;
import com.exam.tournament.model.personal.PersonalResult;
import com.exam.tournament.service.GameService;
import com.exam.tournament.service.PersonalResultService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Log4j2
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BasketBallPersonalResultService implements PersonalResultService {
    GameService gameService;
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
    public Integer calculateMVPPoints(Game game, Team team, PersonalResult personalResult) {
        int pointScoredMultiplier = 2;
        int reboundMultiplier = 1;
        int assistMultiplier = 1;
        BasketBallPersonalResult br = ((BasketBallPersonalResult)personalResult);
        return pointScoredMultiplier*br.getPointsScored() +
                reboundMultiplier*br.getRebounds() +
                assistMultiplier*br.getAssists() +
                gameService.getWinnerBonus(game,team);
    }
}
