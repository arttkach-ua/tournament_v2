package com.exam.tournament;

import com.exam.tournament.service.TournamentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Log4j2
public class TournamentApplication implements CommandLineRunner {
    TournamentService tournamentService;

    public static void main(String[] args) {
        SpringApplication.run(TournamentApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        tournamentService.processTournament();
    }
}
