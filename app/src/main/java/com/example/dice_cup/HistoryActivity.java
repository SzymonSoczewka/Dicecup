package com.example.dice_cup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    RollAdapter arrayAdapter;
    Button clearButt,returnButt;
    private Context context;
    ListView listView;
    ArrayList<Roll> rolls;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        this.context = getWindow().getContext();
        initListView();
        initButtons();
    }

    private void initButtons() {
        returnButt = findViewById(R.id.returnButt);
        clearButt = findViewById(R.id.clearButt);
        returnButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnToMain();
            }
        });
        clearButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
            }
        });
    }

    private void clear() {
        if(!rolls.isEmpty()) {
            this.rolls.clear();
            arrayAdapter.notifyDataSetChanged();
            showToast("History was cleared.");
        } else
            showToast("History is empty.");
    }

    private void returnToMain() {
        Intent i = new Intent();
        i.putExtra("emptyArrayList",rolls);
        setResult(Activity.RESULT_OK,i);
        finish();
    }

    private void initListView() {
        listView = findViewById(R.id.historyLV);
         rolls = (ArrayList<Roll>) getIntent().getSerializableExtra("historyArrayList");
        setAdapter(rolls);
        setMsgForToasts();
    }
    private void setAdapter(ArrayList<Roll> rolls) {
        arrayAdapter = new RollAdapter(context, rolls);
        arrayAdapter.notifyDataSetChanged();
        listView.setAdapter(arrayAdapter);
        listView.setSelection(arrayAdapter.getCount() -1);
    }



    private void setMsgForToasts(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Roll roll = rolls.get(position);
                int score = roll.getScore();
                int dicesAmount = roll.getDices().size();
                String text;
                if(score / dicesAmount == 6)
                    text = "You are a god";
                else if(roll.getScore() / roll.getDices().size() > 3){
                    text = "You rock!";
                }
                else
                    text = "You weak boy";
                showToast(text);
            }
        });
    }
    private void showToast(String text) {
        Toast.makeText(context, text,
                Toast.LENGTH_SHORT).show();
    }
}

