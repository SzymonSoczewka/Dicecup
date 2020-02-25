package com.example.dice_cup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    public static final int HISTORY_ACTIVITY = 2;
    private Button add,subtract,roll;
    private TextView dicesNum,lastScore;
    private LinearLayout dicesField;
    private ListView mainListView;
    private Context c;
    private ImageView historyIcon;
    LinearLayout.LayoutParams params;
    private int currentNum;
    private int sum;
    ImageView dice;
    private Random r;
    private ArrayList<Integer> dices = new ArrayList<>();
    private ArrayList<Roll> rolls = new ArrayList<>();
    private RollAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        currentNum = 1;
        r = new Random();
        c = getWindow().getContext();
        initViews();
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
            @Override
            public void onClick(View v) {
                dicesField.removeAllViews();
                sum = 0;
                dices.clear();
                generateDices(currentNum);
            }
        });
        historyIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(c,HistoryActivity.class);
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
                // TODO Extract the data returned from the child Activity.
                this.rolls = (ArrayList<Roll>) data.getExtras().getSerializable("emptyArrayList");
                lastScore.setText(String.valueOf(0));
                if(rolls.isEmpty()){
                    this.arrayAdapter.clear();
                }
            }
        }
    }

    private void generateDices(int currentNum) {
        for (int i = 0; i < currentNum; i++) {
            int randomNum = r.nextInt(6) + 1;
            sum += randomNum;
            dice = new ImageView(c);
            int id,size;
            switch (randomNum) {
                case 1:
                    id = R.drawable.dice_one;
                    setImage(id);
                    break;
                case 2:
                    id = R.drawable.dice_two;
                    setImage(id);
                    break;
                case 3:
                    id = R.drawable.dice_three;
                    setImage(id);
                    break;
                case 4:
                    id = R.drawable.dice_four;
                    setImage(id);
                    break;
                case 5:
                    id = R.drawable.dice_five;
                    setImage(id);
                    break;
                case 6:
                    id = R.drawable.dice_six;
                    setImage(id);
                    break;
            }
            params = new LinearLayout.LayoutParams(150,150);
            params.setMargins(5,0,5,0);
            dice.setLayoutParams(params);
            dicesField.addView(dice);
        }
        lastScore.setText(String.valueOf(sum));
        ArrayList<Integer> copyDices = new ArrayList<>(dices.size());
        copyDices.addAll(dices);
        rolls.add(new Roll(sum,copyDices));
        setAdapter(rolls);
    }

    private void setImage(int id) {
        dice.setImageResource(id);
        dices.add(id);
    }
    private void initViews() {
        add = findViewById(R.id.addButt);
        subtract = findViewById(R.id.subtractButt);
        dicesNum = findViewById(R.id.dicesNum);
        roll = findViewById(R.id.roll);
        dicesField = findViewById(R.id.dicesField);
        lastScore = findViewById(R.id.lastScore);
        historyIcon = findViewById(R.id.historyIcon);
        mainListView = findViewById(R.id.mainListView);
        }
    private void setAdapter(ArrayList<Roll> rolls) {
        arrayAdapter = new RollAdapter(c, rolls);
        arrayAdapter.notifyDataSetChanged();
        mainListView.setAdapter(arrayAdapter);
        mainListView.setSelection(arrayAdapter.getCount() -1);
    }
}
