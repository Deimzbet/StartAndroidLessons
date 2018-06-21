package com.deimzbet.android.sqlitecountry.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.deimzbet.android.sqlitecountry.database.CountryDbSchema.CountryTable;

public class CountryDbHelper extends SQLiteOpenHelper {

    private static final String NAME = "country_database.db";
    private static final int VERSION = 1;

    public CountryDbHelper(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + CountryTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                CountryTable.Columns.COUNTRY_NAME + " text, " +
                CountryTable.Columns.REGION + " text, " +
                CountryTable.Columns.POPULATION + " integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
