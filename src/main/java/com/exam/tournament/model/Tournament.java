package com.exam.tournament.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Tournament {
    Set<Game> games = new HashSet<>();
    Player mvp;
}