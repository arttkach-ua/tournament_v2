package com.exam.tournament.providers;

import com.exam.tournament.exceptions.TournamentProcessingException;
import com.exam.tournament.mappers.PlayerInfoContainerMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.exam.tournament.util.messages.ErrorMessages.GAME_NOT_SUPPORTED;
import static java.util.function.Function.identity;

@Component
@RequiredArgsConstructor
public class MapperProvider {
    private final List<PlayerInfoContainerMapper> availableMappers;

    private Map<String, PlayerInfoContainerMapper> mapperMap;

    @PostConstruct
    private void initMapperMap() {
        mapperMap = availableMappers.stream()
                .collect(Collectors.toMap(PlayerInfoContainerMapper::getGame,
                        identity()));
    }

    public PlayerInfoContainerMapper getMapper(String gameName) {
        return Optional
                .ofNullable(mapperMap.get(gameName))
                .orElseThrow(() -> new TournamentProcessingException(GAME_NOT_SUPPORTED));
    }
}
