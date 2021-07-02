package com.example.covidhelp.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.covidhelp.R;
import com.example.covidhelp.model.Requests;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class RequestFragment extends Fragment {
    private EditText name_et,phone_et,state_et,hospital_et,desc_et;
    private Spinner req_type,blood_amount,bloodtype;
    private Button req_btn;
    private FirebaseAuth mAuth;
    private DatabaseReference mRef;
    private FirebaseUser mUser;
    public RequestFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.request_fragment,container,false);
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        name_et=(EditText)view.findViewById(R.id.req_name_et);
        phone_et=(EditText)view.findViewById(R.id.req_phone_et);
        state_et=(EditText)view.findViewById(R.id.req_state_et);
        hospital_et=(EditText)view.findViewById(R.id.req_hospital_et);
        desc_et=(EditText)view.findViewById(R.id.req_desc_et);
        req_type=(Spinner)view.findViewById(R.id.request_type);
        blood_amount=(Spinner)view.findViewById(R.id.blood_quant);
        bloodtype=(Spinner)view.findViewById(R.id.blood_type);
        req_btn=(Button)view.findViewById(R.id.request_btn);

        req_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              helper();

            }
        });

        return view;
    }



    private void helper() {
        String name=name_et.getText().toString().trim();
        String phone=phone_et.getText().toString().trim();
        String state=state_et.getText().toString().trim();
        String hospital=hospital_et.getText().toString().trim();
        String desc=desc_et.getText().toString().trim();
        String request_for=req_type.getSelectedItem().toString();
        String amount=blood_amount.getSelectedItem().toString();
        String type=bloodtype.getSelectedItem().toString();

        if(TextUtils.isEmpty(name)){
            Toast.makeText(getContext(),"Please Enter a valid Name",Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(phone)){
            Toast.makeText(getContext(),"Please Enter a valid Phone Number",Toast.LENGTH_SHORT).show();
        }else if(phone.length()!=10){
            Toast.makeText(getContext(),"Please Enter a valid Phone Number",Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(state)){
            Toast.makeText(getContext(),"Please Enter a valid State",Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(hospital)){
            Toast.makeText(getContext(),"Please Enter a valid Hospital Name",Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(desc)){
            Toast.makeText(getContext(),"Please Enter a valid Description",Toast.LENGTH_SHORT).show();
        }else{
          mRef= FirebaseDatabase.getInstance().getReference().child("Requests");
            Requests newRequest=new Requests(name,phone,state,hospital,desc,type,amount,request_for,mUser.getUid());
            DatabaseReference newref=mRef.push();
            newref.setValue(newRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        MakealertBox();
                        empty();
                    }else {
                        Toast.makeText(getContext(),"Request Not Submitted! Please Try again",Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void MakealertBox() {
        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        builder.setTitle("Request Submitted Successfully!");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                empty();
            }
        });
        builder.create().show();
    }
    private void empty(){
        name_et.setText("");
        phone_et.setText("");
        state_et.setText("");
        hospital_et.setText("");
        desc_et.setText("");
    }
}
