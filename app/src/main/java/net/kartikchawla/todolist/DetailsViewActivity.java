package net.kartikchawla.todolist;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class DetailsViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_view);
        String id = getIntent().getStringExtra("id");
        System.out.println((id));
    }
}