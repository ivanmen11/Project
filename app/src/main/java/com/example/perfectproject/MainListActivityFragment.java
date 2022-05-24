package com.example.perfectproject;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_FIRST_USER;
import static android.app.Activity.RESULT_OK;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
    LinkedList<Student> studentLinkedList;
    ArrayAdapter<Student> arrayAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_list, null);
        floatingActionButton = view.findViewById(R.id.fab);
        student = view.findViewById(R.id.student_button);
        students_view = view.findViewById(R.id.student_list);
        studentLinkedList = new LinkedList<>();

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
                        cursor.getString((int)cursor.getColumnIndex(DataBaseHelper.COLUMN_BLOODTYPE)),
                        cursor.getString((int)cursor.getColumnIndex(DataBaseHelper.COLUMN_SOME_INFO))));
            } while (cursor.moveToNext());
            cursor.close();
            //dataBaseUpdate(studentLinkedList);
            arrayAdapter =new ArrayAdapter<>(getContext(),
                    R.layout.list_item, R.id.student_button, studentLinkedList);
            students_view.setAdapter(arrayAdapter);
        }

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newStudentDialog = new NewStudentActivity();
                Bundle args = new Bundle();
                args.putInt("flag", 0);
                newStudentDialog.setArguments(args);
                newStudentDialog.setTargetFragment(MainListActivityFragment.this, 1);
                newStudentDialog.show(getFragmentManager(), "getNewStudent");
            }
        });

        students_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                DialogFragment newStudentDialog = new NewStudentActivity();
                newStudentDialog.setTargetFragment(MainListActivityFragment.this, 1);

                Bundle args = new Bundle();
                args.putInt("flag", 1);
                args.putString(DataBaseHelper.COLUMN_FULLNAME, studentLinkedList.get(i).fullName);
                args.putInt(DataBaseHelper.COLUMN_AGE, studentLinkedList.get(i).age);
                args.putString(DataBaseHelper.COLUMN_BIRTHDAY, studentLinkedList.get(i).birthday);
                args.putString(DataBaseHelper.COLUMN_BLOODTYPE, studentLinkedList.get(i).bloodType);
                args.putString(DataBaseHelper.COLUMN_CLASS, studentLinkedList.get(i).className);
                args.putString(DataBaseHelper.COLUMN_CLASSROOMTEACHER, studentLinkedList.get(i).mainTeacher);
                args.putInt(DataBaseHelper.COLUMN_HEIGHT, studentLinkedList.get(i).height);
                args.putInt(DataBaseHelper.COLUMN_WEIGHT, studentLinkedList.get(i).weight);
                args.putString(DataBaseHelper.COLUMN_SEX, studentLinkedList.get(i).sex);
                args.putString(DataBaseHelper.COLUMN_SOME_INFO, studentLinkedList.get(i).someInfo);

                newStudentDialog.setArguments(args);
                newStudentDialog.show(getFragmentManager(), "getNewStudent");
            }
        });



        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            dataBaseHelper = new DataBaseHelper(getContext());
            database = dataBaseHelper.getWritableDatabase();

            studentLinkedList.add(new Student(data.getStringExtra(DataBaseHelper.COLUMN_FULLNAME),
                    data.getStringExtra(DataBaseHelper.COLUMN_CLASS),
                    data.getStringExtra(DataBaseHelper.COLUMN_CLASSROOMTEACHER),
                    Integer.parseInt(data.getStringExtra(DataBaseHelper.COLUMN_HEIGHT)),
                    Integer.parseInt(data.getStringExtra(DataBaseHelper.COLUMN_WEIGHT)),
                    data.getStringExtra(DataBaseHelper.COLUMN_BIRTHDAY),
                    data.getStringExtra(DataBaseHelper.COLUMN_SEX),
                    Integer.parseInt(data.getStringExtra(DataBaseHelper.COLUMN_AGE)),
                    data.getStringExtra(DataBaseHelper.COLUMN_BLOODTYPE),
                    data.getStringExtra(DataBaseHelper.COLUMN_SOME_INFO)));
            ContentValues value = new ContentValues();

            value.put(DataBaseHelper.COLUMN_FULLNAME, data.getStringExtra(DataBaseHelper.COLUMN_FULLNAME));
            value.put(DataBaseHelper.COLUMN_CLASSROOMTEACHER, data.getStringExtra(DataBaseHelper.COLUMN_CLASSROOMTEACHER));
            value.put(DataBaseHelper.COLUMN_CLASS, data.getStringExtra(DataBaseHelper.COLUMN_CLASS));
            value.put(DataBaseHelper.COLUMN_BIRTHDAY, data.getStringExtra(DataBaseHelper.COLUMN_BIRTHDAY));
            value.put(DataBaseHelper.COLUMN_HEIGHT, Integer.parseInt(data.getStringExtra(DataBaseHelper.COLUMN_HEIGHT)));
            value.put(DataBaseHelper.COLUMN_WEIGHT, Integer.parseInt(data.getStringExtra(DataBaseHelper.COLUMN_WEIGHT)));
            value.put(DataBaseHelper.COLUMN_SEX, data.getStringExtra(DataBaseHelper.COLUMN_SEX));
            value.put(DataBaseHelper.COLUMN_AGE,  Integer.parseInt(data.getStringExtra(DataBaseHelper.COLUMN_AGE)));
            value.put(DataBaseHelper.COLUMN_BLOODTYPE, data.getStringExtra(DataBaseHelper.COLUMN_BLOODTYPE));
            value.put(DataBaseHelper.COLUMN_SOME_INFO, data.getStringExtra(DataBaseHelper.COLUMN_SOME_INFO));
            database.insert(DataBaseHelper.TABLE_NAME, null, value);

            if(studentLinkedList.size()==1){
            arrayAdapter =new ArrayAdapter<>(getContext(),
                    R.layout.list_item, R.id.student_button, studentLinkedList);
            students_view.setAdapter(arrayAdapter);
            }
            else{
                arrayAdapter.notifyDataSetChanged();
            }
            Toast.makeText(getContext(), "Данные внесены", Toast.LENGTH_SHORT).show();
        }
        else {

            String whereClause = DataBaseHelper.COLUMN_FULLNAME + " = ? AND " +
                    DataBaseHelper.COLUMN_AGE + " = ? AND " +
                    DataBaseHelper.COLUMN_BIRTHDAY + " = ? AND " +
                    DataBaseHelper.COLUMN_BLOODTYPE + " = ? AND " +
                    DataBaseHelper.COLUMN_CLASS + " = ? AND " +
                    DataBaseHelper.COLUMN_CLASSROOMTEACHER + " = ? AND " +
                    DataBaseHelper.COLUMN_HEIGHT + " = ? AND " +
                    DataBaseHelper.COLUMN_WEIGHT + " = ? AND " +
                    DataBaseHelper.COLUMN_SEX + " = ? AND " +
                    DataBaseHelper.COLUMN_SOME_INFO + " = ?";


            if(resultCode == RESULT_FIRST_USER){
                //код для изменения
                ContentValues value = new ContentValues();

                value.put(DataBaseHelper.COLUMN_FULLNAME, data.getStringExtra(DataBaseHelper.COLUMN_FULLNAME));
                value.put(DataBaseHelper.COLUMN_CLASSROOMTEACHER, data.getStringExtra(DataBaseHelper.COLUMN_CLASSROOMTEACHER));
                value.put(DataBaseHelper.COLUMN_CLASS, data.getStringExtra(DataBaseHelper.COLUMN_CLASS));
                value.put(DataBaseHelper.COLUMN_BIRTHDAY, data.getStringExtra(DataBaseHelper.COLUMN_BIRTHDAY));
                value.put(DataBaseHelper.COLUMN_HEIGHT, Integer.parseInt(data.getStringExtra(DataBaseHelper.COLUMN_HEIGHT)));
                value.put(DataBaseHelper.COLUMN_WEIGHT, Integer.parseInt(data.getStringExtra(DataBaseHelper.COLUMN_WEIGHT)));
                value.put(DataBaseHelper.COLUMN_SEX, data.getStringExtra(DataBaseHelper.COLUMN_SEX));
                value.put(DataBaseHelper.COLUMN_AGE,  Integer.parseInt(data.getStringExtra(DataBaseHelper.COLUMN_AGE)));
                value.put(DataBaseHelper.COLUMN_BLOODTYPE, data.getStringExtra(DataBaseHelper.COLUMN_BLOODTYPE));
                value.put(DataBaseHelper.COLUMN_SOME_INFO, data.getStringExtra(DataBaseHelper.COLUMN_SOME_INFO));

                database.update(DataBaseHelper.TABLE_NAME,
                        value,
                        whereClause,
                        new String[]{data.getStringExtra(DataBaseHelper.COLUMN_FULLNAME),
                                (data.getStringExtra(DataBaseHelper.COLUMN_AGE)),
                                data.getStringExtra(DataBaseHelper.COLUMN_BIRTHDAY),
                                data.getStringExtra(DataBaseHelper.COLUMN_BLOODTYPE),
                                data.getStringExtra(DataBaseHelper.COLUMN_CLASS),
                                data.getStringExtra(DataBaseHelper.COLUMN_CLASSROOMTEACHER),
                                data.getStringExtra(DataBaseHelper.COLUMN_HEIGHT),
                                data.getStringExtra(DataBaseHelper.COLUMN_WEIGHT),
                                data.getStringExtra(DataBaseHelper.COLUMN_SEX),
                                data.getStringExtra(DataBaseHelper.COLUMN_SOME_INFO)});

                for (int i = 0; i < studentLinkedList.size(); i++) {
                    if(studentLinkedList.get(i).fullName.equals(data.getStringExtra(DataBaseHelper.COLUMN_FULLNAME)) &&
                            studentLinkedList.get(i).age==Integer.parseInt(data.getStringExtra(DataBaseHelper.COLUMN_AGE))  &&
                            studentLinkedList.get(i).birthday.equals(data.getStringExtra(DataBaseHelper.COLUMN_BIRTHDAY)) &&
                            studentLinkedList.get(i).bloodType.equals(data.getStringExtra(DataBaseHelper.COLUMN_BLOODTYPE)) &&
                            studentLinkedList.get(i).className.equals(data.getStringExtra(DataBaseHelper.COLUMN_CLASS)) &&
                            studentLinkedList.get(i).mainTeacher.equals(data.getStringExtra(DataBaseHelper.COLUMN_CLASSROOMTEACHER)) &&
                            studentLinkedList.get(i).height==Integer.parseInt(data.getStringExtra(DataBaseHelper.COLUMN_HEIGHT)) &&
                            studentLinkedList.get(i).weight==Integer.parseInt(data.getStringExtra(DataBaseHelper.COLUMN_WEIGHT)) &&
                            studentLinkedList.get(i).sex.equals(data.getStringExtra(DataBaseHelper.COLUMN_SEX)) &&
                            studentLinkedList.get(i).someInfo.equals(data.getStringExtra(DataBaseHelper.COLUMN_SOME_INFO))){

                        //TODO Сделать изменения в листе и адапетере
                        break;

                    }
                }
                arrayAdapter.notifyDataSetChanged();

                Toast.makeText(getContext(), "Данные изменены", Toast.LENGTH_SHORT).show();


            }
            else if(resultCode == RESULT_CANCELED){
                //Код для удаления
                if(studentLinkedList.size()!=1) {
                    database.delete(DataBaseHelper.TABLE_NAME,
                            whereClause,
                            new String[]{data.getStringExtra(DataBaseHelper.COLUMN_FULLNAME),
                                    (data.getStringExtra(DataBaseHelper.COLUMN_AGE)),
                                    data.getStringExtra(DataBaseHelper.COLUMN_BIRTHDAY),
                                    data.getStringExtra(DataBaseHelper.COLUMN_BLOODTYPE),
                                    data.getStringExtra(DataBaseHelper.COLUMN_CLASS),
                                    data.getStringExtra(DataBaseHelper.COLUMN_CLASSROOMTEACHER),
                                    data.getStringExtra(DataBaseHelper.COLUMN_HEIGHT),
                                    data.getStringExtra(DataBaseHelper.COLUMN_WEIGHT),
                                    data.getStringExtra(DataBaseHelper.COLUMN_SEX),
                                    data.getStringExtra(DataBaseHelper.COLUMN_SOME_INFO)});


                    for (int i = 0; i < studentLinkedList.size(); i++) {
                        if(studentLinkedList.get(i).fullName.equals(data.getStringExtra(DataBaseHelper.COLUMN_FULLNAME)) &&
                                studentLinkedList.get(i).age==Integer.parseInt(data.getStringExtra(DataBaseHelper.COLUMN_AGE))  &&
                                studentLinkedList.get(i).birthday.equals(data.getStringExtra(DataBaseHelper.COLUMN_BIRTHDAY)) &&
                                studentLinkedList.get(i).bloodType.equals(data.getStringExtra(DataBaseHelper.COLUMN_BLOODTYPE)) &&
                                studentLinkedList.get(i).className.equals(data.getStringExtra(DataBaseHelper.COLUMN_CLASS)) &&
                                studentLinkedList.get(i).mainTeacher.equals(data.getStringExtra(DataBaseHelper.COLUMN_CLASSROOMTEACHER)) &&
                                studentLinkedList.get(i).height==Integer.parseInt(data.getStringExtra(DataBaseHelper.COLUMN_HEIGHT)) &&
                                studentLinkedList.get(i).weight==Integer.parseInt(data.getStringExtra(DataBaseHelper.COLUMN_WEIGHT)) &&
                                studentLinkedList.get(i).sex.equals(data.getStringExtra(DataBaseHelper.COLUMN_SEX)) &&
                                studentLinkedList.get(i).someInfo.equals(data.getStringExtra(DataBaseHelper.COLUMN_SOME_INFO))){

                            studentLinkedList.remove(i);
                            break;

                        }
                    }
                    arrayAdapter.notifyDataSetChanged();
                }
                else{
                    database.delete(DataBaseHelper.TABLE_NAME,
                            DataBaseHelper.COLUMN_FULLNAME + " = ?",
                            new String[]{data.getStringExtra(DataBaseHelper.COLUMN_FULLNAME)});
                    studentLinkedList.clear();

                    arrayAdapter =new ArrayAdapter<>(getContext(),
                            R.layout.list_item, R.id.student_button, studentLinkedList);
                    students_view.setAdapter(arrayAdapter);
                }

                Toast.makeText(getContext(), "Данные удалены", Toast.LENGTH_SHORT).show();

            }

            else Toast.makeText(getContext(), "Ошибка!", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        database.close();
    }



}
