package com.example.apidemo;

import java.util.ArrayList;
import java.util.List;

public class Word_Information {
    private String word;
    private ArrayList<Word> results;
    private Pronunciation pronunciation;


    public Word_Information(){}

    public String getWord(){
        return word;
    }

    public ArrayList<Word> getResults(){
        ArrayList<Word> lol = new ArrayList<Word>();

        return results;
    }

    public Pronunciation getPronunciation(){
        return  pronunciation;
    }




}
