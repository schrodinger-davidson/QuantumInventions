package com.example.andy.myapplication.model.dictionary;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


// Model Class for Dictionary Entity
public class GetDictionaryResponse {

        @SerializedName("word")
        @Expose
        private String word;

        @Expose
        @SerializedName("frequency")
        private Integer frequency;



        @SerializedName("word")
        public String getWord() {
            return word;
        }

        @SerializedName("word")
        public void setWord(String word) {
            this.word = word;
        }

        @SerializedName("frequency")
        public Integer getFrequency() {
            return frequency;
        }

        @SerializedName("frequency")
        public void setFrequency(Integer frequency) {
            this.frequency = frequency;
        }


    
}
