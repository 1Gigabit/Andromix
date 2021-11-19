package com.hashmonopolist.andromix;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.tabs.TabLayout;
import com.hashmonopolist.andromix.gson.SearchResults;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {
    API api;
    SearchResults searchResults;
    LinearLayout albumLayout;
    LinearLayout artistLayout;
    LinearLayout trackLayout;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("");

        this.api = new API(BuildConfig.DEEMIX_SERVER, getCacheDir());

        Toast.makeText(this, "Logging in", Toast.LENGTH_SHORT).show();

        //Log in
        this.api.loginARL(BuildConfig.DEEMIX_ARL, v -> {
            runOnUiThread(() -> Toast.makeText(MainActivity.this, "Logged in!", Toast.LENGTH_SHORT).show());
        });

        //Tabs
        /*
        Side swiping not supported with ViewPager so not implementing it.
         */
        this.tabLayout = findViewById(R.id.tablayout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabLayout.selectTab(tab);
                LinearLayout pageLayout = findViewById(R.id.linearlayout_page);
                pageLayout.removeAllViewsInLayout();
                switch(tab.getText().toString()) {
                    case "Album":
                        if(albumLayout == null) break;
                        pageLayout.addView(albumLayout);
                        break;
                    case "Artist":
                        if(artistLayout == null) break;
                        pageLayout.addView(artistLayout);
                        break;
                    case "Track":
                        if(trackLayout == null) break;
                        pageLayout.addView(trackLayout);

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
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
                Toast.makeText(MainActivity.this,"Searching...",Toast.LENGTH_SHORT).show();
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

    public void search(String search) {
        api.mainSearch(search, (searchResults) -> {
            this.searchResults = searchResults;
            LayoutInflater layoutInflater = getLayoutInflater();
            albumLayout = (LinearLayout) layoutInflater.inflate(R.layout.layout_page,null);
            artistLayout = (LinearLayout) layoutInflater.inflate(R.layout.layout_page,null);
            trackLayout = (LinearLayout) layoutInflater.inflate(R.layout.layout_page,null);
            AlertDialog.Builder confirmDownloadDialog = new AlertDialog.Builder(this)
                    .setTitle("Are you sure")
                    .setNegativeButton("No", (dialog, which) -> {});
            for (SearchResults.Albums.Album data : searchResults.getALBUM().getData()) {
                LinearLayout item = (LinearLayout) layoutInflater.inflate(R.layout.layout_item, null);
                item.setOnClickListener(l -> {
                    confirmDownloadDialog.setMessage("Are you sure you want to download " + data.getALB_TITLE())
                            .setPositiveButton("Yes", (dialog, which) -> {
                                Toast.makeText(MainActivity.this, "Downloading...", Toast.LENGTH_SHORT).show();
                                api.addToQueue(data.getALB_ID(),"album",(response) -> {
                                    Toast.makeText(MainActivity.this, "Download request sent!", Toast.LENGTH_SHORT).show();
                                });
                            })
                            .create()
                            .show();
                });
                ((TextView) item.findViewById(R.id.textview_title)).setText(data.getALB_TITLE());
                ((TextView) item.findViewById(R.id.textview_artist)).setText(data.getALB_TITLE());
                Picasso.get().load(data.getALB_PICTURE()).into((ImageView) item.findViewById(R.id.imageview_cover));
                albumLayout.addView(item);
            }
            for (SearchResults.Artists.Artist data : searchResults.getARTIST().getData()) {
                LinearLayout item = (LinearLayout) layoutInflater.inflate(R.layout.layout_item, null);
                item.setOnClickListener(l -> {
                    confirmDownloadDialog.setMessage("Are you sure you want to download " + data.getART_NAME())
                            .setPositiveButton("Yes", (dialog, which) -> {
                                Toast.makeText(MainActivity.this, "Downloading...", Toast.LENGTH_SHORT).show();
                                api.addToQueue(data.getART_ID(),"artist",(response) -> {
                                    Toast.makeText(MainActivity.this, "Download request sent!", Toast.LENGTH_SHORT).show();
                                });
                            })
                            .create()
                            .show();
                });
                ((TextView) item.findViewById(R.id.textview_title)).setText(data.getART_NAME());
                ((TextView) item.findViewById(R.id.textview_by)).setVisibility(View.GONE);
                ((TextView) item.findViewById(R.id.textview_artist)).setVisibility(View.GONE);
                Picasso.get().load(data.getART_PICTURE()).into((ImageView) item.findViewById(R.id.imageview_cover));
                artistLayout.addView(item);
            }
            for (SearchResults.Tracks.Track data : searchResults.getTRACK().getData()) {
                LinearLayout item = (LinearLayout) layoutInflater.inflate(R.layout.layout_item, null);
                item.setOnClickListener(l -> {
                    confirmDownloadDialog.setMessage("Are you sure you want to download " + data.getART_NAME())
                            .setPositiveButton("Yes", (dialog, which) -> {
                                Toast.makeText(MainActivity.this, "Downloading...", Toast.LENGTH_SHORT).show();
                                api.addToQueue(data.getSNG_ID(),"track",(response) -> {
                                    Toast.makeText(MainActivity.this, "Download request sent!", Toast.LENGTH_SHORT).show();
                                });
                            })
                            .create()
                            .show();
                });
                ((TextView) item.findViewById(R.id.textview_title)).setText(data.getSNG_TITLE());
                ((TextView) item.findViewById(R.id.textview_artist)).setText(data.getART_NAME());
                Picasso.get().load(data.getALB_PICTURE()).into((ImageView) item.findViewById(R.id.imageview_cover));
                trackLayout.addView(item);
            }
            TabLayout.Tab tab = tabLayout.getTabAt(tabLayout.getSelectedTabPosition());
            if(tabLayout.getSelectedTabPosition() == 0) {
                tabLayout.selectTab(tabLayout.getTabAt(1));
                tabLayout.selectTab(tab);
            }
            if(tabLayout.getSelectedTabPosition() == 1) {
                tabLayout.selectTab(tabLayout.getTabAt(2));
                tabLayout.selectTab(tab);
            }
            if(tabLayout.getSelectedTabPosition() == 2) {
                tabLayout.selectTab(tabLayout.getTabAt(1));
                tabLayout.selectTab(tab);
            }
            Toast.makeText(MainActivity.this,"Search completed",Toast.LENGTH_SHORT).show();
        });
    }
}


