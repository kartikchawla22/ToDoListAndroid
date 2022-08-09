package net.kartikchawla.todolist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class ToDoListActivity extends AppCompatActivity {
    /**
     * Class variables
     * sharedPrefs is used to manage data of logged in user
     */
    SharedPreferences sharedPrefs;

    /**
     * This method is used to initialize class variables
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);
        sharedPrefs = getSharedPreferences("ToDoListUser", MODE_PRIVATE);
        String name = sharedPrefs.getString("userName", "");
        setTitle("Welcome " + name);
    }

    /**
     * This method is called whenever this activity comes into view
     */
    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * This method is called when user clicks on add button
     *
     * @param view
     */

    public void addNewItem(android.view.View view) {
        Intent toDoListIntent = new Intent(view.getContext(), AddItemActivity.class);
        startActivity(toDoListIntent);
    }

    /**
     * This method is called when user clicks on logout button.
     * This clears the logged in user data.
     *
     * @param view
     */
    public void logoutUser(android.view.View view) {
        SharedPreferences.Editor sharedPrefsEditor = sharedPrefs.edit();
        sharedPrefsEditor.remove("userName");
        sharedPrefsEditor.remove("userEmail");
        sharedPrefsEditor.apply();
        Intent mainActivityIntent = new Intent(getApplicationContext(), MainActivity.class);
        mainActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(mainActivityIntent);
        finish();
    }
}