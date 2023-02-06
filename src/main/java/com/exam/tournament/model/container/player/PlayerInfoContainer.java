package com.exam.tournament.model.container.player;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public abstract class PlayerInfoContainer {
    String playerName;
    String nickName;
    String number;
    String team;
}
