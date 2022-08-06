package net.kartikchawla.todolist.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;


public class DataModel extends SQLiteOpenHelper {
    Context context;
    SQLiteDatabase db;

    public DataModel(Context context) {
        super(context, Constants.TO_DO_LIST_DB, null, Constants.VERSION);
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
        db.insert(Constants.TO_DO_LIST_TABLE, null, args);
        Toast.makeText(context, "New Entry Done!", Toast.LENGTH_LONG).show();
    }

    public Cursor readData() {
        Cursor cursor = db.rawQuery("SELECT " + Constants.ID_COLUMN + ", " + Constants.DESCRIPTION_COLUMN + ", " + Constants.DATE_COLUMN + ", " + Constants.TIME_COLUMN + " FROM " + Constants.TO_DO_LIST_TABLE, null);
        return cursor;
    }
    public Cursor fetchItem(Integer itemId) {

        Cursor cursor = db.rawQuery("SELECT " + Constants.ID_COLUMN + ", " + Constants.DESCRIPTION_COLUMN + ", " + Constants.DATE_COLUMN + ", " + Constants.TIME_COLUMN + " FROM " + Constants.TO_DO_LIST_TABLE+" WHERE "+ Constants.ID_COLUMN +" = " +itemId, null);

        return cursor;
    }
    public boolean updateItem(Integer itemId,String description,String date, String time) {
        ContentValues contentValues =new ContentValues();
        contentValues.put(Constants.DESCRIPTION_COLUMN,description);
        contentValues.put(Constants.DATE_COLUMN,date);
        contentValues.put(Constants.TIME_COLUMN,time);
        db.update(Constants.TO_DO_LIST_TABLE,contentValues,Constants.ID_COLUMN + "=" + itemId, null);
      return true;
    }
    public boolean deleteItem(Integer itemId) {
        return db.delete(Constants.TO_DO_LIST_TABLE, Constants.ID_COLUMN + "=" + itemId, null) > 0;
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
