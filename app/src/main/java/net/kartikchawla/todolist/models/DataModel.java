package net.kartikchawla.todolist.models;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import net.kartikchawla.todolist.MainActivity;
import net.kartikchawla.todolist.ToDoItemFragment;
import net.kartikchawla.todolist.ToDoListActivity;


public class DataModel extends SQLiteOpenHelper {
    Context context;
    SQLiteDatabase db;
    public DataModel(Context context) {
        super(context, Constants.TO_DO_LIST_DB, null, Constants.VERSION);
        System.out.println(context);
        this.context = context;
        db = this.getWritableDatabase();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "create table if not exists " + Constants.TO_DO_LIST_TABLE + "(id integer primary key autoincrement, date text, time text, description text)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "drop table if exists " + Constants.TO_DO_LIST_TABLE + "";
        db.execSQL(query);
        onCreate(db);
    }

    public void addData(String description, String date, String time) {
        db = this.getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put(Constants.DATE_COLUMN, date);
        args.put(Constants.TIME_COLUMN, time);
        args.put(Constants.DESCRIPTION_COLUMN, description);
        Long insertQuery = db.insert(Constants.TO_DO_LIST_TABLE, null, args);
        Toast.makeText(context, "New Entry Done!", Toast.LENGTH_LONG).show();
    }
    public Cursor readData() {
        System.out.println("check this1 1 1 1 1  1");
        Cursor cursor = db.rawQuery("SELECT " + Constants.ID_COLUMN + ", " + Constants.DESCRIPTION_COLUMN + ", " + Constants.DATE_COLUMN + ", " + Constants.TIME_COLUMN + " FROM " + Constants.TO_DO_LIST_TABLE, null);
        return cursor;
    }

    static class Constants {
        private static final String TO_DO_LIST_DB = "ToDoListDB";
        private static final String TO_DO_LIST_TABLE = "ToDoListTable";
        private static final String DATE_COLUMN = "date";
        private static final String DESCRIPTION_COLUMN = "description";
        private static final String TIME_COLUMN = "time";
        private static final String ID_COLUMN = "id";
        private static final int VERSION = 1;
    }

}
