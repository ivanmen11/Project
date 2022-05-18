package com.example.perfectproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class EnterActivity extends AppCompatActivity {

    Button enter_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);
        enter_btn = findViewById(R.id.enter_button);

        enter_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EnterActivity.this, MainActivity.class);
                startActivity(intent);
                finishActivity(1);
            }
        });


    }

}