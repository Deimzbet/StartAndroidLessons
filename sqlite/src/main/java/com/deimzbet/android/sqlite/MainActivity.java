package com.deimzbet.android.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.deimzbet.android.sqlite.database.DatabaseHelper;

import java.io.UTFDataFormatException;

import static com.deimzbet.android.sqlite.database.DatabaseScheme.UserTable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String MAIN_TAG = "main";

    private EditText nameField, emailField, nameSearchField;

    private SQLiteDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameField = findViewById(R.id.et_name);
        emailField = findViewById(R.id.et_email);
        nameSearchField = findViewById(R.id.et_name_read);
        Button addButton = findViewById(R.id.btn_add);
        Button readButton = findViewById(R.id.btn_read);
        Button clearButton = findViewById(R.id.btn_clear);
        Button readOneButton = findViewById(R.id.btn_read_one);
        Button deleteButton = findViewById(R.id.btn_delete);


        addButton.setOnClickListener(this);
        readButton.setOnClickListener(this);
        clearButton.setOnClickListener(this);
        readOneButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        mDatabase = new DatabaseHelper(this).getWritableDatabase();
        switch (id) {
            case R.id.btn_add: {
                addUser();
                break;
            }
            case R.id.btn_read: {
                getAll();
                break;
            }
            case R.id.btn_clear: {
                clearDb();
                break;
            }
            case R.id.btn_read_one: {
                String value = nameSearchField.getText().toString();
                if (!value.equals("")) {
                    int userId = Integer.parseInt(value);
                    getUser(userId);
                }
                break;
            }
            case R.id.btn_delete: {
                String value = nameSearchField.getText().toString();
                if (!value.equals("")) {
                    int userId = Integer.parseInt(value);
                    deleteUser(userId);
                }
                break;
            }
        }
        mDatabase.close();
    }

    private void addUser() {
        if (nameField.getText().toString().equals("") || emailField.getText().toString().equals
                ("")) {
            Log.d(MAIN_TAG, "Enter valid Name and Email!\n");
            return;
        }
        User user = new User();
        ContentValues values = new ContentValues();
        user.setName(nameField.getText().toString());
        user.setEmail(emailField.getText().toString());

        values.put(UserTable.Columns.NAME, user.getName());
        values.put(UserTable.Columns.EMAIL, user.getEmail());
        mDatabase.insert(UserTable.NAME, null, values);

        Log.d(MAIN_TAG, "User " + user.getName() + " added!\n");
    }

    private void getAll() {
        Log.d(MAIN_TAG, "Read database: ");
        try (Cursor cursor = mDatabase.query(
                UserTable.NAME,
                null,
                null,
                null,
                null,
                null,
                null)) {

            if (cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndex("_id"));
                    String name = cursor.getString(cursor.getColumnIndex(UserTable.Columns.NAME));
                    String email = cursor.getString(cursor.getColumnIndex(UserTable.Columns.EMAIL));
                    Log.d(MAIN_TAG, String.valueOf(id) + ": " + name + "/" + email + ".\n");
                } while (cursor.moveToNext());
            } else {
                Log.d(MAIN_TAG, "0 users in database! \n");
            }
        }
    }

    private void getUser(int userId) {
        try (Cursor cursor = mDatabase.query(
                UserTable.NAME,
                null,
                "_id = ? ",
                new String[] { String.valueOf(userId)},
                null,
                null,
                null)) {
            if (cursor.moveToFirst()) {
                int id = cursor.getInt(cursor.getColumnIndex("_id"));
                String name = cursor.getString(cursor.getColumnIndex(UserTable.Columns.NAME));
                String email = cursor.getString(cursor.getColumnIndex(UserTable.Columns.EMAIL));
                nameField.setText(name);
                emailField.setText(email);
                Log.d(MAIN_TAG, String.valueOf(id) + ": " + name + "/" + email + ".\n");
            } else {
                Log.d(MAIN_TAG, "None");
            }
        }
    }

    private void deleteUser(int userId) {
        mDatabase.delete(UserTable.NAME, "_id = ? ", new String[] {String.valueOf(userId)});
    }

    private void clearDb() {
        Log.d(MAIN_TAG, "Clear table " + UserTable.NAME.toUpperCase() + ":");
        int count = mDatabase.delete(UserTable.NAME, null, null);
        Log.d(MAIN_TAG, UserTable.NAME.toUpperCase() +
                " table clear. " +
                count +
                " users delete" +
                ".\n");
    }
}
