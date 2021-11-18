package com.hashmonopolist.andromix.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hashmonopolist.andromix.R;

public class FragmentItem extends Fragment {
    String title;
    String artist;
    public FragmentItem(String title, String artist) {
        super(R.layout.fragment_item);
        this.title = title;
        this.artist = artist;
    }
    public FragmentItem(String title) {
        super(R.layout.fragment_item);
        this.title = title;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_page, container, false);
        TextView textViewTitle = rootView.findViewById(R.id.textview_title);
        TextView textViewArtist = rootView.findViewById(R.id.textview_artist);
        TextView textViewBy = rootView.findViewById(R.id.textview_by);
        ImageView imageViewCover = rootView.findViewById(R.id.imageview_cover);
        if(artist == null) {
            textViewBy.setVisibility(View.GONE);
            textViewArtist.setVisibility(View.GONE);
        } else textViewArtist.setText(artist);
        textViewTitle.setText(title);
        return rootView;
    }
}
