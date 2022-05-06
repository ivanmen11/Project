package com.example.perfectproject;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.LinkedList;



public class MainActivity extends AppCompatActivity {

    public int flagSpinner=0;

    DataBaseHelper dataBaseHelper;
    Spinner basic_menu;
    ListView students_view;
    SQLiteDatabase database;
    AutoCompleteTextView search_bar;
    Button student;
    LinkedList<HashMap<String, Object>> listForAdapter=new LinkedList<>();
    LinkedList<Student> studentLinkedList = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        basic_menu = findViewById(R.id.menu_button);
        student = findViewById(R.id.student_button);
        students_view = findViewById(R.id.student_list);
        search_bar = findViewById(R.id.search_bar);


        ArrayAdapter<?> arrayAdapterForSpinner = ArrayAdapter.createFromResource(this,
                R.array.spinner_text, android.R.layout.simple_spinner_dropdown_item);
        basic_menu.setAdapter(arrayAdapterForSpinner);

        dataBaseHelper = new DataBaseHelper(this);
        database = dataBaseHelper.getWritableDatabase();

        basic_menu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch(i){
                    case 0:
                        if(flagSpinner==0) {flagSpinner++;break;}
                            Intent intentNewStudent = new Intent(MainActivity.this,
                                    NewStudentActivity.class);
                            startActivity(intentNewStudent);
                            break;
                    case 1:
                        Intent intentAboutProgramm = new Intent(MainActivity.this,
                                AboutProgrammActivity.class);
                        startActivity(intentAboutProgramm);
                        break;
                    case 2:
                        finishAffinity();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

    }

}