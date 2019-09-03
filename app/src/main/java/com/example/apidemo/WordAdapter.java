package com.example.apidemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<Word> {
    public WordAdapter(Context context, ArrayList<Word> words) {
        super(context, 0, words);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Word word  = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_word, parent, false);
        }
        // Lookup view for data population
        TextView def = (TextView) convertView.findViewById(R.id.def);
        TextView example = (TextView) convertView.findViewById(R.id.example);
        // Populate the data into the template view using the data object
        def.setText(word.getDefinition());
        example.setText(word.getExamples()[0]);
        // Return the completed view to render on screen
        return convertView;
    }
}