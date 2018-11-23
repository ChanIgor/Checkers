package ru.yandex.igor;

import io.javalin.Context;

public class Player {

    private String name;
    private int wins = 0;
    private boolean color;



    public Player(String name, boolean color){
        this.name = name;
        wins = 0;
        this.color = color;
    }
    public boolean getColor(){
        return color;
    }

    public String getName(){
        return name;
    }

    public void isWin(){
        wins++;
    }

    public int getWins(){
        return wins;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", color='" + color + '\'' +
                ", victories'= " + wins +
                '}';
    }
}
