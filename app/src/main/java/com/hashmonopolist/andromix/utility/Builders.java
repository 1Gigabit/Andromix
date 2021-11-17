package com.hashmonopolist.andromix.utility;

import android.app.Activity;
import android.widget.TableRow;

import com.hashmonopolist.andromix.gson.SearchResults;

import java.util.List;

public class Builders {
    public static void buildTableRowAlbums(SearchResults.Albums albums) {
        List<SearchResults.Albums.Album> albumData = albums.getData();
        for(SearchResults.Albums.Album album: albumData) {

        }
    }
}
