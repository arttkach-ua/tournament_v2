package com.exam.tournament;

import com.exam.tournament.service.TournamentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
@Log4j2
public class TournamentApplication implements CommandLineRunner {
    private final TournamentService tournamentService;

    public static void main(String[] args) {
        SpringApplication.run(TournamentApplication.class, args);
    }

    @Override
    public void run(String... args) {
        tournamentService.processTournament();
    }
}
