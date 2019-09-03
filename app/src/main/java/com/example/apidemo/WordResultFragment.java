package com.example.apidemo;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class WordResultFragment extends Fragment {
    Word_Information wordInformation;
    String jsonWordObject;
    String POS;
    ArrayList<Word> arrayOfWords = new ArrayList<Word>();
    TextView word;
    TextView pronunciation;

    public WordResultFragment () {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup view, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search, view, false);

        word = rootView.findViewById(R.id.wordTextView);
        pronunciation = rootView.findViewById(R.id.pronunciationTextView);



        if (getArguments().getString("word") != null ) {
            jsonWordObject = getArguments().getString("word");
            POS = getArguments().getString("POS").trim();
            Log.e("WordResultFragment",POS);


        }
        else {
            Toast.makeText(getActivity(),
                    "null",
                    Toast.LENGTH_LONG).show();

        }


        wordInformation = new Gson().fromJson(jsonWordObject,Word_Information.class);
        //Construct the data source
        arrayOfWords = this.getWords(wordInformation.getResults());
        word.setText(POS);
        pronunciation.setText(wordInformation.getPronunciation().getAll());



// Create the adapter to convert the array to views
        WordAdapter adapter = new WordAdapter(getActivity(), arrayOfWords);
// Attach the adapter to a ListView
        ListView listView = (ListView) rootView.findViewById(R.id.wordDefinitionListView);
        listView.setAdapter(adapter);

        return rootView;
    }

    public ArrayList<Word> getWords(ArrayList<Word> wordList){
        ArrayList<Word> wordsWithSamePOS = new ArrayList<Word>();

        for (int i = 0; i < wordList.size(); i++) {
            Word word = wordList.get(i);
            String partOfSpeech = word.getPartOfSpeech();
            if (partOfSpeech.equals(POS)){
                wordsWithSamePOS.add(word);
            }
        }

        return wordsWithSamePOS;

    }

}

