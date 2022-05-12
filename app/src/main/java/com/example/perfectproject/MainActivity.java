package com.example.perfectproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.HashMap;
import java.util.LinkedList;



public class MainActivity extends AppCompatActivity {

    public int flagSpinner=0;

    MainListActivityFragment mainListActivityFragment = new MainListActivityFragment();
    SearchActivityFragment searchActivityFragment = new SearchActivityFragment();
    FragmentManager fragmentManager = getSupportFragmentManager();

    Spinner basic_menu;
    EditText search_bar;
    LinkedList<HashMap<String, Object>> listForAdapter=new LinkedList<>();
    LinkedList<Student> studentLinkedList = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        basic_menu = findViewById(R.id.menu_button);
        search_bar = findViewById(R.id.search_bar);

        if(savedInstanceState==null){
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.add(R.id.container_layout, mainListActivityFragment);
            ft.commit();
        }

        search_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.replace(R.id.container_layout, searchActivityFragment);
                ft.commit();
            }
        });


        ArrayAdapter<?> arrayAdapterForSpinner = ArrayAdapter.createFromResource(this,
                R.array.spinner_text, android.R.layout.simple_spinner_dropdown_item);
        basic_menu.setAdapter(arrayAdapterForSpinner);


        basic_menu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch(i){
                    case 0:
                        if(flagSpinner==0){flagSpinner++; break;}
                        Intent intentAboutProgramm = new Intent(MainActivity.this,
                                AboutProgrammActivity.class);
                        startActivity(intentAboutProgramm);
                        break;
                    case 1:
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