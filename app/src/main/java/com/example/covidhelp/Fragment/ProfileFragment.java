package com.example.covidhelp.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.covidhelp.R;
import com.example.covidhelp.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class ProfileFragment  extends Fragment {
    private TextView name_tv,email_tv,mobile_tv;
    private FirebaseAuth mAuth;
    private DatabaseReference mRef;
    private FirebaseUser mUser;
    private Button mwhobtn;
    private LinearLayout namell,emailll,phonell;
    public ProfileFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.profile_fragment,container,false);
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        name_tv=(TextView)view.findViewById(R.id.profile_nametv);
        email_tv=(TextView)view.findViewById(R.id.profile_emailtv);
        mobile_tv=(TextView)view.findViewById(R.id.profile_phonetv);
        namell=(LinearLayout)view.findViewById(R.id.name_reset);
        emailll=(LinearLayout)view.findViewById(R.id.email_reset);
        phonell=(LinearLayout)view.findViewById(R.id.phone_reset);
        mwhobtn=(Button)view.findViewById(R.id.visitwhobtn);
        init();

        namell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit();
                init();
            }
        });
        phonell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
phoneedit();
            }
        });

        emailll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"You cannot change your Email Id..",Toast.LENGTH_SHORT).show();
            }
        });

        mwhobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Intent.ACTION_VIEW, Uri.parse( "https://www.who.int/"));
                startActivity(i);
            }
        });

        return view;
    }

    private void phoneedit() {
        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        builder.setTitle("Update Mobile");
        final EditText editText=new EditText(getContext());
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        editText.setLayoutParams(layoutParams);
        builder.setView(editText);
        editText.setHint("Enter Number");
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mRef=FirebaseDatabase.getInstance().getReference().child("Users").child(mUser.getUid()).child("Profile");
                mRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User curruser=snapshot.getValue(User.class);
                        String nameupdate=editText.getText().toString().trim();
                        if(TextUtils.isEmpty(nameupdate)){
                            Toast.makeText(getContext(),"Please Enter a valid Number",Toast.LENGTH_SHORT).show();
                        }else if(nameupdate.length()!=10){
                            Toast.makeText(getContext(),"Please Enter a valid Number",Toast.LENGTH_SHORT).show();
                        } else{
                            mRef.child("mPhone").setValue(nameupdate).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()) {
                                        Toast.makeText(getContext(), "Number Updated Successfully!", Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(getContext(), "Number  not Updated Successfully!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.create().show();

    }

    private void edit() {
        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        builder.setTitle("Update Name");
        final EditText editText=new EditText(getContext());
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        editText.setLayoutParams(layoutParams);
        builder.setView(editText);
        editText.setHint("Enter name");
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mRef=FirebaseDatabase.getInstance().getReference().child("Users").child(mUser.getUid()).child("Profile");
                mRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User curruser=snapshot.getValue(User.class);
                        String nameupdate=editText.getText().toString().trim();
                        if(TextUtils.isEmpty(nameupdate)){
                            Toast.makeText(getContext(),"Please Enter a valid Name",Toast.LENGTH_SHORT).show();
                        } else{
                           mRef.child("mName").setValue(nameupdate).addOnCompleteListener(new OnCompleteListener<Void>() {
                               @Override
                               public void onComplete(@NonNull Task<Void> task) {
                                   if(task.isSuccessful()) {
                                       Toast.makeText(getContext(), "Name Updated Successfully!", Toast.LENGTH_SHORT).show();
                                   }else{
                                       Toast.makeText(getContext(), "Name  not Updated Successfully!", Toast.LENGTH_SHORT).show();
                                   }
                               }
                           });
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.create().show();


    }

    private void init() {
        mRef=FirebaseDatabase.getInstance().getReference().child("Users").child(mUser.getUid()).child("Profile");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User newUser=snapshot.getValue(User.class);
                name_tv.setText(newUser.getmName());
                mobile_tv.setText(newUser.getmPhone());
                email_tv.setText(newUser.getmEmail());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}
