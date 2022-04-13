package com.example.quizappdmrd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class activity_register extends AppCompatActivity {
    EditText etLogin,etPassword;
    Button bLogin;

    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etLogin = (EditText) findViewById(R.id.etLogin);
        etPassword = (EditText) findViewById(R.id.etPassword);
        bLogin = findViewById(R.id.bLogin);

        mAuth = FirebaseAuth.getInstance();

        bLogin.setOnClickListener(view -> {
            createUser();
        });
    }
        private void createUser(){
            String email = etLogin.getText().toString();
            String password = etPassword.getText().toString();

            if(TextUtils.isEmpty(email)){
                etLogin.setError("Email cannot be empty");
                etLogin.requestFocus();
            }else if (TextUtils.isEmpty(password)){
                etPassword.setError("Password cannot be empty");
                etPassword.requestFocus();
            }else{
                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
           //if created
                        if(task.isSuccessful()){
                            Toast.makeText(activity_register.this, "User registered succesfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(activity_register.this,MainActivity.class));

                        }else{
                            Toast.makeText(activity_register.this, "Registriation error : " +task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }


        }

}