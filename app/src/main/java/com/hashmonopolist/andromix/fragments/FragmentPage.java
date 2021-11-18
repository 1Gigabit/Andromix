package com.hashmonopolist.andromix.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hashmonopolist.andromix.R;
import com.hashmonopolist.andromix.gson.SearchResults;

import java.util.List;

public class FragmentPage extends Fragment {
    List<TableRow> tableRows;
    public FragmentPage(String text) {
        super(R.layout.fragment_page);
//        this.text = text;
    }

    public FragmentPage(List<SearchResults.Albums.Album> data) {
        super(R.layout.fragment_page);
        this.tableRows = tableRows;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_page, container, false);
        TableLayout tableLayout = (TableLayout) rootView.findViewById(R.id.tablayout);
        for(TableRow tableRow: tableRows) {
            tableLayout.addView(tableRow);
        }
        return rootView;
    }
}
