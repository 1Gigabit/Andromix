package com.hashmonopolist.andromix;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.util.Objects;

public class SettingsActivity extends Activity {
    TextInputEditText textInputARL;
    TextInputEditText textInputServer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        this.textInputARL = findViewById(R.id.edittext_arl);
        this.textInputServer = findViewById(R.id.edittext_server);
        Button button_cancel = findViewById(R.id.settings_button_cancel);
        Button button_save = findViewById(R.id.settings_button_save);

        updateFromStorage();

        button_cancel.setOnClickListener(l -> finish());
        button_save.setOnClickListener(l -> {
            saveToStorage();

            new MaterialAlertDialogBuilder(this)
                    .setTitle("Successfully saved")
                    .setPositiveButton("Okay", (dialog, which) -> {
                        finish();
                    }).create().show();
        });
    }

    public void updateFromStorage() {
        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        this.textInputARL.setText(sharedPreferences.getString(getString(R.string.saved_arl), getResources().getString(R.string.saved_arl)));
        this.textInputServer.setText(sharedPreferences.getString(getString(R.string.saved_server),getResources().getString(R.string.saved_server)));
    }
    public void saveToStorage() {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.saved_arl), Objects.requireNonNull(this.textInputARL.getText()).toString());
        editor.putString(getString(R.string.saved_server), Objects.requireNonNull(this.textInputServer.getText()).toString());
        editor.apply();
    }
}
