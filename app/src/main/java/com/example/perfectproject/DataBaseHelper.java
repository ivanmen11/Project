package com.example.perfectproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Student";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "StudentDataBase.db";
    public static final String COLUMN_FULLNAME = "Name";
    public static final String COLUMN_CLASSROOMTEACHER = "ClassroomTeacher";
    public static final String COLUMN_CLASSROOM = "Classroom";
    public static final String COLUMN_CLASS = "Class";
    public static final String COLUMN_BIRTHDAY = "Birthday";
    public static final String COLUMN_HEIGHT = "Height";
    public static final String COLUMN_WEIGHT = "Weight";
    public static final String COLUMN_SEX = "Sex";
    public static final String COLUMN_AGE = "Age";


    public DataBaseHelper(@Nullable Context context) {
        super(context, TABLE_NAME, null, DATABASE_VERSION );
    }



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sqlQuery = "CREATE TABLE " + DATABASE_NAME +
                "( _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_FULLNAME + " TEXT NOT NULL, " +
                COLUMN_CLASSROOMTEACHER + " TEXT NOT NULL, "+
                COLUMN_CLASSROOM + " TEXT NOT NULL, "+
                COLUMN_CLASS + " TEXT NOT NULL, " +
                COLUMN_BIRTHDAY + " TEXT NOT NULL, " +
                COLUMN_HEIGHT + " INTEGER NOT NULL, " +
                COLUMN_WEIGHT + " INTEGER NOT NULL, " +
                COLUMN_SEX + " TEXT NOT NULL, " +
                COLUMN_AGE + " TEXT NOT NULL);";
        sqLiteDatabase.execSQL(sqlQuery);
    }



    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
