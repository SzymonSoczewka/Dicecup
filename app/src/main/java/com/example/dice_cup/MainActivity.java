package com.example.dice_cup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    public static final int HISTORY_ACTIVITY = 2;
    private Button add,subtract,roll;
    private TextView dicesToRoll, score;
    private LinearLayout dicesLayout;
    private ListView listView;
    private Context context;
    private ImageView history;
    private int currentNum,sum;
    private ArrayList<Integer> dices = new ArrayList<>();
    private ArrayList<Roll> rolls = new ArrayList<>();
    private RollAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        currentNum = 1;
        context = getWindow().getContext();
        initializeViews();
        setButtons();
    }
    private void setButtons() {
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentNum = currentNum == 6 ? 6 : Integer.parseInt(dicesToRoll.getText().toString()) + 1 ;
                dicesToRoll.setText(String.valueOf(currentNum));
            }
        });
        subtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentNum = currentNum == 1 ? 1 : Integer.parseInt(dicesToRoll.getText().toString()) - 1 ;
                dicesToRoll.setText(String.valueOf(currentNum));
            }
        });
        roll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dicesLayout.removeAllViews();
                sum = 0;
                dices.clear();
                generateDices(currentNum);
            }
        });
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context,HistoryActivity.class);
                i.putExtra("historyArrayList",rolls);
                startActivityForResult(i,HISTORY_ACTIVITY);
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == HISTORY_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
                this.rolls = (ArrayList<Roll>) data.getExtras().getSerializable("emptyArrayList");
                if(rolls.isEmpty()){
                    this.arrayAdapter.clear();
                }
            }
        }
    }

    private void generateDices(int currentNum) {
        for (int i = 0; i < currentNum; i++) {
            int randomNum = new Random().nextInt(6) + 1;
            sum += randomNum;
            ImageView dice = new ImageView(context);
            switch (randomNum) {
                case 1:
                    setImage(R.drawable.dice_one,dice);
                    break;
                case 2:
                    setImage(R.drawable.dice_two,dice);
                    break;
                case 3:
                    setImage(R.drawable.dice_three,dice);
                    break;
                case 4:
                    setImage(R.drawable.dice_four,dice);
                    break;
                case 5:
                    setImage(R.drawable.dice_five,dice);
                    break;
                case 6:
                    setImage(R.drawable.dice_six,dice);
                    break;
            }
            dice.setLayoutParams(setParameters());
            dicesLayout.addView(dice);
        }
        score.setText(String.valueOf(sum));
        ArrayList<Integer> dicesCopy = new ArrayList<>(dices.size());
        dicesCopy.addAll(dices);
        rolls.add(new Roll(sum,dicesCopy,createTimestamp()));
        setAdapter(rolls);
    }

    private LinearLayout.LayoutParams setParameters(){
        LinearLayout.LayoutParams params;
        params = new LinearLayout.LayoutParams(150,150);
        params.setMargins(5,0,5,0);
        return params;
    }
    private String createTimestamp(){
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        return  dateFormat.format(date);
    }
    private void setImage(int id, ImageView dice) {
        dice.setImageResource(id);
        dices.add(id);
    }
    private void initializeViews() {
        add = findViewById(R.id.addButt);
        subtract = findViewById(R.id.subtractButt);
        dicesToRoll = findViewById(R.id.dicesNum);
        roll = findViewById(R.id.roll);
        dicesLayout = findViewById(R.id.dicesField);
        score = findViewById(R.id.lastScore);
        history = findViewById(R.id.historyIcon);
        listView = findViewById(R.id.mainListView);
        }
    private void setAdapter(ArrayList<Roll> rolls) {
        arrayAdapter = new RollAdapter(context, rolls);
        arrayAdapter.notifyDataSetChanged();
        listView.setAdapter(arrayAdapter);
        listView.setSelection(arrayAdapter.getCount() -1);
    }
}
