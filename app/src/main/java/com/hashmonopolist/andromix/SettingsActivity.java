package com.hashmonopolist.andromix;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.hashmonopolist.andromix.utility.Settings;

import java.io.File;
import java.util.Objects;

public class SettingsActivity extends Activity {
    Settings settings;
    TextInputEditText textInputARL;
    TextInputEditText textInputServer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        this.settings = new Settings(new File(getFilesDir(),"settings.json"));

        this.textInputARL = findViewById(R.id.edittext_arl);
        this.textInputServer = findViewById(R.id.edittext_server);
        Button button_cancel = findViewById(R.id.settings_button_cancel);
        Button button_save = findViewById(R.id.settings_button_save);

        updateTextFields();

        button_cancel.setOnClickListener(l -> finish());
        button_save.setOnClickListener(l -> {
            updateFromTextFields();
            settings.saveToFile();
            new MaterialAlertDialogBuilder(this)
                    .setTitle("Successfully saved")
                    .setPositiveButton("Okay", (dialog, which) -> {
                        finish();
                    }).create().show();
        });
    }

    public void updateTextFields() {
        settings.loadFromFile();
//        System.out.println(settings.toString());
        this.textInputARL.setText(settings.getArl());
        this.textInputServer.setText(settings.getServer());
    }
    public void updateFromTextFields() {
        settings.setServer(Objects.requireNonNull(this.textInputServer.getText()).toString());
        settings.setArl(Objects.requireNonNull(this.textInputARL.getText()).toString());
        System.out.println(settings.toString());

        settings.saveToFile();
    }
}
