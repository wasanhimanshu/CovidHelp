package com.example.covidhelp.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covidhelp.Adapter.NewsAdapter;
import com.example.covidhelp.R;
import com.example.covidhelp.model.News;
import com.example.covidhelp.model.ResponseModel;
import com.example.covidhelp.network.ApiInterface;
import com.example.covidhelp.network.RetofitClientInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsFragment extends Fragment {
    private RecyclerView mRecyclerview;
    private NewsAdapter mAdapter;
private ProgressBar mIndiactor;
    public NewsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_fragment, container, false);
        mRecyclerview = (RecyclerView) view.findViewById(R.id.news_recycler_view);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        mIndiactor=(ProgressBar)view.findViewById(R.id.new_loading_indicator);
        mRecyclerview.setHasFixedSize(true);
         ApiInterface apiInterface = RetofitClientInstance.getRetrofitInstane().create(ApiInterface.class);
         apiInterface.getAllnews().enqueue(new Callback<ResponseModel>() {
             @Override
             public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                 if(response.body().getStatus().equals("ok")){
                     List<News> mNews=response.body().getArticles();
                     if(mNews.size()>0){
                         mIndiactor.setVisibility(View.GONE);
                         mAdapter=new NewsAdapter(getContext(),mNews);
                         mRecyclerview.setAdapter(mAdapter);
                     }
                 }else{
                     mIndiactor.setVisibility(View.GONE);
                     Toast.makeText(getContext(),"null",Toast.LENGTH_SHORT).show();
                 }
             }

             @Override
             public void onFailure(Call<ResponseModel> call, Throwable t) {
                 mIndiactor.setVisibility(View.GONE);
                 Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
             }
         });
        return view;

    }
}
