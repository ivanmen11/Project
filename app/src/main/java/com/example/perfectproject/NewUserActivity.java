package com.example.perfectproject;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class NewUserActivity extends DialogFragment {

    EditText registrationLogin;
    EditText registrationPassword;
    EditText registrationPasswordConfirm;
    Button registrationButton;
    ImageButton toEnterBtn;
    SQLiteDatabase database;
    DBHelperForRegistration dbHelperForRegistration;

    @Override
    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        window.setLayout(1090, 1590);
        window.setGravity(Gravity.CENTER);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(true);


        View view = inflater.inflate(R.layout.activity_new_user, container, false);

        registrationLogin = view.findViewById(R.id.registration_login);
        registrationPassword = view.findViewById(R.id.registration_password);
        registrationPasswordConfirm = view.findViewById(R.id.password_confirm);
        registrationButton = view.findViewById(R.id.registration_btn);
        toEnterBtn=view.findViewById(R.id.to_enter_button);

        dbHelperForRegistration = new DBHelperForRegistration(getContext());
        database = dbHelperForRegistration.getWritableDatabase();

        toEnterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().cancel();
            }
        });


        registrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(registrationLogin.getText().toString().equals("") ||
                        registrationPassword.getText().toString().equals("") ||
                        registrationPasswordConfirm.getText().toString().equals("")){
                    Toast.makeText(getContext(), "Заполните все поля", Toast.LENGTH_SHORT).show();
                }
                else if(!(registrationPassword.getText().toString().equals(registrationPasswordConfirm.getText().toString()))){
                    Toast.makeText(getContext(), "Пароли не совпадают", Toast.LENGTH_SHORT).show();
                }
                else {

                    ContentValues values = new ContentValues();
                    values.put(DBHelperForRegistration.COLUMN_LOGIN, registrationLogin.getText().toString());
                    values.put(DBHelperForRegistration.COLUMN_PASSWORD, registrationPassword.getText().toString());


                    database.insert(DBHelperForRegistration.REGISTRATION_TABLE_NAME, null, values);
                    Toast.makeText(getContext(), "Данные зарегистрированы", Toast.LENGTH_SHORT).show();
                    getDialog().cancel();
                }


            }
        });


        return view;
    }


}