package com.example.perfectproject;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_FIRST_USER;
import static android.app.Activity.RESULT_OK;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
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
import androidx.annotation.RequiresApi;
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
    ImageButton backButton;
    EditText someInfo;
    private boolean isReached = false;

    @Override
    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        window.setLayout(1090, 2090);
        window.setGravity(Gravity.CENTER);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
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
        someInfo = view.findViewById(R.id.some_info);


        if(getArguments().getInt("flag")==1) {
            cancelBtn.setText("Удалить");
            addBtn.setText("Изменить");


            cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    //Изначальные данные
                    intent.putExtra(DataBaseHelper.COLUMN_FULLNAME+"0",
                            getArguments().getString(DataBaseHelper.COLUMN_FULLNAME));
                    intent.putExtra(DataBaseHelper.COLUMN_AGE+"0",
                            String.valueOf(getArguments().getInt(DataBaseHelper.COLUMN_AGE)));
                    intent.putExtra(DataBaseHelper.COLUMN_BIRTHDAY+"0",
                            getArguments().getString(DataBaseHelper.COLUMN_BIRTHDAY));
                    intent.putExtra(DataBaseHelper.COLUMN_BLOODTYPE+"0",
                            getArguments().getString(DataBaseHelper.COLUMN_BLOODTYPE));
                    intent.putExtra(DataBaseHelper.COLUMN_CLASS+"0",
                            String.valueOf(getArguments().getInt(DataBaseHelper.COLUMN_CLASS)));
                    intent.putExtra(DataBaseHelper.COLUMN_CLASSROOMTEACHER+"0",
                            getArguments().getString(DataBaseHelper.COLUMN_CLASSROOMTEACHER));
                    intent.putExtra(DataBaseHelper.COLUMN_HEIGHT+"0",
                            String.valueOf(getArguments().getInt(DataBaseHelper.COLUMN_HEIGHT)));
                    intent.putExtra(DataBaseHelper.COLUMN_WEIGHT+"0",
                            String.valueOf(getArguments().getInt(DataBaseHelper.COLUMN_WEIGHT)));
                    intent.putExtra(DataBaseHelper.COLUMN_SEX+"0",
                            getArguments().getString(DataBaseHelper.COLUMN_SEX));
                    intent.putExtra(DataBaseHelper.COLUMN_SOME_INFO+"0",
                            getArguments().getString(DataBaseHelper.COLUMN_SOME_INFO));

                    getTargetFragment().onActivityResult(getTargetRequestCode(), RESULT_CANCELED, intent);
                    getDialog().dismiss();
                }
            });

            addBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO: Написать код для изменения данных
                    Intent intent = new Intent();


                    //Изменённые данные
                    intent.putExtra(DataBaseHelper.COLUMN_FULLNAME, fullName.getText().toString());
                    intent.putExtra(DataBaseHelper.COLUMN_AGE, age.getSelectedItem().toString());
                    intent.putExtra(DataBaseHelper.COLUMN_BIRTHDAY, birthday.getText().toString());
                    intent.putExtra(DataBaseHelper.COLUMN_BLOODTYPE, bloodType.getSelectedItem().toString());
                    intent.putExtra(DataBaseHelper.COLUMN_CLASS, className.getSelectedItem().toString());
                    intent.putExtra(DataBaseHelper.COLUMN_CLASSROOMTEACHER, mainTeacher.getText().toString());
                    intent.putExtra(DataBaseHelper.COLUMN_HEIGHT, height.getText().toString());
                    intent.putExtra(DataBaseHelper.COLUMN_WEIGHT, weight.getText().toString());
                    intent.putExtra(DataBaseHelper.COLUMN_SEX, sex.getSelectedItem().toString());
                    intent.putExtra(DataBaseHelper.COLUMN_SOME_INFO, someInfo.getText().toString());


                    //Изначальные данные
                    intent.putExtra(DataBaseHelper.COLUMN_FULLNAME+"0",
                            getArguments().getString(DataBaseHelper.COLUMN_FULLNAME));
                    intent.putExtra(DataBaseHelper.COLUMN_AGE+"0",
                            String.valueOf(getArguments().getInt(DataBaseHelper.COLUMN_AGE)));
                    intent.putExtra(DataBaseHelper.COLUMN_BIRTHDAY+"0",
                            getArguments().getString(DataBaseHelper.COLUMN_BIRTHDAY));
                    intent.putExtra(DataBaseHelper.COLUMN_BLOODTYPE+"0",
                            getArguments().getString(DataBaseHelper.COLUMN_BLOODTYPE));
                    intent.putExtra(DataBaseHelper.COLUMN_CLASS+"0",
                            String.valueOf(getArguments().getInt(DataBaseHelper.COLUMN_CLASS)));
                    intent.putExtra(DataBaseHelper.COLUMN_CLASSROOMTEACHER+"0",
                            getArguments().getString(DataBaseHelper.COLUMN_CLASSROOMTEACHER));
                    intent.putExtra(DataBaseHelper.COLUMN_HEIGHT+"0",
                            String.valueOf(getArguments().getInt(DataBaseHelper.COLUMN_HEIGHT)));
                    intent.putExtra(DataBaseHelper.COLUMN_WEIGHT+"0",
                            String.valueOf(getArguments().getInt(DataBaseHelper.COLUMN_WEIGHT)));
                    intent.putExtra(DataBaseHelper.COLUMN_SEX+"0",
                            getArguments().getString(DataBaseHelper.COLUMN_SEX));
                    intent.putExtra(DataBaseHelper.COLUMN_SOME_INFO+"0",
                            getArguments().getString(DataBaseHelper.COLUMN_SOME_INFO));

                    getTargetFragment().onActivityResult(getTargetRequestCode(), RESULT_FIRST_USER, intent);
                    getDialog().dismiss();
                }
            });

            String heightFromInt = String.valueOf(getArguments().getInt(DataBaseHelper.COLUMN_HEIGHT));
            String weightFromInt = String.valueOf(getArguments().getInt(DataBaseHelper.COLUMN_WEIGHT));
            int ageFromInt = getArguments().getInt(DataBaseHelper.COLUMN_AGE)-6;
            int classNameFromInt = getArguments().getInt(DataBaseHelper.COLUMN_CLASS)-1;

            fullName.setText(getArguments().getString(DataBaseHelper.COLUMN_FULLNAME));
            age.post(new Runnable() {
                @Override
                public void run() {
                    age.setSelection(ageFromInt);
                }
            });
            birthday.setText(getArguments().getString(DataBaseHelper.COLUMN_BIRTHDAY));
            switch(getArguments().getString(DataBaseHelper.COLUMN_BLOODTYPE)){
                case "Не выбрано":
                    bloodType.post(new Runnable() {
                        @Override
                        public void run() {
                            bloodType.setSelection(0);
                        }
                    });
                    break;
                case "I":
                    bloodType.post(new Runnable() {
                        @Override
                        public void run() {
                            bloodType.setSelection(1);
                        }
                    });
                    break;
                case "II":
                    bloodType.post(new Runnable() {
                        @Override
                        public void run() {
                            bloodType.setSelection(2);
                        }
                    });
                    break;
                case "III":
                    bloodType.post(new Runnable() {
                        @Override
                        public void run() {
                            bloodType.setSelection(3);
                        }
                    });
                    break;
                case "IV":
                    bloodType.post(new Runnable() {
                        @Override
                        public void run() {
                            bloodType.setSelection(4);
                        }
                    });
                    break;
            }
            className.post(new Runnable() {
                @Override
                public void run() {
                    className.setSelection(classNameFromInt);
                }
            });
            mainTeacher.setText(getArguments().getString(DataBaseHelper.COLUMN_CLASSROOMTEACHER));
            height.setText(heightFromInt);
            weight.setText(weightFromInt);
            switch (getArguments().getString(DataBaseHelper.COLUMN_SEX)){
                case "М":
                    sex.setSelection(0);
                case "Ж":
                    sex.setSelection(1);
            }
            someInfo.setText(getArguments().getString(DataBaseHelper.COLUMN_SOME_INFO));

        }
        else {
            addBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!fullName.getText().toString().equals("") &&
                            !birthday.getText().toString().equals("") &&
                            !mainTeacher.getText().toString().equals("") &&
                            !height.getText().toString().equals("") &&
                            !weight.getText().toString().equals("")) {
                        Intent intent = new Intent();
                        intent.putExtra(DataBaseHelper.COLUMN_FULLNAME, fullName.getText().toString());
                        intent.putExtra(DataBaseHelper.COLUMN_AGE, age.getSelectedItem().toString());
                        intent.putExtra(DataBaseHelper.COLUMN_BIRTHDAY, birthday.getText().toString());
                        intent.putExtra(DataBaseHelper.COLUMN_BLOODTYPE, bloodType.getSelectedItem().toString());
                        intent.putExtra(DataBaseHelper.COLUMN_CLASS, className.getSelectedItem().toString());
                        intent.putExtra(DataBaseHelper.COLUMN_CLASSROOMTEACHER, mainTeacher.getText().toString());
                        intent.putExtra(DataBaseHelper.COLUMN_HEIGHT, height.getText().toString());
                        intent.putExtra(DataBaseHelper.COLUMN_WEIGHT, weight.getText().toString());
                        intent.putExtra(DataBaseHelper.COLUMN_SEX, sex.getSelectedItem().toString());
                        intent.putExtra(DataBaseHelper.COLUMN_SOME_INFO, someInfo.getText().toString());

                        getTargetFragment().onActivityResult(getTargetRequestCode(), RESULT_OK, intent);
                        getDialog().dismiss();
                    }
                    else Toast.makeText(getContext(), "Заполните все поля", Toast.LENGTH_SHORT).show();
                }
            });

            cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onStop();
                }
            });

        }

        someInfo.addTextChangedListener(new TextWatcher() {
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
                if(fullName.getText().length() == 58 && !isReached) {
                    fullName.append("\n");
                    isReached = true;
                }
                if(fullName.getText().length() < 58 && isReached)  isReached = false;
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
                onStop();
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

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
    }
}