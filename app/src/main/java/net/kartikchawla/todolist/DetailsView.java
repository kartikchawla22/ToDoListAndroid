package net.kartikchawla.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class DetailsView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_view);
        String id = getIntent().getStringExtra("id");
    }
}