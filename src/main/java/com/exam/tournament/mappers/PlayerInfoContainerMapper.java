package com.exam.tournament.mappers;

import com.exam.tournament.model.container.player.PlayerInfoContainer;

public interface PlayerInfoContainerMapper {
    <T extends PlayerInfoContainer> T toContainer(String[] values);

    String getGame();
}
