package com.example.covidhelp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.covidhelp.Adapter.HomeAdapter;
import com.example.covidhelp.R;
import com.example.covidhelp.model.Requests;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyRequestActivity extends AppCompatActivity {
private FirebaseAuth mAuth;
private FirebaseUser mUser;
private DatabaseReference mRef;
private RecyclerView mRecyclerview;
private HomeAdapter mAdapter;
private List<Requests> my_req;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_request);

        ActionBar actionBar=getSupportActionBar();


        assert actionBar != null;
        actionBar.setTitle("My Requests");
        actionBar.setDisplayHomeAsUpEnabled(true);

        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        mRecyclerview=(RecyclerView)findViewById(R.id.my_request_rec_view);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerview.setHasFixedSize(true);
        my_req=new ArrayList<>();
        init();
    }

    private void init() {
        mRef=FirebaseDatabase.getInstance().getReference().child("Requests");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                my_req.clear();
                for(DataSnapshot ds:snapshot.getChildren()){
                    Requests myrequests=ds.getValue(Requests.class);
                    assert myrequests != null;
                    if(myrequests.getUid().equals(mUser.getUid())){
                        my_req.add(myrequests);
                    }
                }
                mAdapter=new HomeAdapter(MyRequestActivity.this,my_req);
                mRecyclerview.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MyRequestActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}