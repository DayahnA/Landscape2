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

public class sign_up extends AppCompatActivity {

    private Button back;
    private Button save;

    EditText email, password, confirmPass;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    ProgressDialog progressDialog;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        back = findViewById(R.id.btnBack);
        save = findViewById(R.id.btnSave);

        email = findViewById(R.id.txtEmail);
        password = findViewById(R.id.pwBoxPassword);
        confirmPass = findViewById(R.id.pwBoxConfirmPassword);

        //https://www.youtube.com/watch?v=gaykE36N7PY -ref
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(sign_up.this, MainActivity.class));
                //https://www.youtube.com/watch?v=TxpN7uWJyBw -ref
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidatingDetails();
            }
        });
    }

    //https://www.youtube.com/watch?v=gaykE36N7PY -ref
    private void ValidatingDetails() {
        String inputEmail = email.getText().toString();
        String inputPass = password.getText().toString();
        String confirmpassword = confirmPass.getText().toString();

        //verifying user text
        if (!inputEmail.matches(emailPattern)) {
            email.setError("Enter correct email");
        } else if (inputPass.isEmpty() || inputPass.length() < 6) {
            password.setError("Enter a valid password");
        } else if (!confirmpassword.equals(inputPass)) {
            confirmPass.setError("Passwords do not match");
        } else {
            progressDialog.setMessage("Registration in progress...");
            progressDialog.setTitle("Registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.createUserWithEmailAndPassword(inputEmail, inputPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        startActivity(new Intent(sign_up.this, MainActivity.class));
                        //https://www.youtube.com/watch?v=TxpN7uWJyBw -ref

                        Toast.makeText(sign_up.this, "Registration successful!", Toast.LENGTH_SHORT).show();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(sign_up.this, "" + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
