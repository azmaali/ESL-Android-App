package com.example.apidemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WordResultActivity extends AppCompatActivity {
    Word_Information wordInformation;
    ArrayList<String> titles;
    int numOfTabs;
    String jsonWordObject;
    Button pronunceButton;
    Button spellButton;
    Button contextButton;


    public static final String TAG = "WordResultActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_word_result);
        showWordResult();

        pronunceButton = findViewById(R.id.pronounceButton);
        spellButton = findViewById(R.id.spellButton);
        contextButton = findViewById(R.id.contextButton);


    }

    public ArrayList<String> getTabTitles (){

        ArrayList<String> POS = new ArrayList<String>();

        List<Word> wordList = wordInformation.getResults();

        for (int i = 0; i < wordList.size(); i++) {
            Word word = wordList.get(i);
            String partOfSpeech = word.getPartOfSpeech();
            if (!POS.contains(partOfSpeech.trim())){
                POS.add(partOfSpeech);
            }
        }

        return POS;

    }

    public void showWordResult() {

        Intent i = getIntent();
        jsonWordObject = i.getStringExtra("wordObject");
        wordInformation = new Gson().fromJson(jsonWordObject,Word_Information.class);
        titles = getTabTitles();
        numOfTabs = titles.size();


        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);

        // Create an adapter that knows which fragment should be shown on each page
        Bundle args = new Bundle();
        args.putString("word",jsonWordObject);


        SimpleFragmentPagerAdapter adapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager(),titles,numOfTabs,jsonWordObject);

        // Set the adapter onto the view pager
        viewPager.setAdapter(adapter);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }
}
