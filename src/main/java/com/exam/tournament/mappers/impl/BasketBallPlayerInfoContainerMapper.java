package com.exam.tournament.mappers.impl;

import com.exam.tournament.exceptions.TournamentProcessingException;
import com.exam.tournament.mappers.PlayerInfoContainerMapper;
import com.exam.tournament.model.container.player.BasketballPlayerInfoContainer;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import static com.exam.tournament.util.messages.ErrorMessages.CONTAINER_LENGTH_NOT_VALID;

@Component
public class BasketBallPlayerInfoContainerMapper implements PlayerInfoContainerMapper {
    private static final int PLAYER_NAME_POSITION = 0;
    private static final int NICK_NAME_POSITION = 1;
    private static final int NUMBER_POSITION = 2;
    private static final int TEAM_POSITION = 3;
    private static final int SCORED_POSITION = 4;
    private static final int REBOUNDS_POSITION = 5;
    private static final int ASSISTS_POSITION = 6;

    @Override
    public BasketballPlayerInfoContainer toContainer(String[] rowValues) {
        validateRowValues(rowValues);

        return BasketballPlayerInfoContainer.builder()
                .playerName(rowValues[PLAYER_NAME_POSITION])
                .nickName(rowValues[NICK_NAME_POSITION])
                .number(rowValues[NUMBER_POSITION])
                .team(rowValues[TEAM_POSITION])
                .scored(rowValues[SCORED_POSITION])
                .rebounds(rowValues[REBOUNDS_POSITION])
                .assists(rowValues[ASSISTS_POSITION])
                .build();
    }

    private void validateRowValues(@NonNull final String[] rowValues) {
        if (rowValues.length != 7) {
            throw new TournamentProcessingException(CONTAINER_LENGTH_NOT_VALID);
        }
    }

    @Override
    public String getGame() {
        return "BASKETBALL";
    }
}
