package com.exam.tournament.providers;

import com.exam.tournament.exceptions.TournamentProcessingException;
import com.exam.tournament.model.GameType;
import com.exam.tournament.service.PersonalResultService;
import com.exam.tournament.util.messages.ErrorMessages;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Log4j2
public class PersonalResultServiceProvider {

    private final Map<GameType, PersonalResultService> serviceMap;

    public PersonalResultServiceProvider(List<PersonalResultService> availableServices) {
        serviceMap = availableServices.stream()
                .collect(Collectors.toMap(PersonalResultService::getGameType, Function.identity()));
    }

    public PersonalResultService getPersonalResultService(GameType gameType) {
        return Optional.ofNullable(serviceMap.get(gameType))
                .orElseThrow(() -> new TournamentProcessingException(String.format(ErrorMessages.GAME_NOT_SUPPORTED, gameType)));
    }
}
