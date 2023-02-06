package com.exam.tournament.model.container.player;

import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.SuperBuilder;

@Value
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class HandballPlayerInfoContainer extends PlayerInfoContainer {
    String goalsMade;
    String goalsReceived;
}
