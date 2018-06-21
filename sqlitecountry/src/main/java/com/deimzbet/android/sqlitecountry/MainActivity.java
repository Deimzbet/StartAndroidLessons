package com.deimzbet.android.sqlitecountry;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.deimzbet.android.sqlitecountry.database.CountryDbHelper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText countryField, regionField, populationField, moreField;
    private Button addButton, allButton, regionsButton, morecButton, morerButton;
    private TextView showLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countryField = findViewById(R.id.et_country);
        regionField = findViewById(R.id.et_region);
        populationField = findViewById(R.id.et_population);
        moreField = findViewById(R.id.et_more);
        addButton = findViewById(R.id.btn_add);
        allButton = findViewById(R.id.btn_all);
        regionsButton = findViewById(R.id.btn_regions);
        morecButton = findViewById(R.id.btn_more_country);
        morerButton = findViewById(R.id.btn_more_region);
        showLabel = findViewById(R.id.tv_show);

        addButton.setOnClickListener(this);
        allButton.setOnClickListener(this);
        regionsButton.setOnClickListener(this);
        morecButton.setOnClickListener(this);
        morerButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        CountryLab lab = CountryLab.get(this);
        String country = countryField.getText().toString();
        String region = countryField.getText().toString();
        int population = Integer.parseInt(countryField.getText().toString());
        int more = Integer.parseInt(countryField.getText().toString());
        switch (id) {
            case R.id.btn_add: {
                Country mCountry = new Country(country, region, population);
                lab.addCountry(mCountry);
            }
            case R.id.btn_all: {
                showLabel.setText(lab.getAll());
            }
            case R.id.btn_regions: {
                showLabel.setText(lab.getGroupRegion());
            }
            case R.id.btn_more_country: {
                showLabel.setText(lab.getPopulationMore(more));
            }
            case R.id.btn_more_region: {
                showLabel.setText(lab.getPopulationRegionMore(more));
            }
        }

    }
}
