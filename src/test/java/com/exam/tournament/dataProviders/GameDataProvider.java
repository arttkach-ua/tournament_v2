package com.exam.tournament.dataProviders;

import com.exam.tournament.model.container.player.BasketballPlayerInfoContainer;
import com.exam.tournament.model.container.player.HandballPlayerInfoContainer;
import com.exam.tournament.model.container.player.PlayerInfoContainer;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class GameDataProvider {
    public List<List<String>> getBasketBallDataAsFromFile() {
        return List.of(
                List.of("BASKETBALL"),
                List.of("player 1", "nick1", "4", "Team A", "10", "2", "7"),
                List.of("player 2", "nick2", "8", "Team A", "0", "10", "0"),
                List.of("player 3", "nick3", "15", "Team A", "15", "10", "4"),
                List.of("player 4", "nick4", "16", "Team B", "20", "0", "0"),
                List.of("player 5", "nick5", "23", "Team B", "4", "7", "7"),
                List.of("player 6", "nick6", "42", "Team B", "8", "10", "0"));
    }

    public List<List<String>> getHandBallDataAsFromFile() {
        return List.of(
                List.of("HANDBALL"),
                List.of("player 1", "nick1", "4", "Team A", "0", "20"),
                List.of("player 2", "nick2", "8", "Team A", "15", "20"),
                List.of("player 3", "nick3", "15", "Team A", "10", "20"),
                List.of("player 4", "nick4", "16", "Team B", "1", "25"),
                List.of("player 5", "nick5", "23", "Team B", "12", "25"),
                List.of("player 6", "nick6", "42", "Team B", "8", "25"));
    }

    public Set<PlayerInfoContainer> getHandBallInfoContainers() {
        return Set.of(HandballPlayerInfoContainer.builder().playerName("player 1").nickName("nick1").number("1").team("Team A").goalsMade("0").goalsReceived("20").build(),
                HandballPlayerInfoContainer.builder().playerName("player 2").nickName("nick2").number("2").team("Team A").goalsMade("15").goalsReceived("20").build(),
                HandballPlayerInfoContainer.builder().playerName("player 3").nickName("nick3").number("3").team("Team A").goalsMade("10").goalsReceived("20").build(),
                HandballPlayerInfoContainer.builder().playerName("player 4").nickName("nick4").number("4").team("Team B").goalsMade("0").goalsReceived("25").build(),
                HandballPlayerInfoContainer.builder().playerName("player 5").nickName("nick5").number("5").team("Team B").goalsMade("12").goalsReceived("25").build(),
                HandballPlayerInfoContainer.builder().playerName("player 6").nickName("nick6").number("6").team("Team B").goalsMade("8").goalsReceived("25").build());
    }

    public Set<PlayerInfoContainer> getBasketBallInfoContainers() {
        return Set.of(BasketballPlayerInfoContainer.builder().playerName("player 1").nickName("nick1").number("1").team("Team A").scored("10").rebounds("2").assists("7").build(),
                BasketballPlayerInfoContainer.builder().playerName("player 2").nickName("nick2").number("2").team("Team A").scored("0").rebounds("10").assists("0").build(),
                BasketballPlayerInfoContainer.builder().playerName("player 3").nickName("nick3").number("3").team("Team A").scored("15").rebounds("10").assists("4").build(),
                BasketballPlayerInfoContainer.builder().playerName("player 4").nickName("nick4").number("4").team("Team B").scored("20").rebounds("0").assists("0").build(),
                BasketballPlayerInfoContainer.builder().playerName("player 5").nickName("nick5").number("5").team("Team B").scored("4").rebounds("7").assists("7").build(),
                BasketballPlayerInfoContainer.builder().playerName("player 6").nickName("nick6").number("6").team("Team B").scored("8").rebounds("10").assists("0").build());
    }

    public String[] getBasketBallInfoArray() {
        return List.of("player 2", "nick2", "2", "Team A", "0", "10", "1").toArray(new String[7]);
    }

    public String[] getHandBallInfoArray() {
        return List.of("player 2", "nick2", "2", "Team A", "22", "33").toArray(new String[6]);
    }
}
