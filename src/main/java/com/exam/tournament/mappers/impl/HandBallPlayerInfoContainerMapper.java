package com.exam.tournament.mappers.impl;

import com.exam.tournament.exceptions.TournamentProcessingException;
import com.exam.tournament.mappers.PlayerInfoContainerMapper;
import com.exam.tournament.model.container.player.HandballPlayerInfoContainer;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import static com.exam.tournament.util.messages.ErrorMessages.CONTAINER_LENGTH_NOT_VALID;

@Component
public class HandBallPlayerInfoContainerMapper implements PlayerInfoContainerMapper {
    private static final int PLAYER_NAME_POSITION = 0;
    private static final int NICK_NAME_POSITION = 1;
    private static final int NUMBER_POSITION = 2;
    private static final int TEAM_POSITION = 3;
    private static final int GOALS_MADE_POSITION = 4;
    private static final int GOALS_RECEIVED_POSITION = 5;

    @Override
    public HandballPlayerInfoContainer toContainer(String[] rowValues) {
        validateRowValues(rowValues);

        return HandballPlayerInfoContainer.builder()
                .playerName(rowValues[PLAYER_NAME_POSITION])
                .nickName(rowValues[NICK_NAME_POSITION])
                .number(rowValues[NUMBER_POSITION])
                .team(rowValues[TEAM_POSITION])
                .goalsMade(rowValues[GOALS_MADE_POSITION])
                .goalsReceived(rowValues[GOALS_RECEIVED_POSITION])
                .build();
    }

    private void validateRowValues(@NonNull final String[] rowValues) {
        if (rowValues.length != 6) {
            throw new TournamentProcessingException(CONTAINER_LENGTH_NOT_VALID);
        }
    }

    @Override
    public String getGame() {
        return "HANDBALL";
    }
}
