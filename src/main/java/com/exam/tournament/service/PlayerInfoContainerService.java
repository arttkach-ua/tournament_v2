package com.exam.tournament.service;

import com.exam.tournament.model.GameType;
import com.exam.tournament.model.container.player.PlayerInfoContainer;

public interface PlayerInfoContainerService<T extends PlayerInfoContainer> {
    Integer getScores(T container);

    GameType getGameType();
}
