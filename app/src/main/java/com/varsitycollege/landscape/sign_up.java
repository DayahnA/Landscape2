package com.varsitycollege.landscape;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class sign_up extends AppCompatActivity {

    private Button back;
    private Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        back = findViewById(R.id.btnBack);
        save = findViewById(R.id.btnSave);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(sign_up.this, MainActivity.class);
                startActivity(intent);
                //https://www.youtube.com/watch?v=TxpN7uWJyBw -ref
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(sign_up.this, MainActivity.class);
                startActivity(intent);
                //https://www.youtube.com/watch?v=TxpN7uWJyBw -ref

                Toast.makeText(sign_up.this, "Details successfully saved!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
