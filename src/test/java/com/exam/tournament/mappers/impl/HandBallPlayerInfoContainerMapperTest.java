package com.exam.tournament.mappers.impl;

import com.exam.tournament.dataProviders.GameDataProvider;
import com.exam.tournament.exceptions.TournamentProcessingException;
import com.exam.tournament.model.container.player.HandballPlayerInfoContainer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class HandBallPlayerInfoContainerMapperTest {
    @Autowired
    private HandBallPlayerInfoContainerMapper mapper;
    @Autowired
    private GameDataProvider gameDataProvider;

    @Test
    void toContainerTest() {
        HandballPlayerInfoContainer container = mapper.toContainer(gameDataProvider.getHandBallInfoArray());
        assertThat(container)
                .hasFieldOrPropertyWithValue("goalsMade", "22")
                .hasFieldOrPropertyWithValue("goalsReceived", "33")
                .hasFieldOrPropertyWithValue("playerName", "player 2")
                .hasFieldOrPropertyWithValue("team", "Team A")
                .hasFieldOrPropertyWithValue("nickName", "nick2")
                .hasFieldOrPropertyWithValue("number", "2");
    }

    @Test
    void toContainerWrongInputTest() {
        assertThatThrownBy(() -> mapper.toContainer(new String[8])).
                isInstanceOf(TournamentProcessingException.class)
                .hasMessage("Container length is not valid");
    }

    @Test
    void toContainerNullInputTest() {
        assertThatThrownBy(() -> mapper.toContainer(null)).
                isInstanceOf(NullPointerException.class)
                .hasMessage("rowValues is marked non-null but is null");
    }
}