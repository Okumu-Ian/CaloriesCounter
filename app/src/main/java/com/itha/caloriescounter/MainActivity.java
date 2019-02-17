package com.itha.caloriescounter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import models.UserModel;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private AppCompatButton button;
    private EditText email,password,name,age,weight,height;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //check whether user is already signed in
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser()!=null)
        {
            startActivity(new Intent(MainActivity.this,NavigationActivity.class));
        }
        initUI();

    }



    private void initUI()
    {
        reference = FirebaseDatabase.getInstance().getReference("users");
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
                try {
                    registerUser();
                    String userId = reference.push().getKey();
                    //add firebase key
                    SharedPreferences.Editor editor = getSharedPreferences("FIREBASE_KEY", Context.MODE_PRIVATE).edit();
                    editor.putString("UserId",userId);
                    editor.apply();
                    editor.clear();
                    UserModel model = new UserModel(name.getText().toString(),age.getText().toString(),weight.getText().toString(),height.getText().toString());
                    reference.child(userId).setValue(model);
                }
                catch (NullPointerException exception)
                {
                    Toast.makeText(MainActivity.this, "Kindly Fill in all the details!", Toast.LENGTH_LONG).show();
                }

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
                            startActivity(new Intent(MainActivity.this,NavigationActivity.class));
                        }    else
                        {
                            Toast.makeText(MainActivity.this, "Error, Try Again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
