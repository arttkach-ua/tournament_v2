package com.exam.tournament.service.aaaa;

import com.exam.tournament.model.GameType;
import com.exam.tournament.model.container.player.BasketballPlayerInfoContainer;
import com.exam.tournament.service.PlayerInfoContainerService;
import org.springframework.stereotype.Service;

import static java.lang.Integer.parseInt;
@Service
public class BasketBallPlayerInfoContainerService implements PlayerInfoContainerService<BasketballPlayerInfoContainer> {
    @Override
    public Integer getScores(BasketballPlayerInfoContainer container) {
        return parseInt(container.getScored());
    }

    @Override
    public GameType getGameType() {
        return GameType.BASKETBALL;
    }
}
