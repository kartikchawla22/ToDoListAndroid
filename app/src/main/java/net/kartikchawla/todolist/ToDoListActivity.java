package net.kartikchawla.todolist;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class ToDoListActivity extends AppCompatActivity {
    String name = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);
         name = getIntent().getStringExtra("name");
        setTitle("Welcome " + name);
    }

    @Override
    protected void onResume(){
        super.onResume();

    }
    public void addNewItem(android.view.View view) {
        Intent toDoListIntent = new Intent(view.getContext(), AddItemActivity.class);
        toDoListIntent.putExtra("name", name);
        startActivity(toDoListIntent);
    }
}