package com.example.covidhelp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.covidhelp.Fragment.HomeFragment;
import com.example.covidhelp.Fragment.NewsFragment;
import com.example.covidhelp.Fragment.ProfileFragment;
import com.example.covidhelp.Fragment.RequestFragment;
import com.example.covidhelp.Fragment.UpdateFragment;
import com.example.covidhelp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bottomNavigationView=(BottomNavigationView)findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListner);
        getSupportFragmentManager().beginTransaction().replace(R.id.frag_container,new HomeFragment()).commit();

    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListner=new BottomNavigationView.OnNavigationItemSelectedListener() {
        Fragment selected_frag=null;
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int id=item.getItemId();
            switch (id){
                case R.id.home1:
                   selected_frag=new HomeFragment();
                   break;
                case R.id.request:
                    selected_frag=new RequestFragment();
                    break;
                case R.id.update:
                    selected_frag=new UpdateFragment();
                    break;
                case R.id.news:
                    selected_frag=new NewsFragment();
                    break;
                case R.id.profile:
                    selected_frag=new ProfileFragment();
                    break;

            }
            getSupportFragmentManager().beginTransaction().replace(R.id.frag_container,selected_frag).commit();
            return true;
        }
    };

}