package com.hashmonopolist.andromix.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hashmonopolist.andromix.R;

public class FragmentPage extends Fragment {
    String text;
    public FragmentPage(String text) {
        super(R.layout.fragment_page);
        this.text = text;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_page, container, false);
        TextView textView = (TextView) rootView.findViewById(R.id.textView);
        textView.setText(text);
        return rootView;
    }
}
