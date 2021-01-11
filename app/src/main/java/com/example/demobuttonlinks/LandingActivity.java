package com.example.demobuttonlinks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class LandingActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<ButtonModel> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        getSupportActionBar().setTitle("You Are Awesome");

        data = genData();

        recyclerView = findViewById(R.id.recycler);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(data);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(adapter);
    }

    private List<ButtonModel> genData() {
        List<ButtonModel> generatedData = new ArrayList<ButtonModel>();

        // Static buttons
        generatedData.add(new ButtonModel("Google", "https://www.google.com"));
        generatedData.add(new ButtonModel("Wikipedia", "https://www.wikipedia.com"));
        generatedData.add(new ButtonModel("Reddit", "https://www.reddit.com"));
        generatedData.add(new ButtonModel("Facebook", "https://www.facebook.com"));
        generatedData.add(new ButtonModel("Twitter", "https://www.twitter.com"));
        generatedData.add(new ButtonModel("YouTube", "https://www.youtube.com"));
        generatedData.add(new ButtonModel("Twitch", "https://www.twitch.com"));

        // Dynamic button page
        generatedData.add(new ButtonModel("Dynamic Buttons", "-"));

        return generatedData;
    }
}