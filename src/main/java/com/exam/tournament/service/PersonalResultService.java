package com.exam.tournament.service;

import com.exam.tournament.model.Game;
import com.exam.tournament.model.Team;
import com.exam.tournament.model.personal.PersonalResult;

import java.util.List;

public interface PersonalResultService {
    /**
     * Creates personal information. One raw in file -> one entity personal result
     * @param playerInfo
     * @return
     */
    PersonalResult createPersonalResult(List<String> playerInfo);

    /**
     * Calculates MVP points for one personal result.
     * @param game
     * @param team
     * @param personalResult
     * @return count of points
     */
    Integer calculateMVPPoints(Game game, Team team, PersonalResult personalResult);
}
