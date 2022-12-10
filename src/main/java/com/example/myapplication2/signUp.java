package com.example.myapplication2;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class signUp extends AppCompatActivity {
    EditText username;
    EditText btemail;
    EditText btpassword;
    TextView tvLoginHere;
    MaterialButton btnRegister;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        username=findViewById(R.id.edtUsername);
        btemail = findViewById(R.id.edtEmail);
        btpassword = findViewById(R.id.edtPassword);
        tvLoginHere = findViewById(R.id.txtLogin);
        btnRegister = findViewById(R.id.btnSignUp);

        mAuth = FirebaseAuth.getInstance();

        btnRegister.setOnClickListener(view ->{
            createUser();
        });

        tvLoginHere.setOnClickListener(view ->{
            startActivity(new Intent(signUp.this, login.class));
        });
    }

    private void createUser() {
        String nameuser = username.getText().toString();
        String email = btemail.getText().toString();
        String password = btpassword.getText().toString();

        if (TextUtils.isEmpty(email)) {
            btemail.setError("Email cannot be empty");
            btemail.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            btpassword.setError("Password cannot be empty");
            btpassword.requestFocus();
        } else if (TextUtils.isEmpty(nameuser)){
            username.setError("Password cannot be empty");
            username.requestFocus();
        }else{
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(signUp.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(signUp.this, login.class));
                    }else{
                        Toast.makeText(signUp.this, "Registration Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

}