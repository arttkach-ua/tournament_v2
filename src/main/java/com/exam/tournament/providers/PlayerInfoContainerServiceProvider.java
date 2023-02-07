package com.exam.tournament.providers;

import com.exam.tournament.exceptions.TournamentProcessingException;
import com.exam.tournament.model.GameType;
import com.exam.tournament.model.container.player.PlayerInfoContainer;
import com.exam.tournament.service.PlayerInfoContainerService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.exam.tournament.util.messages.ErrorMessages.GAME_NOT_SUPPORTED;
import static java.util.function.Function.identity;

@Component
@RequiredArgsConstructor
public class PlayerInfoContainerServiceProvider {
    private final Set<PlayerInfoContainerService<?>> serviceSet;

    private Map<GameType, PlayerInfoContainerService<?>> services;

    @PostConstruct
    private void initMapperMap() {
        services = serviceSet.stream()
                .collect(Collectors.toMap(PlayerInfoContainerService::getGameType, identity()));
    }

    public PlayerInfoContainerService<? extends PlayerInfoContainer> getService(GameType gameName) {
        return Optional
                .ofNullable(services.get(gameName))
                .orElseThrow(() -> new TournamentProcessingException(String.format(GAME_NOT_SUPPORTED, gameName)));
    }
}
