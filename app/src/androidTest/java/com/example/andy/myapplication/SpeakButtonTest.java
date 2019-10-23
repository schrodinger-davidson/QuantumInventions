package com.example.andy.myapplication;


import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.andy.myapplication.activities.MainActivity;
import com.example.andy.myapplication.adapters.CustomAdapter;
import com.example.andy.myapplication.model.DataModel;
import com.google.common.collect.Ordering;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.ArrayList;
import java.util.List;

import static androidx.test.espresso.assertion.ViewAssertions.matches;

@RunWith(AndroidJUnit4.class)
public class SpeakButtonTest {


    @Rule
    public ActivityTestRule<MainActivity> activityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void btnTest(){


       // Espresso.onView(ViewMatchers.withId(R.id.btnSpeak)).perform(ViewActions.click());


    }


    @Test
    public void recyclerViewTest(){



        Espresso.onView(ViewMatchers.withId(R.id.recyclerView)).check(matches(isSortedAlphabetically()));

    }

    private static Matcher<View> isSortedAlphabetically() {
        return new TypeSafeMatcher<View>() {

            private final List<Integer> number = new ArrayList<>();

            @Override
            protected boolean matchesSafely(View item) {
                RecyclerView recyclerView = (RecyclerView) item;
                CustomAdapter adapter = (CustomAdapter) recyclerView.getAdapter();
                number.clear();
                number.addAll(extractTeamNames(adapter.getList()));
                return Ordering.natural().isOrdered(number);
            }

            private ArrayList<Integer> extractTeamNames(ArrayList<DataModel> dataModelList) {
                ArrayList<Integer> number = new ArrayList<>();
                for (DataModel dataModel : dataModelList) {

                    try {
                      int n =  Integer.parseInt(dataModel.text);
                      number.add(n);

                    }catch (NumberFormatException e){

                        Log.d("devdx", "extractTeamNames: ");
                    }


                }

                Log.d("devdx", "extractTeamNames: "+number.toString());
                return number;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("has items sorted alphabetically: " +number );
            }
        };
    }



}
