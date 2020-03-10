package com.example.dice_cup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RollAdapter extends ArrayAdapter<Roll> {

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
        txtScore.setText(MessageFormat.format(roll.getTimestamp(), roll.getScore()));


        LinearLayout dicesLayout = view.findViewById(R.id.dicesContainter);
        List<Integer> dices = roll.getDices();
        dicesLayout.removeAllViews();
        for (Integer imageID : dices) {
            ImageView dice = new ImageView(getContext());
            dice.setImageResource(imageID);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(80, LinearLayout.LayoutParams.MATCH_PARENT);
            params.setMargins(5,5,5,5);
            dice.setLayoutParams(params);
            dicesLayout.addView(dice);
        }
        return view;
    }
}
