package com.example.apidemo;


import java.util.LinkedList;
import java.util.Queue;

public class SearchHistory {

    final int MAX_HISTORY_LENGTH = 5;
    private static SearchHistory search_history_instance = null;
    public Queue<Word_Information> history = new LinkedList<>();

    private SearchHistory() {

    }

    // static method to create instance of Singleton class
    public static SearchHistory getInstance() {
        if (search_history_instance == null)
            search_history_instance = new SearchHistory();

        return search_history_instance;
    }

    public Queue<Word_Information> getHistory(){
        return history;
    }

    public void addToHistory(Word_Information wordInfo){
        if (history.size() == MAX_HISTORY_LENGTH){
            history.remove();
        }

        history.add(wordInfo);
    }

}