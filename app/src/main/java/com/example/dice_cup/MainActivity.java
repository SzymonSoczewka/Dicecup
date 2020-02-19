package com.example.dice_cup;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.MessageFormat;
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
    private int currentNum;
    private int sum;
    ImageView dice;
    private Random r;
    private ArrayList<Integer> dices = new ArrayList<>();
    private ArrayList<Roll> rolls = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        currentNum = 1;
        r = new Random();
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
                dices.clear();
                generateDices(currentNum);
            }
        });
    }
    private void generateDices(int currentNum) {
        for (int i = 0; i < currentNum; i++) {
            int randomNum = r.nextInt(6) + 1;
            sum += randomNum;
            dice = new ImageView(c);
            int id;
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

    private void setAdapter(ArrayList<Roll> rolls) {
        RollAdapter arrayAdapter = new RollAdapter(this, rolls);
        arrayAdapter.notifyDataSetChanged();
        historyListView.setAdapter(arrayAdapter);
        historyListView.setSelection(arrayAdapter.getCount() -1);
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


    class RollAdapter extends ArrayAdapter<Roll>{

        private ArrayList<Roll> rolls;

        RollAdapter(@NonNull Context context, @NonNull ArrayList<Roll> rolls) {
            super(context, 0, rolls);
            this.rolls = rolls;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {

            if(view == null)
            {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.single_record,null);
            }

            Roll roll = rolls.get(position);
            TextView txtScore = view.findViewById(R.id.textView);
            txtScore.setText(MessageFormat.format("Score: {0}", roll.getScore()));


            LinearLayout dicesLayout = view.findViewById(R.id.dicesContainter);
            List<Integer> dices = roll.getDices();
            dicesLayout.removeAllViews();
            for (Integer imageID : dices) {
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
