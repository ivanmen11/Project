package com.example.perfectproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelperForRegistration extends SQLiteOpenHelper {


    public static final String DATABASE_NAME_REGISTRATION = "RegistrationDB.db";
    public static final String REGISTRATION_TABLE_NAME = "RegistrationDB";
    public static final String COLUMN_PASSWORD = "Password";
    public static final String COLUMN_LOGIN = "Login";

    public DBHelperForRegistration(@Nullable Context context) {
        super(context, DATABASE_NAME_REGISTRATION, null, 1);

    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sqlQuery = "CREATE TABLE " + REGISTRATION_TABLE_NAME +
                "( _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_LOGIN + " TEXT NOT NULL, " +
                COLUMN_PASSWORD + " TEXT NOT NULL);";
        sqLiteDatabase.execSQL(sqlQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + REGISTRATION_TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
