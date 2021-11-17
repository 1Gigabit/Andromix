package com.hashmonopolist.andromix;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.hashmonopolist.andromix.fragments.ItemFragment;

public class MainActivity extends AppCompatActivity {
    String cookie;
    API api;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("");

        this.api = new API(BuildConfig.DEEMIX_SERVER, getCacheDir());

        Toast.makeText(this, "Logging in", Toast.LENGTH_SHORT).show();

        //Log in
        this.api.loginARL(BuildConfig.DEEMIX_ARL, v -> {
            this.cookie = v.headers.get("Set-Cookie");
            ContextCompat.getMainExecutor(this).execute(() -> {
                Toast.makeText(this, "Logged in!", Toast.LENGTH_SHORT).show();
            });
            System.out.println(cookie);
        });


        TabLayout tabLayout = findViewById(R.id.tablayout);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_container_view,new Fragment(R.layout.fragment_item));
        transaction.add(R.id.fragment_container_view,new Fragment(R.layout.fragment_item));
        transaction.add(R.id.fragment_container_view,new Fragment(R.layout.fragment_item));
        transaction.add(R.id.fragment_container_view,new Fragment(R.layout.fragment_item));
        tra

        //Tab selection
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabLayout.selectTab(tab);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        new AlertDialog.Builder(this)
                .setTitle("Confirm download")
                .setMessage("Are you sure you want to download?\n\n" +
                        "Album: "+"\n" +
                        "By: "+"\n")
                .setPositiveButton("Okay", (dialog, which) -> {

                })
                .setNegativeButton("Cancel", (dialog, which) -> {

                })
                .create()
                .show();
    }
        @SuppressLint("NonConstantResourceId")
        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            if (item.getItemId() == R.id.app_bar_settings) {
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            }
            return super.onOptionsItemSelected(item);
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

}


