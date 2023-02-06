package com.exam.tournament.service.aaaa;

import com.exam.tournament.model.GameType;
import com.exam.tournament.model.container.player.HandballPlayerInfoContainer;
import com.exam.tournament.service.PlayerInfoContainerService;
import org.springframework.stereotype.Service;

import static java.lang.Integer.parseInt;
@Service
public class HandballPlayerInfoContainerService implements PlayerInfoContainerService<HandballPlayerInfoContainer> {
    @Override
    public Integer getScores(HandballPlayerInfoContainer container) {
        return parseInt(container.getGoalsMade());
    }

    @Override
    public GameType getGameType() {
        return GameType.HANDBALL;
    }
}
