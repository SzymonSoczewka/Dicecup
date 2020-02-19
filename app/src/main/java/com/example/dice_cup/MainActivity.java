package com.example.dice_cup;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private Button add,subtract,roll;
    private TextView dicesNum,lastScore;
    private LinearLayout dicesField;
    private ListView historyListView;
    private Context c;
    LinearLayout.LayoutParams params;
    private int currentNum = 1;
    private int sum;
    ImageView dice;
    private Random r = new Random();
    private ArrayList<String> historyRecord = new ArrayList<>();
    private ArrayList<Integer> historyRecord2 = new ArrayList<>();

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
            @Override
            public void onClick(View v) {
                dicesField.removeAllViews();
                sum = 0;
                historyRecord2.clear();
                generateDices(currentNum);
            }
        });
    }
    private void generateDices(int currentNum) {
        for (int i = 0; i < currentNum; i++) {
            int randomNum = r.nextInt(6) + 1;
            sum += randomNum;
            dice = new ImageView(c);
            switch (randomNum) {
                case 1:
                    dice.setImageResource(R.drawable.dice_one);
                    historyRecord2.add(R.drawable.dice_one);
                    break;
                case 2:
                    dice.setImageResource(R.drawable.dice_two);
                    historyRecord2.add(R.drawable.dice_two);
                    break;
                case 3:
                    dice.setImageResource(R.drawable.dice_three);
                    historyRecord2.add(R.drawable.dice_three);
                    break;
                case 4:
                    dice.setImageResource(R.drawable.dice_four);
                    historyRecord2.add(R.drawable.dice_four);
                    break;
                case 5:
                    dice.setImageResource(R.drawable.dice_five);
                    historyRecord2.add(R.drawable.dice_five);
                    break;
                case 6:
                    dice.setImageResource(R.drawable.dice_six);
                    historyRecord2.add(R.drawable.dice_six);
                    break;
            }
            params = new LinearLayout.LayoutParams(150,150);
            params.setMargins(5,0,5,0);
            dice.setLayoutParams(params);
            dicesField.addView(dice);
        }
        lastScore.setText(String.valueOf(sum));
        setAdapter(historyRecord);
    }

    private void setAdapter(ArrayList<String> historyRecord) {
        historyRecord.add(historyRecord.size()+1 +". SUM = " + sum);
        RollAdapter arrayAdapter = new RollAdapter(this,historyRecord);
        historyListView.setAdapter(arrayAdapter);
    }

    private void init() {
        add = findViewById(R.id.addButt);
        subtract = findViewById(R.id.subtractButt);
        dicesNum = findViewById(R.id.dicesNum);
        roll = findViewById(R.id.roll);
        dicesField = findViewById(R.id.dicesField);
        historyListView = findViewById(R.id.listView);
        lastScore = findViewById(R.id.lastScore);
        }


    class RollAdapter extends ArrayAdapter<String>{

        private ArrayList<String> rolls = historyRecord;

        public RollAdapter(@NonNull Context context, @NonNull ArrayList<String> rolls) {
            super(context, 0, rolls);
            this.rolls = rolls;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {

            if(view == null)
            {
                //Obtain inflater
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.single_record,null);
            }

            String score = historyRecord.get(position);

            TextView txtScore = view.findViewById(R.id.textView);
            txtScore.setText(score);


            LinearLayout dicesLayout = view.findViewById(R.id.dicesContainter);
            dicesLayout.removeAllViews();
            for (Integer imageID : historyRecord2) {
                ImageView dice = new ImageView(c);
                dice.setImageResource(imageID);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(80, LinearLayout.LayoutParams.MATCH_PARENT);
                params.setMargins(5,5,5,5);
                dice.setLayoutParams(params);
                dicesLayout.addView(dice);
            }
            return view;
        }
        }
    }
