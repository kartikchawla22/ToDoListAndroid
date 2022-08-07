package net.kartikchawla.todolist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class ToDoListActivity extends AppCompatActivity {
    SharedPreferences sharedPrefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);
         sharedPrefs = getSharedPreferences("ToDoListUser", MODE_PRIVATE);
        String name = sharedPrefs.getString("userName", "");
        setTitle("Welcome " + name);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public void addNewItem(android.view.View view) {
        Intent toDoListIntent = new Intent(view.getContext(), AddItemActivity.class);
        startActivity(toDoListIntent);
    }
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