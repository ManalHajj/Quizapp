package com.example.quizappdmrd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    EditText etLogin,etPassword;
    Button bLogin;
    Button bLoc;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etLogin = (EditText) findViewById(R.id.etLogin);
        etPassword = (EditText) findViewById(R.id.etPassword);
        bLogin = findViewById(R.id.bLogin);
        mAuth = FirebaseAuth.getInstance();
        bLoc=findViewById(R.id.bLoc);

        bLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AcLocation.class));
                finish();
            }
        });

bLogin.setOnClickListener(view -> {
    loginUser();
});

        TextView textView = findViewById(R.id.tvRegister);
        String text = "to register click here !";
        SpannableString ss = new SpannableString(text);
        ClickableSpan clickableSpan1=new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                Intent intent = new Intent(MainActivity.this,activity_register.class);
                startActivity(intent);
            }
        };
        ss.setSpan(clickableSpan1,18,22, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(ss);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }

private void loginUser(){
    String email = etLogin.getText().toString();
    String password = etPassword.getText().toString();

    if(TextUtils.isEmpty(email)){
        etLogin.setError("Email cannot be empty");
        etLogin.requestFocus();
    }else if (TextUtils.isEmpty(password)){
        etPassword.setError("Password cannot be empty");
        etPassword.requestFocus();
    }else{
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(MainActivity.this, "User logged in succesfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this,activity_question.class));
                }else {
                    Toast.makeText(MainActivity.this, "Login error : " +task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
    }
