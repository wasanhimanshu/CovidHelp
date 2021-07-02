package com.example.covidhelp.Adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covidhelp.R;
import com.example.covidhelp.model.Requests;

import java.util.List;

public class HomeAdapter  extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {
    private Activity context;
    private List<Requests> mRequests;
    private static final int MY_PERMISSONS_REQUEST_READ_CONTACTS = 1;
    public  HomeAdapter(Activity context, List<Requests>r){
        this.context=context;
        this.mRequests=r;
    }

    @NonNull
    @Override
    public HomeAdapter.HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(context).inflate(R.layout.home_list_item,parent,false);
       return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.HomeViewHolder holder, int position) {
final Requests newReq=mRequests.get(position);
holder.nametv.setText(newReq.getName());
holder.statetv.setText(newReq.getState());
holder.desctv.setText(newReq.getDesc());
holder.blooggrp_tv.setText(newReq.getType());
holder.amount_tv.setText(newReq.getAmount());
holder.hospitaltv.setText(newReq.getHospital());
holder.requesttype_tv.setText(newReq.getRequest_type());

holder.call_btn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        call(newReq.getPhone());
    }
});

holder.itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

    }
});

    }

    @Override
    public int getItemCount() {
       return mRequests.size();
    }
    public  static class HomeViewHolder extends RecyclerView.ViewHolder{
  private TextView nametv,statetv,desctv,hospitaltv,requesttype_tv,amount_tv,blooggrp_tv;
  private Button call_btn;

        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
          nametv=(TextView)itemView.findViewById(R.id.homeli_nametv);

          statetv=(TextView)itemView.findViewById(R.id.homeli_statetv);
          hospitaltv=(TextView)itemView.findViewById(R.id.homeli_hospitaltv);
          desctv=(TextView)itemView.findViewById(R.id.homeli_desctv);
          requesttype_tv=(TextView)itemView.findViewById(R.id.homeli_requestfortv);
          amount_tv=(TextView)itemView.findViewById(R.id.homeli_amounttv);
          blooggrp_tv=(TextView)itemView.findViewById(R.id.homeli_typetv);
          call_btn=(Button)itemView.findViewById(R.id.call_btn);




        }




    }

    private void call(final String phoneString){
        Intent phoneIntent = new Intent(Intent.ACTION_CALL);
        phoneIntent.setData(Uri.parse(context.getString(R.string.tel_colon) + phoneString));
        // Check whether the app has a given permission
        if (ActivityCompat.checkSelfPermission(context,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(context,
                    Manifest.permission.CALL_PHONE)) {

            } else {
                // Request permission to be granted to this application
                ActivityCompat.requestPermissions(context,
                        new String[]{ Manifest.permission.CALL_PHONE},
                        MY_PERMISSONS_REQUEST_READ_CONTACTS);
            }
            return;
        }
        context.startActivity(Intent.createChooser(phoneIntent, context.getString(R.string.make_a_phone_call)));
    }
}
