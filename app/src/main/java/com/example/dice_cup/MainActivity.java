package com.example.dice_cup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private Button add,subtract,dicesNum,roll,clear;
    private LinearLayout dicesField, historyLayout;
    private Context c;
    LinearLayout.LayoutParams params;
    private int currentNum = 1;
    private int sum = 0;
    private String historyLog = "";
    ImageView dice;
    private Random r = new Random();
    private List<String> historyRecord = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        c = getWindow().getContext();
        init();
        setButtons();

    }

    private void setButtons() {
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentNum = currentNum == 6 ? 6 : Integer.parseInt(dicesNum.getText().toString()) + 1 ;
                dicesNum.setText(String.valueOf(currentNum));
            }
        });
        subtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentNum = currentNum == 1 ? 1 : Integer.parseInt(dicesNum.getText().toString()) - 1 ;
                dicesNum.setText(String.valueOf(currentNum));
            }
        });
        roll.setOnClickListener(new View.OnClickListener() {
            @Override // 300 git
            public void onClick(View v) {
                dicesField.removeAllViews();
                sum = 0;
                generateDices(currentNum);
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override // 300 git
            public void onClick(View v) {
                clearHistory();
            }
        });
    }
    private LinearLayout.LayoutParams adjustParams(int num){
        if(currentNum < 4)
            return new LinearLayout.LayoutParams(300,300);
        else if(currentNum == 4)
            return new LinearLayout.LayoutParams(250,250);
        else if(currentNum == 5)
            return new LinearLayout.LayoutParams(200,200);
        else
            return new LinearLayout.LayoutParams(150,150);
    }

    private void generateDices(int currentNum) {
        for (int i = 0; i < currentNum; i++) {
            int randomNum = r.nextInt(6) + 1;
            sum += randomNum;
            dice = new ImageView(c);
            switch (randomNum) {
                case 1:
                    dice.setImageResource(R.drawable.dice_one);
                    break;
                case 2:
                    dice.setImageResource(R.drawable.dice_two);
                    break;
                case 3:
                    dice.setImageResource(R.drawable.dice_three);
                    break;
                case 4:
                    dice.setImageResource(R.drawable.dice_four);
                    break;
                case 5:
                    dice.setImageResource(R.drawable.dice_five);
                    break;
                case 6:
                    dice.setImageResource(R.drawable.dice_six);
                    break;
            }
            params = adjustParams(currentNum);
            params.setMargins(5,0,5,0);
            dice.setLayoutParams(params);
            dicesField.addView(dice);
        }
        historyLog= "SUM = "+sum;
        handleHistory(historyLog);
    }

    private void handleHistory(String historyLog) {
        if(historyRecord.size()>5){
            historyRecord.remove(0);
            historyRecord.add(historyLog);
            addLogs(historyRecord);
        }
        else{
            historyRecord.add(historyLog);
            addLogs(historyRecord);
        }
    }


    private void addLogs(List<String> logs)
    {
        TextView v;
        historyLayout.removeAllViews();
        v = new TextView(c);
        String str = "History";
        v.setText(str);
        historyLayout.addView(new TextView(c));
        for (String s: logs){
            v = new TextView(c);
            v.setText(s);
            historyLayout.addView(v);
        }
    }
    private void init() {
     add = findViewById(R.id.addButt);
     subtract = findViewById(R.id.subtractButt);
     dicesNum = findViewById(R.id.dicesNum);
     roll = findViewById(R.id.roll);
     clear = findViewById(R.id.clear);
     dicesField = findViewById(R.id.dicesField);
     historyLayout = findViewById(R.id.historyLayout);
    }

    public void clearHistory() {
        historyLayout.removeAllViews();
        historyRecord.clear();
    }

}
