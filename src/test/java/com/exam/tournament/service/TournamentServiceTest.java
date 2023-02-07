package com.exam.tournament.service;

import com.exam.tournament.model.Game;
import com.exam.tournament.model.Player;
import com.exam.tournament.model.Tournament;
import com.exam.tournament.model.container.GameInfoContainer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.File;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class TournamentServiceTest {
    @Autowired
    FilesService filesService;
    @Autowired
    GameService gameService;
    @Autowired
    private TournamentService tournamentService;

    @Test
    void fillTournamentBigTest() {

        Set<File> files = filesService.getFileNames();
        Set<GameInfoContainer> gameInfoContainers = files.stream()
                .map(filesService::transformCSV)
                .collect(Collectors.toSet());
        Set<Game> games = gameInfoContainers.stream()
                .map(gameService::createGame)
                .collect(Collectors.toSet());
        Tournament t = new Tournament(games);
        Player mvp = tournamentService.determineMVP(t);

        assertThat(mvp.nickName())
                .isEqualToIgnoringCase("nick4");
    }
}