package com.itha.caloriescounter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Authentication extends AppCompatActivity {


    private FirebaseAuth auth;
    private AppCompatButton button;
    private EditText email,password,name,age,weight,height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        auth = FirebaseAuth.getInstance();
        initUI();
    }

    private void initUI()
    {
        email = findViewById(R.id.edt_register_email);
        password = findViewById(R.id.edt_register_password);
        name = findViewById(R.id.edt_full_name);
        age = findViewById(R.id.edt_age);
        weight = findViewById(R.id.edt_weight_in_kg);
        height = findViewById(R.id.edt_height);
        button = findViewById(R.id.btn_login);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });
    }

    private void registerUser()
    {
        auth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
           if (task.isSuccessful())
           {
               startActivity(new Intent(Authentication.this,NavigationActivity.class));
           }    else
               {
                   Toast.makeText(Authentication.this, "Error, Try Again", Toast.LENGTH_SHORT).show();
               }
            }
        });
    }
}
