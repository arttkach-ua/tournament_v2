package com.exam.tournament.service;

import com.exam.tournament.model.*;
import com.exam.tournament.model.personal.PersonalResult;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
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
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class TournamentService {
    PersonalResultServiceFactory factory;
    FilesService filesService;
    GameService gameService;
    public void processTournament(){
        log.info("processing");
        Tournament tournament = new Tournament();
        fillTournament(tournament);
        determineMVP(tournament);
        log.info("MVP IS " + tournament.getMvp().nickName());
    }

    /**
     * In this procedure MVP player determines and sets to tournament field.
     * @param tournament
     */
    public void determineMVP(Tournament tournament) {
       Set<MVPRecord> mvpRecords = new HashSet<>();
       for (Game game:tournament.getGames()){
           PersonalResultService personalResultService = factory.getPersonalResultService(game.getType());
           for (Team team: game.getTeams()){
               for(PersonalResult pr:team.getPersonalResults()){
                   mvpRecords.add(new MVPRecord(pr.getPlayer(),personalResultService.calculateMVPPoints(game,team,pr),game));
               }
           }
        }

        Map<Player, Integer> myMap = mvpRecords.stream()
                .collect(Collectors.groupingBy(MVPRecord::getPlayer,Collectors.summingInt(MVPRecord::getPoints)));
        tournament.setMvp(Collections.max(myMap.entrySet(), Map.Entry.comparingByValue()).getKey());
    }

    /**
     * This function gets set of files and calls processing of them
     * @param tournament
     */
    public void fillTournament(Tournament tournament){
        Set<File> files = filesService.getFileNames();
        addGamesToTournament(files, tournament);
    }

    /**
     * This function calls process of reading info from files and mapping it to games.
     * @param files
     * @param tournament
     */
    public void addGamesToTournament(Set<File> files, Tournament tournament){
        files.stream()
                .forEach(file->gameService.addGameToTournament(file, tournament, factory));
    }
}
