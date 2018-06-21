package com.deimzbet.android.sqlitecountry;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;

import com.deimzbet.android.sqlitecountry.database.CountryDbHelper;

import static com.deimzbet.android.sqlitecountry.database.CountryDbSchema.CountryTable;

public class CountryLab {

    private static CountryLab countryList;
    private SQLiteDatabase database;

    private CountryLab(Context context) {
        database = new CountryDbHelper(context).getWritableDatabase();
    }

    public static CountryLab get(Context context) {
        if (countryList == null) {
            countryList = new CountryLab(context);
        }
        return countryList;
    }

    public String getAll() {
        return getAll(null);
    }

    private String getAll(@Nullable String... values) {
        StringBuilder builder = new StringBuilder();
        try (Cursor cursor = database.query(
                CountryTable.NAME, values, null, null, null, null,
                null, null)) {
            readData(builder, cursor);
        }
        return builder.toString();
    }

    public String getPopulationMore(int value) {
        StringBuilder builder = new StringBuilder();
        try (Cursor cursor = database.query(
                CountryTable.NAME,
                null,
                CountryTable.Columns.POPULATION + " > ? ",
                new String[]{String.valueOf(value)},
                null,
                null,
                null,
                null)) {
            readData(builder, cursor);
        }
        return builder.toString();
    }

    public String getGroupRegion() {
        String[] columns = {CountryTable.Columns.REGION, "sum(" +
                CountryTable.Columns.POPULATION + ") as " +
                CountryTable.Columns.POPULATION};
        StringBuilder builder = new StringBuilder();
        try (Cursor cursor = database.query(
                CountryTable.NAME,
                columns,
                null,
                null,
                CountryTable.Columns.REGION,
                null,
                null,
                null)) {
            readData(builder, cursor);
        }
        return builder.toString();
    }

    public String getPopulationRegionMore(int value) {
        String[] columns = {CountryTable.Columns.REGION, "sum(" +
                CountryTable.Columns.POPULATION + ") as " +
                CountryTable.Columns.POPULATION};
        StringBuilder builder = new StringBuilder();
        try (Cursor cursor = database.query(
                CountryTable.NAME,
                columns,
                "sum(" + CountryTable.Columns.POPULATION + ") > ? ",
                new String[]{String.valueOf(value)},
                CountryTable.Columns.REGION,
                null,
                null,
                null)) {
            readData(builder, cursor);
        }
        return builder.toString();
    }

    private void readData(StringBuilder builder, Cursor cursor) {
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndex(CountryTable.Columns.COUNTRY_NAME));
                String region = cursor.getString(cursor.getColumnIndex(CountryTable.Columns
                        .REGION));
                int population = cursor.getInt(cursor.getColumnIndex(CountryTable.Columns
                        .POPULATION));
                builder.append(name).append(region).append(population).append("\n");
            } while (cursor.moveToNext());
        }
    }

    public void addCountry(Country country) {
        ContentValues values = new ContentValues();
        values.put(CountryTable.Columns.COUNTRY_NAME, country.getName());
        values.put(CountryTable.Columns.REGION, country.getRegion());
        values.put(CountryTable.Columns.POPULATION, country.getPopulation());

        database.insert(CountryTable.NAME, null, values);
    }


}
