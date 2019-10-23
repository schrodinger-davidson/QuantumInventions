package com.example.andy.myapplication.model.dictionary;

import com.example.andy.myapplication.model.dictionary.GetDictionaryResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

// Dicitionary class for holding @baseUrl Api json response Dictionary Array
public class Dictionary {

    @Expose
    @SerializedName("dictionary")
    ArrayList<GetDictionaryResponse> dictionary;



    public Dictionary(){
        dictionary = new ArrayList<>();
    }

    public ArrayList<GetDictionaryResponse> getDictionary() {
        return dictionary;
    }

    public void setDictionary(ArrayList<GetDictionaryResponse> dictionary) {
        this.dictionary = dictionary;
    }



}
