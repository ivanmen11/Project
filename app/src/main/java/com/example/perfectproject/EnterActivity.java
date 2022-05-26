package com.example.perfectproject;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

public class EnterActivity extends AppCompatActivity {

    Button enter_btn;
    Button registration_btn;
    EditText login;
    EditText password;
    SQLiteDatabase database;
    DBHelperForRegistration dbHelperForRegistration;
    Cursor userCursor;
    int fl=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);
        enter_btn = findViewById(R.id.enter_button);
        registration_btn = findViewById(R.id.registration_button);
        login = findViewById(R.id.user_login);
        password = findViewById(R.id.user_password);

        dbHelperForRegistration = new DBHelperForRegistration(getApplicationContext());
        database = dbHelperForRegistration.getWritableDatabase();

        enter_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                userCursor = database.query(DBHelperForRegistration.REGISTRATION_TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);

                if(userCursor.moveToFirst()){
                    do{
                        if(login.getText().toString().equals(userCursor.getString((int)userCursor.getColumnIndex(DBHelperForRegistration.COLUMN_LOGIN))) &&
                                password.getText().toString().equals(userCursor.getString((int)userCursor.getColumnIndex(DBHelperForRegistration.COLUMN_PASSWORD)))){
                            Intent intent = new Intent(EnterActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                            fl++;
                            break;
                        }

                    }while(userCursor.moveToNext());
                    if(fl==0) Toast.makeText(getApplicationContext(), "Введены неверные данные", Toast.LENGTH_SHORT).show();
                }
                else Toast.makeText(getApplicationContext(), "Зарегистрируйтесь", Toast.LENGTH_SHORT).show();
                userCursor.close();
            }
        });

        registration_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newUserDialog = new NewUserActivity();
                newUserDialog.show(getSupportFragmentManager(), "setNewUser");
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        database.close();
        userCursor.close();
    }
}