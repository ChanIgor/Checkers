package ru.yandex.igor;

public class GetStarted {

    private boolean started = false;

    public GetStarted(){
        StartTheGame();
    }


    public void StartTheGame(){
        started = true;
    }

    public void gameOver(){
        started = false;
    }

    public boolean ifStarted(){
        return started;
    }
}
