package com.example.perfectproject;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.LinkedList;


public class MainListActivityFragment extends Fragment {

    FloatingActionButton floatingActionButton;
    ListView students_view;
    DataBaseHelper dataBaseHelper;
    SQLiteDatabase database;
    Button student;
    public LinkedList<Student> studentLinkedList;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_list, null);
        floatingActionButton = view.findViewById(R.id.fab);
        student = view.findViewById(R.id.student_button);
        students_view = view.findViewById(R.id.student_list);
        studentLinkedList = new LinkedList<>();


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newStudentDialog = new NewStudentActivity();
                newStudentDialog.show(getFragmentManager(), "lol");
            }
        });


        dataBaseHelper = new DataBaseHelper(getContext());
        database = dataBaseHelper.getWritableDatabase();
        Cursor cursor = database.query(DataBaseHelper.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);
        if(cursor.moveToFirst()){
            do{
                studentLinkedList.add(new Student(
                        cursor.getString((int)cursor.getColumnIndex(DataBaseHelper.COLUMN_FULLNAME)),
                        cursor.getString((int)cursor.getColumnIndex(DataBaseHelper.COLUMN_CLASS)),
                        cursor.getString((int)cursor.getColumnIndex(DataBaseHelper.COLUMN_CLASSROOMTEACHER)),
                        cursor.getInt((int)cursor.getColumnIndex(DataBaseHelper.COLUMN_HEIGHT)),
                        cursor.getInt((int)cursor.getColumnIndex(DataBaseHelper.COLUMN_WEIGHT)),
                        cursor.getString((int)cursor.getColumnIndex(DataBaseHelper.COLUMN_BIRTHDAY)),
                        cursor.getString((int)cursor.getColumnIndex(DataBaseHelper.COLUMN_SEX)),
                        cursor.getInt((int)cursor.getColumnIndex(DataBaseHelper.COLUMN_AGE)),
                        cursor.getString((int)cursor.getColumnIndex(DataBaseHelper.COLUMN_BLOODTYPE))));
            } while (cursor.moveToNext());
            cursor.close();
            students_view.setAdapter(new ArrayAdapter<Student>(getContext(),
                    R.layout.list_item, R.id.student_button, studentLinkedList));
        }else{
            Toast.makeText(getContext(), "Ошибка считывания", Toast.LENGTH_SHORT).show();
        }



        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        database.close();
    }

}
