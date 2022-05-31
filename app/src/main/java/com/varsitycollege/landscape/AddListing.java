package com.varsitycollege.landscape;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Spinner;

public class AddListing extends AppCompatActivity {
    String[] categories = {"Water", "Animals", "Plants/Trees", "Land", "Life", "Sky", "Flowers", "Space"};
    Spinner category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_add_listing);

    }
}