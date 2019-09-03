package com.example.apidemo;

import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter implements Serializable {
    ArrayList<String> tabTitles; //needs to be based in
    int numOfTabs;
    String jsonWordInfo;
    WordResultFragment [] fragments;





    public SimpleFragmentPagerAdapter(FragmentManager fm, ArrayList<String> titles, int tabCount, String jsonObjectString){

        super(fm);
        tabTitles = titles;
        numOfTabs = tabCount;
        jsonWordInfo = jsonObjectString;
        fragments = new WordResultFragment[tabCount];



    }


    @Override    public Fragment getItem(int position) {

        Bundle data = new Bundle();


        if (fragments[position] != null) {
            return fragments[position];

        }
        else {
            String partOfSpeech = tabTitles.get(position);
            Log.e("PagerAdapter",partOfSpeech);
            WordResultFragment fragment = new WordResultFragment();
            //fragments[position] = fragment;
            data.putString("POS",partOfSpeech);
            data.putString("word",jsonWordInfo);
            fragment.setArguments(data);
            fragments[position] = fragment;

            return fragment;

        }

    }


    @Override
    public int getCount() {
        return numOfTabs;
    }
    @Override    public CharSequence getPageTitle(int position) {

        return tabTitles.get(position);
    }
}
