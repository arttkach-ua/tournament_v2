package com.exam.tournament.service;

import com.exam.tournament.exceptions.TournamentProcessingException;
import com.exam.tournament.model.GameTypes;
import com.exam.tournament.service.impl.BasketBallPersonalResultService;
import com.exam.tournament.service.impl.HandballPersonalResultService;
import com.exam.tournament.util.messages.ErrorMessages;
import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

@Component
@Log4j2
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class PersonalResultServiceFactory {

    private final ApplicationContext context;
    private static final Map<GameTypes, PersonalResultService> serviceMap = new EnumMap<>(GameTypes.class);

    /**
     * Determines service that will process personal info data.
     * @param gameType
     * @return
     */
    public PersonalResultService getPersonalResultService(GameTypes gameType) {
        return Optional.of(serviceMap.get(gameType))
                .orElseThrow(()->new TournamentProcessingException(String.format(ErrorMessages.GAME_NOT_SUPPORTED,gameType)));
    }

    @PostConstruct
    private void initServiceMap(){
        serviceMap.put(GameTypes.HANDBALL,   context.getBean(HandballPersonalResultService.class));
        serviceMap.put(GameTypes.BASKETBALL, context.getBean(BasketBallPersonalResultService.class));
    }

}
