package net.kartikchawla.todolist.models;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;


public class DataModel extends SQLiteOpenHelper {
    Context context;
    SQLiteDatabase db;

    public DataModel(Context context) {
        super(context, Constants.TO_DO_LIST_DB, null, Constants.VERSION);
        this.context = context;
        System.out.println(context.getDatabasePath(Constants.TO_DO_LIST_DB));
        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "create table if not exists " + Constants.TO_DO_LIST_TABLE + "(id integer primary key autoincrement, date text, time text, description text, email text)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "drop table if exists " + Constants.TO_DO_LIST_TABLE + "";
        db.execSQL(query);
        onCreate(db);
    }
    private String getUserEmail(SharedPreferences sharedPrefs) {
     return sharedPrefs.getString("userEmail", "");
    }

    public void addData(String description, String date, String time, SharedPreferences sharedPrefs) {
        String email = getUserEmail(sharedPrefs);
        db = this.getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put(Constants.DATE_COLUMN, date);
        args.put(Constants.TIME_COLUMN, time);
        args.put(Constants.DESCRIPTION_COLUMN, description);
        args.put(Constants.EMAIL_COLUMN, email);
        db.insert(Constants.TO_DO_LIST_TABLE, null, args);
        Toast.makeText(context, "New Entry Done!", Toast.LENGTH_LONG).show();
    }

    public Cursor readData(SharedPreferences sharedPrefs) {
        String email = getUserEmail(sharedPrefs);
        System.out.println(email);
        Cursor cursor = db.rawQuery("SELECT * FROM " +  Constants.TO_DO_LIST_TABLE + " WHERE " + Constants.EMAIL_COLUMN + " = ?", new String[]{email});
        return cursor;
    }

    public Cursor fetchItem(Integer itemId, SharedPreferences sharedPrefs) {
        String email = getUserEmail(sharedPrefs);
        Cursor cursor = db.rawQuery("SELECT " + Constants.ID_COLUMN + ", " + Constants.DESCRIPTION_COLUMN + ", " + Constants.DATE_COLUMN + ", " + Constants.TIME_COLUMN + " FROM " + Constants.TO_DO_LIST_TABLE + " WHERE " + Constants.ID_COLUMN + " = ? AND " + Constants.EMAIL_COLUMN + " = ?" , new String[]{itemId.toString(), email});
        return cursor;
    }

    public boolean updateItem(Integer itemId, String description, String date, String time, SharedPreferences sharedPrefs) {
        String email = getUserEmail(sharedPrefs);
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.DESCRIPTION_COLUMN, description);
        contentValues.put(Constants.DATE_COLUMN, date);
        contentValues.put(Constants.TIME_COLUMN, time);
        return db.update(Constants.TO_DO_LIST_TABLE, contentValues, Constants.ID_COLUMN + " = ? AND " + Constants.EMAIL_COLUMN + " = ?", new String[]{itemId.toString(), email}) > -1;
    }

    public boolean deleteItem(Integer itemId, SharedPreferences sharedPrefs) {
        String email = getUserEmail(sharedPrefs);
        return db.delete(Constants.TO_DO_LIST_TABLE, Constants.ID_COLUMN + " = ? AND "  + Constants.EMAIL_COLUMN + " = ?", new String[]{itemId.toString(), email}) > -1;
    }

    static class Constants {
        private static final String TO_DO_LIST_DB = "ToDoListDB";
        private static final String TO_DO_LIST_TABLE = "ToDoListTable";
        private static final String DATE_COLUMN = "date";
        private static final String DESCRIPTION_COLUMN = "description";
        private static final String TIME_COLUMN = "time";
        private static final String ID_COLUMN = "id";
        private static final String EMAIL_COLUMN = "email";
        private static final int VERSION = 1;
    }

}
