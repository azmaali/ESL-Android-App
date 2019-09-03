package com.example.apidemo;

public class Word {

    private String definition;
    private String partOfSpeech;
    private String [] examples ;
    private String [] synonyms;

    public Word () {}

    public String getDefinition () {
        return definition;

    }

    public String getPartOfSpeech () {
        return partOfSpeech;
    }

    public String[] getExamples () {
        return examples;
    }

    public String[] getSynonyms () {
        return synonyms;
    }
}
