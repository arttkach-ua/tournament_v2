package com.exam.tournament.model.container;

import com.exam.tournament.model.container.player.PlayerInfoContainer;
import lombok.Value;

import java.util.Set;

@Value
public class GameInfoContainer {
    String gameType;
    Set<PlayerInfoContainer> playersInfo;
}
