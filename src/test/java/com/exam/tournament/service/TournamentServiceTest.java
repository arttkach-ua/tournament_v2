package com.exam.tournament.service;

import com.exam.tournament.model.Tournament;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class TournamentServiceTest {
    @Autowired
    private TournamentService tournamentService;

    @Test
    void fillTournamentBigTest() {
        Tournament tournament = new Tournament();
        tournamentService.fillTournament(tournament);
        tournamentService.determineMVP(tournament);
        assertThat(tournament.getMvp().nickName())
                .isEqualToIgnoringCase("nick4");
    }
}