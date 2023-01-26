package com.exam.tournament.service;

import com.exam.tournament.exceptions.TournamentProcessingException;
import com.exam.tournament.model.Game;
import com.exam.tournament.model.GameTypes;
import com.exam.tournament.model.Team;
import com.exam.tournament.model.Tournament;
import com.exam.tournament.model.personal.PersonalResult;
import com.exam.tournament.util.messages.ErrorMessages;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Log4j2
public class GameService {
    FilesService filesService;
    TeamService teamService;

    static Map<String, GameTypes> supportedGameTypes = new HashMap<>();

    static {
        initSupportedGames();
    }


    /**
     * Sets into game type of game
     * @param game - *{@link Game} - game entity
     * @param data - list with data from file. It should contain 1 element with game name
     */
    public void setGameType(Game game, List<String> data){
        if (data.size()==1){
            game.setType(getGameTypeByName(data.get(0)));
        }else {
            throw new TournamentProcessingException(ErrorMessages.WRONG_FILE_DATA);
        }
    }

    /**
     * Gets enum type of game by String with name
     * @param name - name of game
     * @return *{@link GameTypes} - type of game
     * @throws *{@link RuntimeException} if name not supported
     */
    public GameTypes getGameTypeByName(String name){
        log.info("Game name is {}",name);
        if (name==null) {
            throw new TournamentProcessingException(ErrorMessages.NULL_GAME_NAME);
        }
        return Optional
                .ofNullable(supportedGameTypes.get(name.toUpperCase()))
                .orElseThrow(()->constructTournamentProcessingException(name));
    }
    private TournamentProcessingException constructTournamentProcessingException(String name) {
        return new TournamentProcessingException(String.format(ErrorMessages.GAME_NOT_SUPPORTED, name));
    }

    public Team getGameWinner(Game game, List<List<String>> gameData){
        Map<Team, Integer> teamScores = getTeamScores(game, gameData);
        return Collections.max(teamScores.entrySet(), Map.Entry.comparingByValue()).getKey();
}


    /**
     * Calculates team scores and returns it as Map
     * @param gameData
     * @return
     */
    public Map<Team, Integer> getTeamScores(Game game, List<List<String>> gameData){
        return gameData.stream()
                .skip(1)
                .collect(Collectors.groupingBy(d->teamService.getTeamFromGame(game,d.get(3)),Collectors.summingInt(d->Integer.parseInt(d.get(4)))));
    }


    /**
     * Read data from file and add it to tournament
     * @param file
     * @param tournament
     */
    public void addGameToTournament(File file, Tournament tournament, PersonalResultServiceFactory factory){
        List<List<String>> gameData= filesService.readCSV(file);
        if (gameData.isEmpty()) throw new TournamentProcessingException();
        Game game = new Game();
        tournament.getGames().add(game);
        GameTypes gameType = getGameTypeByName(gameData.get(0).get(0));
        game.setType(gameType);
        PersonalResultService personalResultService = factory.getPersonalResultService(gameType);
        game.setTeams(createTeams(gameData));
        game.setWinner(getGameWinner(game, gameData));
        fillPersonalResults(game, gameData, personalResultService);
    }

    public void fillPersonalResults(Game game, List<List<String>> gameData, PersonalResultService personalResultService) {
        game.getTeams().stream()
                .forEach(team ->team.setPersonalResults(collectPersonalResultsForTeam(team,gameData,personalResultService)));
    }
    private Set<PersonalResult> collectPersonalResultsForTeam(Team team, List<List<String>> gameData, PersonalResultService personalResultService){
        return gameData.stream()
                .skip(1)
                .filter(a->a.get(3).equals(team.getName()))
                .map(personalResultService::createPersonalResult)
                .collect(Collectors.toSet());
    }

    private static void initSupportedGames(){
        supportedGameTypes.put("HANDBALL", GameTypes.HANDBALL);
        supportedGameTypes.put("BASKETBALL", GameTypes.BASKETBALL);
    }

    public Set<Team> createTeams(List<List<String>> gameData){
        return gameData.stream()
        .skip(1)
        .map(p->p.get(3))
        .collect(Collectors.toSet())
                .stream().
                map(teamName->teamService.createTeam(teamName))
                .collect(Collectors.toSet());
    }

    public int getWinnerBonus(Game game, Team team){
        int bonus = 0;
        if (team.equals(game.getWinner())){
            bonus = 10;
        }
        return bonus;
    }

}
