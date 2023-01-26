package com.exam.tournament.model;

import lombok.Data;

import java.util.Set;

@Data
public class Game {
    private GameTypes type;
    private Set<Team> teams;
    private Team winner;
}
