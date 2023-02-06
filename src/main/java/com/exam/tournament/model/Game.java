package com.exam.tournament.model;

import lombok.Builder;
import lombok.Value;

import java.util.Set;

@Value
@Builder
public class Game {
    GameType type;
    Set<Team> teams;
    Team winner;
}
