package com.exam.tournament.service;

import com.exam.tournament.model.*;
import com.exam.tournament.model.container.GameInfoContainer;
import com.exam.tournament.model.personal.PersonalResult;
import com.exam.tournament.providers.PersonalResultServiceProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class TournamentService {
    private final PersonalResultServiceProvider factory;
    private final FilesService filesService;
    private final TempService tempService;

    public void processTournament() {
        Set<File> files = filesService.getFileNames();
        Set<GameInfoContainer> gameInfoContainers = files.stream()
                .map(filesService::transformCSV)
                .collect(Collectors.toSet());
        Set<Game> games = gameInfoContainers.stream()
                .map(tempService::createGame)
                .collect(Collectors.toSet());
        Tournament t = new Tournament(games);
        Player mvp = determineMVP(t);
        log.info("MVP v2 IS " + mvp.nickName());
    }

    public Player determineMVP(Tournament tournament) {
        Set<MVPRecord> mvpRecords = new HashSet<>();

        for (Game game : tournament.games()) {
            PersonalResultService personalResultService = factory.getPersonalResultService(game.getType());
            for (Team team : game.getTeams()) {
                for (PersonalResult pr : team.getPersonalResults()) {
                    mvpRecords.add(new MVPRecord(pr.getPlayer(), personalResultService.calculateMVPPoints(game, team, pr), game));
                }
            }
        }

        Map<Player, Integer> myMap = mvpRecords.stream()
                .collect(Collectors.groupingBy(MVPRecord::player,
                        Collectors.summingInt(MVPRecord::points)));
        return Collections.max(myMap.entrySet(), Map.Entry.comparingByValue()).getKey();
    }
}
