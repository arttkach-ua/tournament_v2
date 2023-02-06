package com.exam.tournament.exceptions;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class TournamentProcessingException extends RuntimeException {
    private static final String ERROR_MESSAGE = "Tournament processing exception was thrown. Message is: {}";

    public TournamentProcessingException() {
        log.error(ERROR_MESSAGE);
    }

    public TournamentProcessingException(String message) {
        super(message);
        log.error(ERROR_MESSAGE, message);
    }
}
