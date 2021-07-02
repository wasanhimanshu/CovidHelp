package com.example.covidhelp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.covidhelp.R;
import com.example.covidhelp.model.News;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    private Context context;
    private List<News> mNews;
    public NewsAdapter(Context context,List<News> n){
        this.context=context;
        this.mNews=n;
    }
    @NonNull
    @Override
    public NewsAdapter.NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.news_list_item,parent,false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.NewsViewHolder holder, int position) {
final News news=mNews.get(position);
holder.title_tv.setText(news.getmTitle());
holder.desc_tv.setText(news.getmDesc());
holder.date_tv.setText(news.getmDate().substring(0,10));
if(news.getImageUrl()!=null){
       Glide.with(context).load(news.getImageUrl()).into(holder.image_iv);
}else{
    holder.image_iv.setImageResource(R.drawable.ic_launcher_background);
}

holder.itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
     Intent i=new Intent(Intent.ACTION_VIEW);
     i.setData(Uri.parse(news.getUrl()));
     context.startActivity(i);
    }
});
    }

    @Override
    public int getItemCount() {
        return mNews.size();
    }
    public static class NewsViewHolder extends RecyclerView.ViewHolder{
        private TextView title_tv,desc_tv,date_tv;
        private ImageView image_iv;



        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            title_tv=(TextView)itemView.findViewById(R.id.newsli_title_text);
            desc_tv=(TextView)itemView.findViewById(R.id.newsli_desc_text);
            date_tv=(TextView)itemView.findViewById(R.id.newsli_date_text);
            image_iv=(ImageView)itemView.findViewById(R.id.newsli_image);


        }


    }
}
