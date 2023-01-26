package com.exam.tournament.service.impl;

import com.exam.tournament.model.Game;
import com.exam.tournament.model.Player;
import com.exam.tournament.model.Team;
import com.exam.tournament.model.personal.HandBallPersonalResult;
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
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class HandballPersonalResultService implements PersonalResultService {
    GameService gameService;

    @Override
    public PersonalResult createPersonalResult(List<String> playerInfo) {
        return HandBallPersonalResult.builder()
                .player(new Player(playerInfo.get(1)))
                .goalsMade(Integer.parseInt(playerInfo.get(4)))
                .goalsReceived(Integer.parseInt(playerInfo.get(5)))
                .build();
    }

    @Override
    public Integer calculateMVPPoints(Game game, Team team, PersonalResult personalResult){
        int goalsMadeMultiplier = 2;
        int goalsReceivedMultiplier = -1;

        HandBallPersonalResult handBallPersonalResult = (HandBallPersonalResult) personalResult;

        int points = goalsMadeMultiplier* handBallPersonalResult.getGoalsMade() +
                goalsReceivedMultiplier* handBallPersonalResult.getGoalsReceived() +
                gameService.getWinnerBonus(game,team);
        return Math.max(points,0);
    }
}
