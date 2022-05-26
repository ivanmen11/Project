package com.example.perfectproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    public int flagSpinner = 0;

    MainListActivityFragment mainListActivityFragment = new MainListActivityFragment();
    SearchActivityFragment searchActivityFragment = new SearchActivityFragment();
    FragmentManager fragmentManager = getSupportFragmentManager();

    RelativeLayout interface_menu;
    Spinner basic_menu;
    EditText search_bar;
    ImageButton to_main_btn;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        basic_menu = findViewById(R.id.menu_button);
        search_bar = findViewById(R.id.search_bar);
        interface_menu = findViewById(R.id.interface_menu);
        to_main_btn = findViewById(R.id.to_main_list_btn);

            if (savedInstanceState == null) {
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.add(R.id.container_layout, mainListActivityFragment);
                ft.commit();
            }

            search_bar.setOnTouchListener((view, motionEvent) -> {
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.replace(R.id.container_layout, searchActivityFragment);
                ft.commit();
                to_main_btn.setVisibility(View.VISIBLE);
                basic_menu.setVisibility(View.INVISIBLE);
                return false;
            });


            List<String> list = new ArrayList<>();
            list.add("О программе");
            list.add("Выход");
            list.add("");
            final int listSize = list.size() - 1;
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, list) {
                @Override
                public int getCount() {
                    return (listSize); // Truncate the list
                }
            };
            basic_menu.setAdapter(dataAdapter);


            basic_menu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    switch (i) {
                        case 0:
                            if (flagSpinner == 0) {
                                flagSpinner++;
                                break;
                            }
                            Intent intent = new Intent(MainActivity.this,
                                    AboutProgrammActivity.class);
                            startActivity(intent);
                            break;
                        case 1:
                            finishAffinity();
                            break;
                        case 2:
                            break;
                    }
                    adapterView.setSelection(2);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    adapterView.setSelection(2);
                }
            });


            to_main_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentTransaction ft = fragmentManager.beginTransaction();
                    ft.replace(R.id.container_layout, mainListActivityFragment);
                    ft.commit();
                    to_main_btn.setVisibility(View.INVISIBLE);
                    basic_menu.setVisibility(View.VISIBLE);
                    View viewKeyboard = getCurrentFocus();
                    if (viewKeyboard != null) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                    search_bar.setText("");

                    viewKeyboard.clearFocus();
                }
            });


        }

    }