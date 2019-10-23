package com.example.andy.myapplication.activities;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andy.myapplication.R;
import com.example.andy.myapplication.adapters.CustomAdapter;
import com.example.andy.myapplication.db.MyPref;
import com.example.andy.myapplication.model.DataModel;
import com.example.andy.myapplication.model.dictionary.Dictionary;
import com.example.andy.myapplication.model.dictionary.GetDictionaryResponse;
import com.example.andy.myapplication.network.ApiHandler;
import com.example.andy.myapplication.network.UtilsApi;
import com.example.andy.myapplication.util.MapUtil;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity {
    private final int REQ_CODE = 100;

    RecyclerView recyclerView;
    Button btnSpeak;
    Dictionary dictionary;
    HashMap<String,Integer> hashMap;
    MyPref myPref;
    Gson gson;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_view_dictionary);
        btnSpeak = findViewById(R.id.btn_speak);
        hashMap = new HashMap<>();
        myPref = new MyPref(MainActivity.this);
        gson = new Gson();


        String res = myPref.getprefDictionary();
        dictionary = gson.fromJson(res, Dictionary.class);


        if ( dictionary ==null || res.isEmpty()){
            if(networkConnection.isActive()) {
                callDictionaryUrl();
            }
        }else {
            for(int i=0; i< dictionary.getDictionary().size();i++){
                hashMap.put(dictionary.getDictionary().get(i).getWord().toLowerCase(),dictionary.getDictionary().get(i).getFrequency());
            }


            initAdapter(hashMap);
        }




        btnSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Need to speak");
                //intent.putExtra(RecognizerIntent.EXTRA_SUPPORTED_LANGUAGES, RecognizerIntent.EXTRA_LANGUAGE);
                try {
                    if(networkConnection.isActive()) {
                        startActivityForResult(intent, REQ_CODE);
                    }
                } catch (ActivityNotFoundException a) {
                    Toast.makeText(getApplicationContext(),
                            "Sorry your device not supported",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public ArrayList<DataModel> prepareData(HashMap hashMap){

        HashMap map = (HashMap) MapUtil.sortByValue(hashMap);

         ArrayList<DataModel> arrayList = new ArrayList<>();

        for(int i=0; i< dictionary.getDictionary().size();i++) {

            Object[] keys =   map.keySet().toArray();
            ArrayList<Integer> values =  new ArrayList<>(map.values()) ;

            arrayList.add(new DataModel(keys[i].toString()));
            arrayList.add(new DataModel(Integer.toString(values.get(i))));
        }

        return arrayList;

    }

    public void initAdapter(HashMap hashMap){
        ArrayList<DataModel> arrayList = prepareData(hashMap);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
        CustomAdapter customAdapter = new CustomAdapter(MainActivity.this, arrayList);
        recyclerView.setAdapter(customAdapter); // set the Adapter to RecyclerView
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                     precessData(result);

                }
                break;
            }
        }
    }

    private void precessData(ArrayList<String> result) {

        String r =  result.get(0).toLowerCase();

        if(r.equalsIgnoreCase("xx")){
            r = "twenty";
        }else if (r.equalsIgnoreCase("second exit")){
            r = "2nd exit";
        }else {

        }


        setData(r);
    }




    public void setData(String result){

       boolean contains= hashMap.containsKey(result);

        Log.d("devdx", "setData: "+contains+" "+result+" "+hashMap.get(result));

       if(contains){


           hashMap.put(result,hashMap.get(result)+1);
           Log.d("devdx", "setData: true"+hashMap.get(result));
           ArrayList<DataModel> arrayList = prepareData(hashMap);
           GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
           recyclerView.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
           CustomAdapter customAdapter = new CustomAdapter(MainActivity.this, arrayList);
           recyclerView.setAdapter(customAdapter); // set the Adapter to RecyclerView
       }else {
       }
    }

    @Override
    protected void onResume() {
        super.onResume();




    }


    @Override
    protected void onPause() {
        super.onPause();


        ArrayList<GetDictionaryResponse> responses = new ArrayList<>();

        for(String i :hashMap.keySet()){

           GetDictionaryResponse wordFreq =new GetDictionaryResponse();
           wordFreq.setWord(i);
           wordFreq.setFrequency(hashMap.get(i));
           responses.add(wordFreq);

        }


        Dictionary wrapper = new Dictionary();
        wrapper.setDictionary(responses);
        myPref.setprefDictionary(gson.toJson(wrapper));


    }


    public void callDictionaryUrl(){

        String url = UtilsApi.BASE_URL_API+"dictionary-v2.json";
        Call<Dictionary> getDictionaryResponseCall  = ApiHandler.getApiService().call_GetHourlyDataResponse(url,UtilsApi.str_ContentType);

        getDictionaryResponseCall.enqueue(new Callback<Dictionary>() {
            @Override
            public void onResponse(Call<Dictionary> call, Response<Dictionary> response) {

                dictionary = response.body();


                String json= gson.toJson(dictionary);

                myPref.setprefDictionary(json);



                for(int i=0; i< dictionary.getDictionary().size();i++){
                    hashMap.put(dictionary.getDictionary().get(i).getWord().toLowerCase(),dictionary.getDictionary().get(i).getFrequency());
                }


                initAdapter(hashMap);

                Log.d("devdx", "onResponse: "+dictionary.getDictionary().get(0).getWord());
            }

            @Override
            public void onFailure(Call<Dictionary> call, Throwable t) {

                Log.d("devdx", "onResponse: "+t.toString());
            }
        });

    }

}




