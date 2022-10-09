package com.example.crudsqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    //Database Version
    private static final int DB_VERSION = 1;

    //Database Name
    private static final String DB_NAME = "LIST";

    //ListTodo Table Name
    private static final String TABLE_DATA = "DATA";

    private static final String KEY_ID = "id";
    private static final String TODO_NAME = "TODO_NAME";


    public DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tableCountry = " CREATE TABLE "+TABLE_DATA+" ("+
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                TODO_NAME + " TEXT) ";

        db.execSQL(tableCountry);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS "+TABLE_DATA);
        onCreate(db);
    }

    public List<ListTodo> getAllCountries(){

        List<ListTodo> listTodoList = new ArrayList<>();

        String allCountry = "SELECT * FROM "+TABLE_DATA;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(allCountry, null);

        if(cursor.moveToFirst()){
            do{
                ListTodo listTodo = new ListTodo();
                listTodo.setId(Integer.parseInt(cursor.getString(0)));
                listTodo.setData(cursor.getString(1));

                listTodoList.add(listTodo);

            }while (cursor.moveToNext());
        }

        return listTodoList;

    }

    void addCountry(ListTodo listTodo){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TODO_NAME, listTodo.getData());


        Log.i("DBHELPER ", listTodo.getData());

        db.insert(TABLE_DATA, null, values);
        db.close();

    }

    void delete(ListTodo listTodo){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DATA,TODO_NAME + "=?",new String[]{listTodo.getData()});
        db.close();
    }

}
