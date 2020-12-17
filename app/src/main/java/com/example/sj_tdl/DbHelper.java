package com.example.sj_tdl;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;
import java.util.ArrayList;


public class DbHelper extends SQLiteOpenHelper {
    public static final int VERSION = 2;
    public static final String DB_NAME= "TODO";
    public static final String TABLE_NAME = "ToDo";
    public static final String ID_COLUM = "id";
    public static final String TASK_COLUM = "task";
    public static final String DATE_COLUM = "date";
    public static final String UPDATE_COLUM= "status";

    public DbHelper(Context context){
        super(context, DB_NAME, null, VERSION);}



    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("INFO", "onCreate was called");
        String CREATE_TASK_LIST = "CREATE TABLE "+ TABLE_NAME
                + "(" + ID_COLUM + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                TASK_COLUM + " TEXT, " + DATE_COLUM + " TEXT, " + UPDATE_COLUM + " INTEGER)";
        db.execSQL(CREATE_TASK_LIST);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }

    public ArrayList<item_adapter> listTasks(){
        String dbsql = "select * from " + TABLE_NAME;
        ArrayList<item_adapter> TasksList = new ArrayList<>();
        Cursor cursor = getReadableDatabase().query(TABLE_NAME, new String[]{ID_COLUM, TASK_COLUM, DATE_COLUM, UPDATE_COLUM},null, null,null, null, null);
        if (cursor.moveToFirst()){
            do {
                @SuppressWarnings("StringEquality") item_adapter itemadapter =new item_adapter(
                        cursor.getInt(cursor.getColumnIndex(ID_COLUM)),
                        cursor.getString(cursor.getColumnIndex(TASK_COLUM)),
                        cursor.getString(cursor.getColumnIndex(DATE_COLUM)),
                        cursor.getInt(cursor.getColumnIndex(UPDATE_COLUM))==1);
                TasksList.add(itemadapter);


            }while (cursor.moveToNext());

        }
        cursor.close();
        return TasksList;
    }

    public void updateTask(item_adapter itemadapter){
        ContentValues values = new ContentValues();
        int id = itemadapter.getId();
        int status = 0;
        if (itemadapter.getStatus()){
            status = 1;
        }

        values.put(ID_COLUM, itemadapter.getId());
        values.put(TASK_COLUM, itemadapter.getTask());
        values.put(DATE_COLUM, itemadapter.getDate());
        values.put(UPDATE_COLUM, status);
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_NAME, values, DbHelper.ID_COLUM + "=" + id,null);
    }

    public void addTasks(item_adapter itemadapter){
        ContentValues values = new ContentValues();
        values.put(TASK_COLUM, itemadapter.getTask());
        values.put(DATE_COLUM, itemadapter.getDate());
        values.put(UPDATE_COLUM, false);
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(DbHelper.TABLE_NAME, null, values);
    }

    public void updateStatus(int id, boolean checked){
        ContentValues values = new ContentValues();
        values.put(UPDATE_COLUM, checked);
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_NAME, values, ID_COLUM+ "=?", new String[] {String.valueOf(id)});
    }
    public void deleteTask(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, ID_COLUM +"=?", new String[]{String.valueOf(id)});

    }


}