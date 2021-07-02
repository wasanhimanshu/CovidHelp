package com.example.covidhelp.Fragment;

import android.content.Context;
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
import android.view.animation.Animation;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covidhelp.Adapter.CovidUpdateAdapter;
import com.example.covidhelp.R;
import com.example.covidhelp.model.covidUpdate;
import com.example.covidhelp.network.ApiClientInstance;
import com.example.covidhelp.network.ApiInterface;
import com.example.covidhelp.network.statewisedata;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateFragment extends Fragment {
    private RecyclerView mRecyclervView;
    private CovidUpdateAdapter mAdapter;
    private ProgressBar mIndicator;
   private TextView empty_text_view;
   private View rootView;
    public UpdateFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.update_fragment, container, false);
        mRecyclervView = (RecyclerView) rootView.findViewById(R.id.update_fragment_recylcer_view);
        mIndicator = (ProgressBar) rootView.findViewById(R.id.update_loading_indicator);
        mIndicator.setVisibility(View.VISIBLE);
        helper();
        return  rootView;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu,menu);
        MenuItem item=menu.findItem(R.id.logout);
        item.setVisible(false);
        MenuItem item1=menu.findItem(R.id.search);
        MenuItem item2=menu.findItem(R.id.my_req);
        item2.setVisible(false);
        SearchView searchView=(SearchView)item1.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(!TextUtils.isEmpty(query)){
                    searchQuery(query);
                }else{
                      helper();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                if(!TextUtils.isEmpty(query)){
                    searchQuery(query);
                }else{
                    helper();
                }
                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    private void searchQuery( final String query) {
        ApiInterface apiInterface = ApiClientInstance.getRetrofitInstance().create(ApiInterface.class);
    apiInterface.getStateByName(query).enqueue(new Callback<statewisedata>() {
        @Override
        public void onResponse(Call<statewisedata> call, Response<statewisedata> response) {
            mRecyclervView.setLayoutManager(new LinearLayoutManager(getContext()));
            mAdapter=new CovidUpdateAdapter(getContext(),response.body().mUpdates);
            mRecyclervView.setAdapter(mAdapter);
        }

        @Override
        public void onFailure(Call<statewisedata> call, Throwable t) {

        }
    });

    }

    private void helper(){
         ApiInterface apiInterface=ApiClientInstance.getRetrofitInstance().create(ApiInterface.class);
         apiInterface.getStatewisedata().enqueue(new Callback<statewisedata>() {
             @Override
             public void onResponse(Call<statewisedata> call, Response<statewisedata> response) {
                 mIndicator.setVisibility(View.GONE);
                 mRecyclervView.setLayoutManager(new LinearLayoutManager(getContext()));
                 mAdapter=new CovidUpdateAdapter(getContext(),response.body().mUpdates);
                 mRecyclervView.setAdapter(mAdapter);
             }

             @Override
             public void onFailure(Call<statewisedata> call, Throwable t) {
             mIndicator.setVisibility(View.GONE);
                 empty_text_view=(TextView)rootView.findViewById(R.id.empty_text_view_update);
                 ConnectivityManager cm =
                         (ConnectivityManager)getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                 NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                 boolean isConnected = activeNetwork != null &&
                         activeNetwork.isConnectedOrConnecting();
                 if(!isConnected){
                     empty_text_view.setText("No Internet Connection!!");
                 }else{
                     empty_text_view.setText("Please Try Again Later!!");
                 }

             }
         });
    }
}
