package com.deimzbet.android.sqlite.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.deimzbet.android.sqlite.database.DatabaseScheme.UserTable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String NAME = "users.db";
    private static int VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + UserTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                UserTable.Columns.NAME + ", " +
                UserTable.Columns.EMAIL + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
