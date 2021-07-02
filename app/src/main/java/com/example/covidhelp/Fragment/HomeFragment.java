package com.example.covidhelp.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covidhelp.Activities.MyRequestActivity;
import com.example.covidhelp.Adapter.HomeAdapter;
import com.example.covidhelp.Activities.LoginActivity;
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
import java.util.Objects;


public class HomeFragment extends Fragment {
    private FirebaseAuth mAuth;
    private RecyclerView mRecyclerView;
    private HomeAdapter mAdapter;
    private DatabaseReference mRef;
    private List<Requests>mRequests;
    private ProgressBar mIndicator;
    private TextView emptyTextView;
    private FirebaseUser mUser;
    public HomeFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.home_fragment,container,false);
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        mRecyclerView=(RecyclerView)view.findViewById(R.id.home_recycler_view);
        mIndicator=(ProgressBar)view.findViewById(R.id.home_loading_indicator);
        emptyTextView=(TextView)view.findViewById(R.id.home_empty_text_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);
        mRequests=new ArrayList<>();
        init();
        return view;
    }

    private void init() {
        mRef= FirebaseDatabase.getInstance().getReference().child("Requests");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mRequests.clear();
                for(DataSnapshot  ds:snapshot.getChildren()){
                    Requests newReq= ds.getValue(Requests.class);
                    mRequests.add(newReq);
                }
                mIndicator.setVisibility(View.GONE);
                    mAdapter = new HomeAdapter(getActivity(), mRequests);
                    mRecyclerView.setAdapter(mAdapter);
                    emptyTextView.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                mIndicator.setVisibility(View.GONE);
                ConnectivityManager cm=(ConnectivityManager)getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activenetwork=cm.getActiveNetworkInfo();
                boolean isconnected = activenetwork.isConnectedOrConnecting();
                if(!isconnected){
                    emptyTextView.setText("No Internet");
                    emptyTextView.setVisibility(View.VISIBLE);
                    mRecyclerView.setVisibility(View.INVISIBLE);
                }
            }
        });

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu,menu);
        MenuItem item=menu.findItem(R.id.search);
        androidx.appcompat.widget.SearchView searchView =(androidx.appcompat.widget.SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(!TextUtils.isEmpty(query)){
                    searchRequest(query);
                }else{
                    init();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                if(!TextUtils.isEmpty(query)){
                    searchRequest(query);
                }else{
                    init();
                }
                return false;
            }
        });


        super.onCreateOptionsMenu(menu, inflater);
    }

    private void searchRequest( final String query) {
        mRef=FirebaseDatabase.getInstance().getReference().child("Requests");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mRequests.clear();
                for(DataSnapshot ds:snapshot.getChildren()){
                    Requests newRew=ds.getValue(Requests.class);
                    assert newRew != null;
                    if(newRew.getState().toLowerCase().equals(query.toLowerCase())){
                        mRequests.add(newRew);
                    }
                }
                if(mRequests.size()>0) {
                    mAdapter = new HomeAdapter(getActivity(), mRequests);
                    mRecyclerView.setAdapter(mAdapter);
                    mRecyclerView.setVisibility(View.VISIBLE);
                    emptyTextView.setVisibility(View.INVISIBLE);
                }else{
                    emptyTextView.setText("No Request from this region");
                    emptyTextView.setVisibility(View.VISIBLE);
                    mRecyclerView.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.logout){
            mAuth.signOut();
            Intent i=new Intent(getContext(), LoginActivity.class);
            Objects.requireNonNull(getContext()).startActivity(i);
            getActivity().finish();
        }else if(id==R.id.my_req){
            Intent i=new Intent(getActivity(), MyRequestActivity.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
}
