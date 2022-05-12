package com.example.perfectproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Student.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "StudentDataBase";
    public static final String COLUMN_FULLNAME = "Name";//1
    public static final String COLUMN_CLASSROOMTEACHER = "ClassroomTeacher";//5
    public static final String COLUMN_CLASS = "Class";//4
    public static final String COLUMN_BIRTHDAY = "Birthday";//2
    public static final String COLUMN_HEIGHT = "Height";//6
    public static final String COLUMN_WEIGHT = "Weight";//7
    public static final String COLUMN_SEX = "Sex";//8
    public static final String COLUMN_AGE = "Age";//3
    public static final String COLUMN_BLOODTYPE = "BloodType";//9

    public DataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION );
    }



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sqlQuery = "CREATE TABLE " + TABLE_NAME +
                "( _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_FULLNAME + " TEXT NOT NULL, " +
                COLUMN_CLASSROOMTEACHER + " TEXT NOT NULL, "+
                COLUMN_CLASS + " INTEGER NOT NULL, " +
                COLUMN_BIRTHDAY + " TEXT NOT NULL, " +
                COLUMN_HEIGHT + " INTEGER NOT NULL, " +
                COLUMN_WEIGHT + " INTEGER NOT NULL, " +
                COLUMN_SEX + " TEXT NOT NULL, " +
                COLUMN_AGE + " INTEGER NOT NULL, " +
                COLUMN_BLOODTYPE + " TEXT);";
        sqLiteDatabase.execSQL(sqlQuery);
    }



    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
