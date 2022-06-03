package com.varsitycollege.landscape;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CategoryView extends AppCompatActivity {
    private ImageView img1, back, a1, a2, a3, a4, a5, a6;
    private TextView name, goal, title, category, caption, description;
    private FloatingActionButton add;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_view);

        name = findViewById(R.id.heading);
        goal = findViewById(R.id.txtGoal);
        add = findViewById(R.id.add_listing1);

        a1 = findViewById(R.id.animal1);
        a2 = findViewById(R.id.animal2);
        a3 = findViewById(R.id.animal3);
        a4 = findViewById(R.id.animal4);
        a5 = findViewById(R.id.animal5);
        a6 = findViewById(R.id.animal6);

        a1.setImageResource(R.drawable.animal1);
        a2.setImageResource(R.drawable.animal2);
        a3.setImageResource(R.drawable.animal3);
        a4.setImageResource(R.drawable.animal4);
        a5.setImageResource(R.drawable.animal5);
        a6.setImageResource(R.drawable.animal6);

        String catName = getIntent().getStringExtra("cat_name");
        String catGoal = getIntent().getStringExtra("cat_goal");

        name.setText(catName);
        goal.setText("Category goal = " + catGoal);

        a1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View categoryPopUp = getLayoutInflater().inflate(R.layout.activity_view_entries, null);
                dialogBuilder = new AlertDialog.Builder(CategoryView.this);
                dialogBuilder.setView(categoryPopUp);
                dialog = dialogBuilder.create();
                dialog.show();

                title = categoryPopUp.findViewById(R.id.txtTitle1);
                category = categoryPopUp.findViewById(R.id.txtCat1);
                caption = categoryPopUp.findViewById(R.id.txtCaption1);
                description = categoryPopUp.findViewById(R.id.txtDescription1);

                title.setText("Tiger");
                category.setText(catName);
                caption.setText("Wild animal photograph");
                description.setText("None");
            }
        });

        a2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View categoryPopUp = getLayoutInflater().inflate(R.layout.activity_view_entries, null);
                dialogBuilder = new AlertDialog.Builder(CategoryView.this);
                dialogBuilder.setView(categoryPopUp);
                dialog = dialogBuilder.create();
                dialog.show();

                title = categoryPopUp.findViewById(R.id.txtTitle1);
                category = categoryPopUp.findViewById(R.id.txtCat1);
                caption = categoryPopUp.findViewById(R.id.txtCaption1);
                description = categoryPopUp.findViewById(R.id.txtDescription1);

                title.setText("Hippo");
                category.setText(catName);
                caption.setText("Wild animal photograph");
                description.setText("None");
            }
        });

        a3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View categoryPopUp = getLayoutInflater().inflate(R.layout.activity_view_entries, null);
                dialogBuilder = new AlertDialog.Builder(CategoryView.this);
                dialogBuilder.setView(categoryPopUp);
                dialog = dialogBuilder.create();
                dialog.show();

                title = categoryPopUp.findViewById(R.id.txtTitle1);
                category = categoryPopUp.findViewById(R.id.txtCat1);
                caption = categoryPopUp.findViewById(R.id.txtCaption1);
                description = categoryPopUp.findViewById(R.id.txtDescription1);

                title.setText("Bird");
                category.setText(catName);
                caption.setText("Wild animal photograph");
                description.setText("None");
            }
        });

        a4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View categoryPopUp = getLayoutInflater().inflate(R.layout.activity_view_entries, null);
                dialogBuilder = new AlertDialog.Builder(CategoryView.this);
                dialogBuilder.setView(categoryPopUp);
                dialog = dialogBuilder.create();
                dialog.show();

                title = categoryPopUp.findViewById(R.id.txtTitle1);
                category = categoryPopUp.findViewById(R.id.txtCat1);
                caption = categoryPopUp.findViewById(R.id.txtCaption1);
                description = categoryPopUp.findViewById(R.id.txtDescription1);

                title.setText("Zebra");
                category.setText(catName);
                caption.setText("Wild animal photograph");
                description.setText("None");
            }
        });

        a5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View categoryPopUp = getLayoutInflater().inflate(R.layout.activity_view_entries, null);
                dialogBuilder = new AlertDialog.Builder(CategoryView.this);
                dialogBuilder.setView(categoryPopUp);
                dialog = dialogBuilder.create();
                dialog.show();

                title = categoryPopUp.findViewById(R.id.txtTitle1);
                category = categoryPopUp.findViewById(R.id.txtCat1);
                caption = categoryPopUp.findViewById(R.id.txtCaption1);
                description = categoryPopUp.findViewById(R.id.txtDescription1);

                title.setText("Penguin");
                category.setText(catName);
                caption.setText("Wild animal photograph");
                description.setText("None");
            }
        });

        a6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View categoryPopUp = getLayoutInflater().inflate(R.layout.activity_view_entries, null);
                dialogBuilder = new AlertDialog.Builder(CategoryView.this);
                dialogBuilder.setView(categoryPopUp);
                dialog = dialogBuilder.create();
                dialog.show();

                title = categoryPopUp.findViewById(R.id.txtTitle1);
                category = categoryPopUp.findViewById(R.id.txtCat1);
                caption = categoryPopUp.findViewById(R.id.txtCaption1);
                description = categoryPopUp.findViewById(R.id.txtDescription1);

                title.setText("Peacock");
                category.setText(catName);
                caption.setText("Wild animal photograph");
                description.setText("None");
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AddListingFragment()).commit();
            }
        });
    }
}