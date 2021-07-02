package com.example.covidhelp.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.covidhelp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
private EditText emailet,passet;
private Button login_btn;
private TextView forgot_pass,new_user;
private FirebaseAuth mAuth;
private FirebaseUser mUser;
private ProgressDialog progressDialog;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailet=(EditText)findViewById(R.id.login_email_et);
        passet=(EditText)findViewById(R.id.login_pass_et);
        login_btn=(Button)findViewById(R.id.login_btn);
        forgot_pass=(TextView)findViewById(R.id.forgot_text_view);
        new_user=(TextView)findViewById(R.id.newuser_text_view);
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();


        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=emailet.getText().toString().trim();
                String pass=passet.getText().toString().trim();
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(LoginActivity.this,"Please Enter a valid Email",Toast.LENGTH_SHORT).show();
                }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    Toast.makeText(LoginActivity.this,"Please Enter a valid Email",Toast.LENGTH_SHORT).show();
                } else if(TextUtils.isEmpty(pass)){
                    Toast.makeText(LoginActivity.this,"Please Enter a valid Password",Toast.LENGTH_SHORT).show();
                }else{
                    LoginUser(email,pass);
                }
            }
        });

        forgot_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent i=new Intent(LoginActivity.this,ResetPassWordActivity.class);
              startActivity(i);
              finish();
            }
        });
        new_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);

            }
        });

    }

    private void LoginUser(String email, String pass) {
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Loggin In");
        progressDialog.setMessage("Please Wait");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressDialog.dismiss();
                    Intent i=new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(i);
                    Toast.makeText(LoginActivity.this,"Welcome!!",Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this,"Some Error Occured PleaseTry again!",Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mUser!=null){
            Intent i=new Intent(LoginActivity.this,MainActivity.class);
            startActivity(i);
            finish();
        }

    }

}