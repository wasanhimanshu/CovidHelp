package com.example.covidhelp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.covidhelp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class ResetPassWordActivity extends AppCompatActivity {
private TextInputEditText resetEt;
private Button resetBtn;
private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pass_word);


        resetEt=(TextInputEditText)findViewById(R.id.reset_pass);
        resetBtn=(Button)findViewById(R.id.reset_btn);
        mAuth=FirebaseAuth.getInstance();

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email= Objects.requireNonNull(resetEt.getText()).toString().trim();
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    resetEt.setError("Please Enter a valid Email!!");
                    resetEt.setFocusable(true); }
                else{
                  mAuth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                      @Override
                      public void onSuccess(Void aVoid) {
                          Intent i=new Intent(ResetPassWordActivity.this,LoginActivity.class);
                          startActivity(i);
                          Toast.makeText(ResetPassWordActivity.this,"Please Check your Email!!",Toast.LENGTH_SHORT).show();
                          finish();
                      }
                  }).addOnFailureListener(new OnFailureListener() {
                      @Override
                      public void onFailure(@NonNull Exception e) {
                          Toast.makeText(ResetPassWordActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                      }
                  });
                }
            }
        });

    }
}