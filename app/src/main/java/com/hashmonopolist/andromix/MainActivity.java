package com.hashmonopolist.andromix;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.PathDashPathEffect;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.hashmonopolist.andromix.fragments.FragmentPage;
import com.hashmonopolist.andromix.gson.SearchResults;

import java.util.List;

public class MainActivity extends AppCompatActivity {
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
        });

        // Side swiping
        ViewPager2 viewPager = findViewById(R.id.viewpager);
        TabLayout tabLayout = findViewById(R.id.tablayout);

        PageFragmentStateAdapter adapter = new PageFragmentStateAdapter(getSupportFragmentManager(),getLifecycle());
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout,viewPager, (tab,position) -> {
            String[] titles = new String[]{"Track","Album","Artist"};
            tabLayout.selectTab(tab.setText(titles[position]));
        }).attach();
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
        // Search
        MenuItem searchViewItem = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) searchViewItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                search(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    public class PageFragmentStateAdapter extends FragmentStateAdapter {

        public PageFragmentStateAdapter(FragmentManager fragment, Lifecycle lifecycle) {
            super(fragment,lifecycle);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position){
                case 1:

                    return new FragmentPage("Testing 1");
                case 2:
                    return new FragmentPage("Testing 2");
                case 3:
                    return new FragmentPage("Testing 3");
                default:
                    return new FragmentPage("a");
            }
        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }
    public void search(String search) {
        api.mainSearch(search, (searchResults) -> {
            List<SearchResults.Albums.Album> data = searchResults.getALBUM().getData();
            for(SearchResults.Albums.Album album: data) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(new Fragment
            }
        });
    }
}


