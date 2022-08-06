package net.kartikchawla.todolist;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import net.kartikchawla.todolist.models.DataModel;

public class DetailsViewActivity extends AppCompatActivity {

    public String EXTRA_TEXT;

    DataModel dataModel;
    private TextView dateTextView;
    private TextView timTextView;
    private TextView descriptionTextView;
    private Button deleteRecord;
    private Button updateRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_view);
        dataModel=new DataModel(this);


        dateTextView = findViewById(R.id.dateView);
        timTextView = findViewById(R.id.timeView);
        descriptionTextView = findViewById(R.id.descriptionView);
        updateRecord = findViewById(R.id.editbtn);
        deleteRecord = findViewById(R.id.deletebtn);

        String id = getIntent().getStringExtra("id");
        EXTRA_TEXT=id;
        Cursor data = dataModel.fetchItem(Integer.parseInt(id));


            data.moveToFirst();
            String date, time, description;
            Integer itemId;
            do {
                itemId = data.getInt(0);
                description = data.getString(1);
                date = data.getString(2);
                time = data.getString(3);
                System.out.println(description+","+date+","+time);
            } while (data.moveToNext());

        dateTextView.setText(date);
        timTextView.setText(time);
        descriptionTextView.setText(description);

        deleteRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
              /*
               boolean response= dataModel.deleteItem(Integer.parseInt(id));
               if (response){
                   Toast.makeText(DetailsViewActivity.this, "Item Deleted Successfully!", Toast.LENGTH_SHORT).show();
                   Intent intent = new Intent(DetailsViewActivity.this, ToDoListActivity.class);
                   startActivity(intent);
                   finish();
               }*/
            }
        });
    }


    public void updatePage(View view) {
        Intent intent = new Intent(DetailsViewActivity.this, updateItem.class);
        intent.putExtra("id", EXTRA_TEXT);
        startActivity(intent);
    }
    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    boolean response= dataModel.deleteItem(Integer.parseInt(EXTRA_TEXT));
                    if (response){
                        Toast.makeText(DetailsViewActivity.this, "Item Deleted Successfully!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(DetailsViewActivity.this, ToDoListActivity.class);
                        startActivity(intent);
                        finish();}
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    //No button clicked
                    break;
            }
        }
    };
}