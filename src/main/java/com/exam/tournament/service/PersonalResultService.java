package com.exam.tournament.service;

import com.exam.tournament.model.Game;
import com.exam.tournament.model.Team;
import com.exam.tournament.model.personal.PersonalResult;

import java.util.List;

public interface PersonalResultService {
    PersonalResult createPersonalResult(List<String> playerInfo);
    Integer calculateMVPPoints(Game game, Team team, PersonalResult personalResult);
}
