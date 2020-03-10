package com.example.dice_cup;
import java.io.Serializable;
import java.util.ArrayList;

class Roll implements Serializable {

    private int score;
    private ArrayList<Integer> dices;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    private String timestamp;

    Roll(int score, ArrayList<Integer> dices, String timestamp){
        setScore(score);
        setDices(dices);
        setTimestamp(timestamp);
    }

    private void setScore(int score) {
        this.score = score;
    }

    int getScore() {
        return score;
    }

    private void setDices(ArrayList<Integer> dices) {
        this.dices = dices;
    }

    ArrayList<Integer> getDices() {
        return dices;
    }

}