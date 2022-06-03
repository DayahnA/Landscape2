package com.varsitycollege.landscape;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private Button login;
    private Button signUp;

    EditText email, password;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"; // https://www.youtube.com/watch?v=gaykE36N7PY -ref

    ProgressDialog progressDialog;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = findViewById(R.id.btnLogin);
        signUp = findViewById(R.id.btnSignUp);

        email = findViewById(R.id.txtEmail);
        password = findViewById(R.id.pwBoxPassword);

        //https://www.youtube.com/watch?v=gaykE36N7PY - ref
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateLogin();
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, sign_up.class));
                //https://www.youtube.com/watch?v=TxpN7uWJyBw -ref
            }
        });


    }

    //https://www.youtube.com/watch?v=gaykE36N7PY - ref
    private void validateLogin() {
        String inputEmail = email.getText().toString();
        String inputPass = password.getText().toString();

        //verifying user text
        if (!inputEmail.matches(emailPattern)){
            email.setError("Enter correct email");
        } else if (inputPass.isEmpty() || inputPass.length()< 6 ){
            password.setError("Enter a valid password");
        } else {
            progressDialog.setMessage("Login in progress...");
            progressDialog.setTitle("Login");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.signInWithEmailAndPassword(inputEmail,inputPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){

                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this, "Login successful" , Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, Home.class));
                        //https://www.youtube.com/watch?v=TxpN7uWJyBw -ref
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this, "" + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

}