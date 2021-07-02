package com.example.covidhelp.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.telephony.ims.RcsUceAdapter;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covidhelp.R;
import com.example.covidhelp.model.covidUpdate;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CovidUpdateAdapter extends RecyclerView.Adapter<CovidUpdateAdapter.covidUpdateViewHolder> {
    private Context context;
    private List<covidUpdate>mUpdates=new ArrayList<>();
    public CovidUpdateAdapter(Context context,List<covidUpdate>m){
        this.context=context;
        this.mUpdates=m;
    }
    @NonNull
    @Override
    public CovidUpdateAdapter.covidUpdateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view= LayoutInflater.from(context).inflate(R.layout.covidupdate_list_item,parent,false);
      return new covidUpdateViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull CovidUpdateAdapter.covidUpdateViewHolder holder, int position) {
final covidUpdate newupdates=mUpdates.get(position);
holder.statename_tv.setText(newupdates.getState());
holder.confirm_tv.setText(newupdates.getConfirmed());
holder.active_tv.setText(newupdates.getActive());
holder.deceased_tv.setText(newupdates.getDeaths());
holder.recovered_tv.setText(newupdates.getRecovered());

        Date currentDate = new Date();
        SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date updatedate= null;

        try {
            updatedate = dateFormat.parse(newupdates.getLastupdatedtime());
        } catch ( ParseException e) {
            e.printStackTrace();
        }

        Long sec= TimeUnit.MILLISECONDS.toSeconds(currentDate.getTime()-updatedate.getTime());
        Long min=TimeUnit.MILLISECONDS.toMinutes(currentDate.getTime()-updatedate.getTime());
        Long hr=TimeUnit.MILLISECONDS.toHours(currentDate.getTime()-updatedate.getTime());

        if(sec<60){
            holder.lastupdated_tv.setText("Few seconds ago");
        }
        else if(min<60)
        {
            holder.lastupdated_tv.setText(min+" minutes ago");
        }
        else if (hr<24)
        {
            holder.lastupdated_tv.setText((min/60)+" hours ago");
        }
        else
        {
            holder.lastupdated_tv.setText((DateFormat.format("dd/MM/yyyy",updatedate).toString()));
        }


    }

    @Override
    public int getItemCount() {
        return mUpdates.size();
    }

    public static class covidUpdateViewHolder extends RecyclerView.ViewHolder{
private TextView statename_tv,confirm_tv,active_tv,deceased_tv,lastupdated_tv,recovered_tv;

        public covidUpdateViewHolder(@NonNull View itemView) {
            super(itemView);
            statename_tv=(TextView)itemView.findViewById(R.id.statename_tv);
            active_tv=(TextView)itemView.findViewById(R.id.statewise_active_tvstats);
            confirm_tv=(TextView)itemView.findViewById(R.id.statewise_confirmed_tvstats);
            deceased_tv=(TextView)itemView.findViewById(R.id.statewise_deceased_tvstats);
            lastupdated_tv=(TextView)itemView.findViewById(R.id.statewise_lastupdated_tvstats);
            recovered_tv=(TextView)itemView.findViewById(R.id.statewise_recovered_tvstats);


        }
    }
}
