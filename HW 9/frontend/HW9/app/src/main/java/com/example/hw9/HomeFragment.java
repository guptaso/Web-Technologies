package com.example.hw9;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    public HomeFragment(){
        super(R.layout.fragment_home);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        // Movie Button
        Button movieB = v.findViewById(R.id.movieBtn);
        Button tvB = v.findViewById(R.id.tvBtn);

        // start with movie fragment
        Fragment selectedFragment = new MovieFragment();
        getFragmentManager().beginTransaction().replace(R.id.movie_tv_fragment, selectedFragment).commit();


        movieB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (movieB.getCurrentTextColor() != -1){
                    movieB.setTextColor(Color.parseColor("#FFFFFF"));
                    tvB.setTextColor(Color.parseColor("#156EB4"));
                    // Show Movie Fragment
                    Fragment selectedFragment = new MovieFragment();
                    getFragmentManager().beginTransaction().replace(R.id.movie_tv_fragment, selectedFragment).commit();
                }
            }
        });

        tvB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tvB.getCurrentTextColor() != -1){
                    movieB.setTextColor(Color.parseColor("#156EB4"));
                    tvB.setTextColor(Color.parseColor("#FFFFFF"));
                    // Show TV Fragment
                    Fragment selectedFragment = new TVFragment();
                    getFragmentManager().beginTransaction().replace(R.id.movie_tv_fragment, selectedFragment).commit();
                }
            }
        });

        return v;
    }
}
