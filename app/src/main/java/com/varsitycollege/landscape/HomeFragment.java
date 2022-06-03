package com.varsitycollege.landscape;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomeFragment extends Fragment {
    private FloatingActionButton add;
    private Button save;
    private ImageView back;
    private EditText cat_name, cat_goal;
    private TextView txt;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private int clickCount = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.fragment_home, container, false);

        final View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        add = rootView.findViewById(R.id.add_listing);

        LinearLayout layout = rootView.findViewById(R.id.layout_view);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUpCategory(layout);
            }
        });

        return rootView;
    }

    public void popUpCategory(LinearLayout layout) {

        dialogBuilder = new AlertDialog.Builder(getActivity());
        final View categoryPopUp = getLayoutInflater().inflate(R.layout.activity_category, null);
        cat_name = categoryPopUp.findViewById(R.id.category_name);
        cat_goal = categoryPopUp.findViewById(R.id.category_goal);
        txt = categoryPopUp.findViewById(R.id.txt);

        save = categoryPopUp.findViewById(R.id.save);
        back = categoryPopUp.findViewById(R.id.back);

        dialogBuilder.setView(categoryPopUp);
        dialog = dialogBuilder.create();
        dialog.show();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.hide();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCount++;
                String name = cat_name.getText().toString();
                String goal = cat_goal.getText().toString();

                if (name.isEmpty()) {
                    cat_name.setError("Please enter category name");
                } else if (goal.isEmpty()) {
                    cat_goal.setError("Please enter category goal");
                } else {
                    Toast.makeText(getActivity(), "Category created", Toast.LENGTH_SHORT).show();
                    //adding buttons on homepage according to category name
                    Button btn = new Button(getActivity());
                    btn.setText(cat_name.getText());
                    btn.setId(1 + clickCount);
                    layout.addView(btn);

                    dialog.hide();
                }
            }
        });
        //https://www.youtube.com/watch?v=4GYKOzgQDWI - ref
    }
}


