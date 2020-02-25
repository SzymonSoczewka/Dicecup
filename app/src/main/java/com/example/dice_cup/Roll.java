package com.example.dice_cup;
import java.io.Serializable;
import java.util.ArrayList;

class Roll implements Serializable {

    private int score;
    private ArrayList<Integer> dices;

    Roll(int score, ArrayList<Integer> dices){
        setScore(score);
        setDices(dices);
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