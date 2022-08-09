package net.kartikchawla.todolist.toDoList;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

public class ToDoListContent {
    /**
     * Class variables. using static so that we don't need to create object of class.
     */

    public static final List<ToDoListItem> ITEMS = new ArrayList<ToDoListItem>();

    private static int COUNT = 0;

    /**
     * This method is used to update the ITEMS variable.
     *
     * @param item
     */

    private static void addItem(ToDoListItem item) {
        ITEMS.add(item);
    }

    /**
     * This method is used to read the data from cursor object and pass it along by converting the raw data into a particular format{ToDoListItem}.
     *
     * @param data
     */

    public static void makeToDoList(Cursor data) {
        ITEMS.clear();
        COUNT = data.getCount();
        if (COUNT > 0) {
            data.moveToFirst();
            String date, time, description;
            Integer id;
            do {
                id = data.getInt(0);
                date = data.getString(1);
                time = data.getString(2);
                description = data.getString(3);
                description = description.split("\n")[0];
                description = description.substring(0, Math.min(description.length(), 10)) + (description.length() > 10 ? "..." : "");
                ToDoListItem item = new ToDoListItem(id.toString(), description, date + " at " + time);
                addItem(item);
            } while (data.moveToNext());
        }
    }

    /**
     * Static class so that we won't need to make the object of class.
     * used to format the raw data read using cursor.
     */
    public static class ToDoListItem {
        public final String id;
        public final String content;
        public final String dateTime;

        public ToDoListItem(String id, String content, String dateTime) {
            this.id = id;
            this.content = content;
            this.dateTime = dateTime;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}