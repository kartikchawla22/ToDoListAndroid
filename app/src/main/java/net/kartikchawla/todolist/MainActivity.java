package net.kartikchawla.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void loginUser(android.view.View view) {
        Intent toDoListIntent = new Intent(view.getContext(), ToDoListActivity.class);
        startActivity(toDoListIntent);
    }
}