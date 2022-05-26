package com.example.perfectproject;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_FIRST_USER;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import java.util.LinkedList;

public class SearchActivityFragment extends Fragment {

    EditText search_bar;
    ListView listViewForSearch;
    DataBaseHelper dataBaseHelper;
    SQLiteDatabase database;
    Cursor cursor;
    SimpleCursorAdapter adapter;
    LinkedList<Student> studentLinkedList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_activity, null);
        search_bar = getActivity().findViewById(R.id.search_bar);
        listViewForSearch = view.findViewById(R.id.search_list);
        studentLinkedList = new LinkedList<>();

        dataBaseHelper = new DataBaseHelper(getContext());
        database = dataBaseHelper.getWritableDatabase();
        String title[] = new String[]{DataBaseHelper.COLUMN_FULLNAME, DataBaseHelper.COLUMN_BIRTHDAY};

        cursor=database.query(DataBaseHelper.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);

        if(cursor.moveToFirst()) {
            do {
                studentLinkedList.add(new Student(
                        cursor.getString((int) cursor.getColumnIndex(DataBaseHelper.COLUMN_FULLNAME)),
                        cursor.getInt((int) cursor.getColumnIndex(DataBaseHelper.COLUMN_CLASS)),
                        cursor.getString((int) cursor.getColumnIndex(DataBaseHelper.COLUMN_CLASSROOMTEACHER)),
                        cursor.getInt((int) cursor.getColumnIndex(DataBaseHelper.COLUMN_HEIGHT)),
                        cursor.getInt((int) cursor.getColumnIndex(DataBaseHelper.COLUMN_WEIGHT)),
                        cursor.getString((int) cursor.getColumnIndex(DataBaseHelper.COLUMN_BIRTHDAY)),
                        cursor.getString((int) cursor.getColumnIndex(DataBaseHelper.COLUMN_SEX)),
                        cursor.getInt((int) cursor.getColumnIndex(DataBaseHelper.COLUMN_AGE)),
                        cursor.getString((int) cursor.getColumnIndex(DataBaseHelper.COLUMN_BLOODTYPE)),
                        cursor.getString((int) cursor.getColumnIndex(DataBaseHelper.COLUMN_SOME_INFO))));
            } while (cursor.moveToNext());
            cursor.moveToFirst();
        }

        adapter = new SimpleCursorAdapter(getContext(), R.layout.list_item_for_search, cursor,
                title, new int[]{R.id.search_item_name, R.id.search_item_birthday}, 0);

        if(!search_bar.getText().toString().isEmpty())
            adapter.getFilter().filter(search_bar.getText().toString());

        search_bar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter.getFilter().filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        adapter.setFilterQueryProvider(new FilterQueryProvider() {
            @Override
            public Cursor runQuery(CharSequence charSequence) {

                if(charSequence == null || charSequence.length() ==0){
                    return database.query(DataBaseHelper.TABLE_NAME,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null);
                }
                else{
                    return database.rawQuery("select * from " + DataBaseHelper.TABLE_NAME +
                                    " where " + DataBaseHelper.COLUMN_FULLNAME + " like ?",
                            new String[]{"%" + charSequence.toString() + "%"});
                }


            }
        });

        listViewForSearch.setAdapter(adapter);


        listViewForSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                DialogFragment newStudentDialog = new NewStudentActivity();
                newStudentDialog.setTargetFragment(SearchActivityFragment.this, 1);

                Bundle args = new Bundle();
                args.putInt("flag", 1);
                TextView nametxt = view.findViewById(R.id.search_item_name);
                TextView birthdaytxt = view.findViewById(R.id.search_item_birthday);
                for (int j = 0; j < studentLinkedList.size(); j++) {
                    if(nametxt.getText().toString().equals(studentLinkedList.get(j).fullName) &&
                            birthdaytxt.getText().toString().equals(studentLinkedList.get(j).birthday)){
                        args.putString(DataBaseHelper.COLUMN_FULLNAME, studentLinkedList.get(j).fullName);
                        args.putInt(DataBaseHelper.COLUMN_AGE, studentLinkedList.get(j).age);
                        args.putString(DataBaseHelper.COLUMN_BIRTHDAY, studentLinkedList.get(j).birthday);
                        args.putString(DataBaseHelper.COLUMN_BLOODTYPE, studentLinkedList.get(j).bloodType);
                        args.putInt(DataBaseHelper.COLUMN_CLASS, studentLinkedList.get(j).className);
                        args.putString(DataBaseHelper.COLUMN_CLASSROOMTEACHER, studentLinkedList.get(j).mainTeacher);
                        args.putInt(DataBaseHelper.COLUMN_HEIGHT, studentLinkedList.get(j).height);
                        args.putInt(DataBaseHelper.COLUMN_WEIGHT, studentLinkedList.get(j).weight);
                        args.putString(DataBaseHelper.COLUMN_SEX, studentLinkedList.get(j).sex);
                        args.putString(DataBaseHelper.COLUMN_SOME_INFO, studentLinkedList.get(j).someInfo);

                        newStudentDialog.setArguments(args);
                        newStudentDialog.show(getFragmentManager(), "getNewStudent");
                        break;
                    }

                }





            }
        });

        return view;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

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
                value.put(DataBaseHelper.COLUMN_CLASS, Integer.parseInt(data.getStringExtra(DataBaseHelper.COLUMN_CLASS)));
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
                        new String[]{data.getStringExtra(DataBaseHelper.COLUMN_FULLNAME+"0"),
                                (data.getStringExtra(DataBaseHelper.COLUMN_AGE+"0")),
                                data.getStringExtra(DataBaseHelper.COLUMN_BIRTHDAY+"0"),
                                data.getStringExtra(DataBaseHelper.COLUMN_BLOODTYPE+"0"),
                                data.getStringExtra(DataBaseHelper.COLUMN_CLASS+"0"),
                                data.getStringExtra(DataBaseHelper.COLUMN_CLASSROOMTEACHER+"0"),
                                data.getStringExtra(DataBaseHelper.COLUMN_HEIGHT+"0"),
                                data.getStringExtra(DataBaseHelper.COLUMN_WEIGHT+"0"),
                                data.getStringExtra(DataBaseHelper.COLUMN_SEX+"0"),
                                data.getStringExtra(DataBaseHelper.COLUMN_SOME_INFO+"0")});

                for (int i = 0; i < studentLinkedList.size(); i++) {
                    if(studentLinkedList.get(i).fullName.equals(data.getStringExtra(DataBaseHelper.COLUMN_FULLNAME+"0")) &&
                            studentLinkedList.get(i).age==Integer.parseInt(data.getStringExtra(DataBaseHelper.COLUMN_AGE+"0"))  &&
                            studentLinkedList.get(i).birthday.equals(data.getStringExtra(DataBaseHelper.COLUMN_BIRTHDAY+"0")) &&
                            studentLinkedList.get(i).bloodType.equals(data.getStringExtra(DataBaseHelper.COLUMN_BLOODTYPE+"0")) &&
                            studentLinkedList.get(i).className==Integer.parseInt(data.getStringExtra(DataBaseHelper.COLUMN_CLASS+"0")) &&
                            studentLinkedList.get(i).mainTeacher.equals(data.getStringExtra(DataBaseHelper.COLUMN_CLASSROOMTEACHER+"0")) &&
                            studentLinkedList.get(i).height==Integer.parseInt(data.getStringExtra(DataBaseHelper.COLUMN_HEIGHT+"0")) &&
                            studentLinkedList.get(i).weight==Integer.parseInt(data.getStringExtra(DataBaseHelper.COLUMN_WEIGHT+"0")) &&
                            studentLinkedList.get(i).sex.equals(data.getStringExtra(DataBaseHelper.COLUMN_SEX+"0")) &&
                            studentLinkedList.get(i).someInfo.equals(data.getStringExtra(DataBaseHelper.COLUMN_SOME_INFO+"0"))){

                        //TODO Сделать изменения в листе и адапетере
                        studentLinkedList.set(i, new Student(data.getStringExtra(DataBaseHelper.COLUMN_FULLNAME),
                                Integer.parseInt(data.getStringExtra(DataBaseHelper.COLUMN_CLASS)),
                                data.getStringExtra(DataBaseHelper.COLUMN_CLASSROOMTEACHER),
                                Integer.parseInt(data.getStringExtra(DataBaseHelper.COLUMN_HEIGHT)),
                                Integer.parseInt(data.getStringExtra(DataBaseHelper.COLUMN_WEIGHT)),
                                data.getStringExtra(DataBaseHelper.COLUMN_BIRTHDAY),
                                data.getStringExtra(DataBaseHelper.COLUMN_SEX),
                                Integer.parseInt(data.getStringExtra(DataBaseHelper.COLUMN_AGE)),
                                data.getStringExtra(DataBaseHelper.COLUMN_BLOODTYPE),
                                data.getStringExtra(DataBaseHelper.COLUMN_SOME_INFO)));
                        break;

                    }
                }

                Toast.makeText(getContext(), "Данные изменены", Toast.LENGTH_SHORT).show();
                adapter.getFilter().filter(search_bar.getText().toString());

            }
            else if(resultCode == RESULT_CANCELED){
                //Код для удаления
                if(studentLinkedList.size()!=1) {
                    database.delete(DataBaseHelper.TABLE_NAME,
                            whereClause,
                            new String[]{data.getStringExtra(DataBaseHelper.COLUMN_FULLNAME+"0"),
                                    (data.getStringExtra(DataBaseHelper.COLUMN_AGE+"0")),
                                    data.getStringExtra(DataBaseHelper.COLUMN_BIRTHDAY+"0"),
                                    data.getStringExtra(DataBaseHelper.COLUMN_BLOODTYPE+"0"),
                                    data.getStringExtra(DataBaseHelper.COLUMN_CLASS+"0"),
                                    data.getStringExtra(DataBaseHelper.COLUMN_CLASSROOMTEACHER+"0"),
                                    data.getStringExtra(DataBaseHelper.COLUMN_HEIGHT+"0"),
                                    data.getStringExtra(DataBaseHelper.COLUMN_WEIGHT+"0"),
                                    data.getStringExtra(DataBaseHelper.COLUMN_SEX+"0"),
                                    data.getStringExtra(DataBaseHelper.COLUMN_SOME_INFO+"0")});


                    for (int i = 0; i < studentLinkedList.size(); i++) {
                        if(studentLinkedList.get(i).fullName.equals(data.getStringExtra(DataBaseHelper.COLUMN_FULLNAME+"0")) &&
                                studentLinkedList.get(i).age==Integer.parseInt(data.getStringExtra(DataBaseHelper.COLUMN_AGE+"0")) &&
                                studentLinkedList.get(i).birthday.equals(data.getStringExtra(DataBaseHelper.COLUMN_BIRTHDAY+"0")) &&
                                studentLinkedList.get(i).bloodType.equals(data.getStringExtra(DataBaseHelper.COLUMN_BLOODTYPE+"0")) &&
                                studentLinkedList.get(i).className==Integer.parseInt(data.getStringExtra(DataBaseHelper.COLUMN_CLASS+"0")) &&
                                studentLinkedList.get(i).mainTeacher.equals(data.getStringExtra(DataBaseHelper.COLUMN_CLASSROOMTEACHER+"0")) &&
                                studentLinkedList.get(i).height==Integer.parseInt(data.getStringExtra(DataBaseHelper.COLUMN_HEIGHT+"0")) &&
                                studentLinkedList.get(i).weight==Integer.parseInt(data.getStringExtra(DataBaseHelper.COLUMN_WEIGHT+"0")) &&
                                studentLinkedList.get(i).sex.equals(data.getStringExtra(DataBaseHelper.COLUMN_SEX+"0")) &&
                                studentLinkedList.get(i).someInfo.equals(data.getStringExtra(DataBaseHelper.COLUMN_SOME_INFO+"0"))){

                            studentLinkedList.remove(i);
                            break;

                        }
                    }
                    adapter.getFilter().filter(search_bar.getText().toString());
                }
                else{
                    database.delete(DataBaseHelper.TABLE_NAME,
                            whereClause,
                            new String[]{data.getStringExtra(DataBaseHelper.COLUMN_FULLNAME+"0"),
                                    (data.getStringExtra(DataBaseHelper.COLUMN_AGE+"0")),
                                    data.getStringExtra(DataBaseHelper.COLUMN_BIRTHDAY+"0"),
                                    data.getStringExtra(DataBaseHelper.COLUMN_BLOODTYPE+"0"),
                                    data.getStringExtra(DataBaseHelper.COLUMN_CLASS+"0"),
                                    data.getStringExtra(DataBaseHelper.COLUMN_CLASSROOMTEACHER+"0"),
                                    data.getStringExtra(DataBaseHelper.COLUMN_HEIGHT+"0"),
                                    data.getStringExtra(DataBaseHelper.COLUMN_WEIGHT+"0"),
                                    data.getStringExtra(DataBaseHelper.COLUMN_SEX+"0"),
                                    data.getStringExtra(DataBaseHelper.COLUMN_SOME_INFO+"0")});
                    studentLinkedList.clear();
                    adapter.getFilter().filter(search_bar.getText().toString());
                }

                Toast.makeText(getContext(), "Данные удалены", Toast.LENGTH_SHORT).show();

            }

            else Toast.makeText(getContext(), "Ошибка!", Toast.LENGTH_SHORT).show();
        }



    @Override
    public void onStop() {
        super.onStop();
        database.close();
        cursor.close();
    }


}
