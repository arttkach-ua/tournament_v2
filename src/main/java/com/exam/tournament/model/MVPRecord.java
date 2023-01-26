package com.exam.tournament.model;

/**
 * Class is used to store calculation of mvp points for one player in one game
 * Class is unmountable
 * @param player
 * @param points
 * @param game
 */
public record MVPRecord(Player player, Integer points, Game game) {

    public Player getPlayer() {
        return player;
    }


    public Integer getPoints() {
        return points;
    }


    public Game getGame() {
        return game;
    }
}
