package com.example.perfectproject;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class NewStudentActivity extends DialogFragment {

    EditText mainTeacher;
    Spinner className;
    Spinner age;
    EditText birthday;
    EditText fullName;
    EditText height;
    EditText weight;
    Spinner sex;
    Spinner bloodType;
    Button addBtn;
    Button cancelBtn;
    DataBaseHelper dataBaseHelper;
    SQLiteDatabase database;
    ImageButton backButton;

    @Override
    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        window.setLayout(1090, 1790);
        window.setGravity(Gravity.CENTER);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(true);

        View view = inflater.inflate(R.layout.activity_new_student, container, false);

        addBtn = view.findViewById(R.id.add_button);
        cancelBtn = view.findViewById(R.id.cancel_button);
        mainTeacher = view.findViewById(R.id.main_teacher);
        className = view.findViewById(R.id.classroom);
        age = view.findViewById(R.id.age);
        birthday = view.findViewById(R.id.birthday);
        fullName = view.findViewById(R.id.fullname);
        height = view.findViewById(R.id.height);
        weight = view.findViewById(R.id.weight);
        sex = view.findViewById(R.id.sex);
        bloodType = view.findViewById(R.id.bloodtype);
        backButton = view.findViewById(R.id.back_button);

        mainTeacher.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(null!=mainTeacher.getLayout() && mainTeacher.getLayout().getLineCount() >1){
                    mainTeacher.getText().delete(mainTeacher.getText().length()-1,
                            mainTeacher.getText().length());
                }
            }
        });

        birthday.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(null!=birthday.getLayout() && birthday.getLayout().getLineCount() >1){
                    birthday.getText().delete(birthday.getText().length()-1,
                            birthday.getText().length());
                }
            }
        });

        fullName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(null!=fullName.getLayout() && fullName.getLayout().getLineCount() >1){
                    fullName.getText().delete(fullName.getText().length()-1,
                            fullName.getText().length());
                }
            }
        });

        height.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(null!=height.getLayout() && height.getLayout().getLineCount() >1){
                    height.getText().delete(height.getText().length()-1,
                            height.getText().length());
                }
            }
        });

        weight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(null!=weight.getLayout() && weight.getLayout().getLineCount() >1){
                    weight.getText().delete(weight.getText().length()-1,
                            weight.getText().length());
                }
            }
        });


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });


        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataBaseHelper = new DataBaseHelper(getContext());
                database = dataBaseHelper.getWritableDatabase();
                ContentValues value = new ContentValues();

                if(!fullName.getText().toString().equals("") &&
                        !mainTeacher.getText().toString().equals("") &&
                        !height.getText().toString().equals("") &&
                        !weight.getText().toString().equals("") &&
                        !birthday.getText().toString().equals("")) {
                    value.put(DataBaseHelper.COLUMN_FULLNAME, fullName.getText().toString());
                    value.put(DataBaseHelper.COLUMN_CLASSROOMTEACHER, mainTeacher.getText().toString());
                    value.put(DataBaseHelper.COLUMN_CLASS, className.getSelectedItem().toString());
                    value.put(DataBaseHelper.COLUMN_BIRTHDAY, birthday.getText().toString());
                    value.put(DataBaseHelper.COLUMN_HEIGHT, height.getText().toString());
                    value.put(DataBaseHelper.COLUMN_WEIGHT, weight.getText().toString());
                    value.put(DataBaseHelper.COLUMN_SEX, weight.getText().toString());
                    value.put(DataBaseHelper.COLUMN_AGE, age.getSelectedItem().toString());
                    value.put(DataBaseHelper.COLUMN_BLOODTYPE, bloodType.getSelectedItem().toString());
                    database.insert(DataBaseHelper.TABLE_NAME, null, value);
                    database.close();
                    onStop();
                }else{
                    Toast.makeText(getContext(), "Заполните все поля", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });


        ArrayAdapter<?> arrayAdapterForSpinnerClassRoom = ArrayAdapter.
                createFromResource(getContext(),
                        R.array.spinner_class_text,
                        android.R.layout.simple_spinner_dropdown_item);
        className.setAdapter(arrayAdapterForSpinnerClassRoom);

        ArrayAdapter<?> arrayAdapterForSpinnerAge = ArrayAdapter.
                createFromResource(getContext(),
                        R.array.spinner_age_text,
                        android.R.layout.simple_spinner_dropdown_item);
        age.setAdapter(arrayAdapterForSpinnerAge);

        ArrayAdapter<?> arrayAdapterForSpinnerSex = ArrayAdapter.
                createFromResource(getContext(),
                        R.array.spinner_sex_text,
                        android.R.layout.simple_spinner_dropdown_item);
        sex.setAdapter(arrayAdapterForSpinnerSex);

        ArrayAdapter<?> arrayAdapterForSpinnerBloodType = ArrayAdapter.
                createFromResource(getContext(),
                        R.array.spinner_bloodtype_text,
                        android.R.layout.simple_spinner_dropdown_item);
        bloodType.setAdapter(arrayAdapterForSpinnerBloodType);

        return view;

    }


}