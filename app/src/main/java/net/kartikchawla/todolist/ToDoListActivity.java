package net.kartikchawla.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class ToDoListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);
        String name = getIntent().getStringExtra("name");
        setTitle("Welcome " + name);
        System.out.println(name);
    }
    public void addNewItem(android.view.View view) {
        Intent toDoListIntent = new Intent(view.getContext(), AddItemActivity.class);
        startActivity(toDoListIntent);
    }
}