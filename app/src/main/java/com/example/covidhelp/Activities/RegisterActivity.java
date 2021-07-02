package com.example.covidhelp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.covidhelp.R;
import com.example.covidhelp.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
private FirebaseAuth mAuth;
private DatabaseReference mRef;
private EditText reg_email_et,reg_name_et,reg_phone_et,reg_pass_et;
private Button reg_btn;
private TextView already_user;
private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        reg_email_et=(EditText)findViewById(R.id.reg_email_et);
        reg_name_et=(EditText)findViewById(R.id.reg_name_et);
        reg_phone_et=(EditText)findViewById(R.id.reg_phone_et);
        reg_pass_et=(EditText)findViewById(R.id.reg_pass_et);
        reg_btn=(Button)findViewById(R.id.reg_btn);
        already_user=(TextView)findViewById(R.id.alreadyuser_text_view);
        mAuth=FirebaseAuth.getInstance();

        already_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=reg_email_et.getText().toString().trim();
                String pass=reg_pass_et.getText().toString().trim();
                String name=reg_name_et.getText().toString().trim();
                String phone=reg_phone_et.getText().toString().trim();
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(RegisterActivity.this,"Please Enter a valid Email",Toast.LENGTH_SHORT).show();
                }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    Toast.makeText(RegisterActivity.this,"Please Enter a valid Email",Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(name)){
                    Toast.makeText(RegisterActivity.this,"Please Enter a valid name",Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(phone)){
                    Toast.makeText(RegisterActivity.this,"Please Enter a valid Phone Number",Toast.LENGTH_SHORT).show();
                }else if(phone.length()!=10){
                    Toast.makeText(RegisterActivity.this,"Please Enter a valid Phone Number",Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(pass)){
                    Toast.makeText(RegisterActivity.this,"Please Enter a valid Password",Toast.LENGTH_SHORT).show();
                }else{
                    registerUser(email,pass,name,phone);
                }

            }
        });
    }

    private void registerUser(String email, String pass, String name, String phone) {
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Registering User");
        progressDialog.setMessage("Please Wait");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser mUser=mAuth.getCurrentUser();
                    assert mUser != null;
                    mRef= FirebaseDatabase.getInstance().getReference().child("Users").child(mUser.getUid()).child("Profile");
                    User newUser=new User(name,email,phone);
                    mRef.setValue(newUser);
                    progressDialog.dismiss();
                    Toast.makeText(RegisterActivity.this,"Welcome!!",Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();


                }else{
                    progressDialog.dismiss();
                    Toast.makeText(RegisterActivity.this,"Process Failed. Please Try again..",Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(RegisterActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

}