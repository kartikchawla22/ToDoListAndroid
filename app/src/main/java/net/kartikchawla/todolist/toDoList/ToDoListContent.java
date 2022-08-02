package net.kartikchawla.todolist.toDoList;

import android.database.Cursor;

import net.kartikchawla.todolist.models.DataModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class ToDoListContent {

    /**
     * An array of sample (placeholder) items.
     */
    public static final List<ToDoListItem> ITEMS = new ArrayList<ToDoListItem>();

    private static int COUNT = 0;

    private static void addItem(ToDoListItem item) {
        ITEMS.add(item);
    }
    public static void makeToDoList(Cursor data) {
        ITEMS.clear();
        COUNT = data.getCount();
        data.moveToFirst();
        String date, time, description;
        Integer id;
        do {
            id = data.getInt(0);
            description = data.getString(1);
            date = data.getString(2);
            time = data.getString(3);
            description = description.split("\n")[0];
            description = description.substring(0, Math.min(description.length(), 10)) + (description.length() > 10 ?  "..." : "");
            ToDoListItem item =  new ToDoListItem(id.toString(), description, date + " at " + time);
            addItem(item);
        }while (data.moveToNext());

    }

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