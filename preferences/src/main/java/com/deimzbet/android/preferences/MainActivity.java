package com.deimzbet.android.preferences;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String PREF_TEXT = "com.deimzbet.android.preferences.pref_text";

    private EditText valueField;

    private SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        valueField = findViewById(R.id.et_value);
        Button saveButton = findViewById(R.id.btn_save);
        Button loadButton = findViewById(R.id.btn_load);

        saveButton.setOnClickListener(this);
        loadButton.setOnClickListener(this);

        loadPrefText();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        savePrefText();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_save: {
                savePrefText();
            }
            case R.id.btn_load: {
                loadPrefText();
            }
        }
    }

    private void savePrefText() {
        mPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(PREF_TEXT, valueField.getText().toString());
        editor.apply();
        Toast.makeText(this, getString(R.string.save_message), Toast.LENGTH_SHORT).show();
    }

    private void loadPrefText() {
        mPreferences = getPreferences(MODE_PRIVATE);
        String savedText = mPreferences.getString(PREF_TEXT, getString(R.string.default_pref));
        valueField.setText(savedText);
        Toast.makeText(this, R.string.load_message, Toast.LENGTH_SHORT).show();
    }
}
