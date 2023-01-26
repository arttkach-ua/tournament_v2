package com.exam.tournament.model;


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
