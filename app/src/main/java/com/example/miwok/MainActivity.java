package com.example.miwok;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
//import android.support.design.widget.TabLayout;
import com.google.android.material.tabs.TabLayout;
//import com.google.android.material.view.ViewPager;

import androidx.viewpager.widget.ViewPager;
//import android.support.v7.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        CategoryAdapter adapter = new CategoryAdapter(this, getSupportFragmentManager());

        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        tabLayout.setupWithViewPager(viewPager);
    }
}
