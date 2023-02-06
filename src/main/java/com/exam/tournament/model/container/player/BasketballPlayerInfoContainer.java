package com.exam.tournament.model.container.player;

import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.SuperBuilder;

@Value
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class BasketballPlayerInfoContainer extends PlayerInfoContainer {
    String scored;
    String rebounds;
    String assists;
}
