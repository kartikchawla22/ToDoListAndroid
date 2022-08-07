package net.kartikchawla.todolist;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import net.kartikchawla.todolist.models.DataModel;

import java.util.Calendar;

public class DetailsViewActivity extends AppCompatActivity {

    public String selectedID;
    DataModel dataModel;
    SharedPreferences sharedPrefs;
    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    boolean response = dataModel.deleteItem(Integer.parseInt(selectedID), sharedPrefs);
                    if (response) {
                        Toast.makeText(DetailsViewActivity.this, "Item Deleted Successfully!", Toast.LENGTH_SHORT).show();
                        Intent toDoListIntent = new Intent(DetailsViewActivity.this, ToDoListActivity.class);
                        toDoListIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(toDoListIntent);
                        finish();
                    }
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    //No button clicked
                    break;
            }
        }
    };
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private EditText dateTextField;
    private EditText timeTextField;
    private EditText descriptionTextField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_view);
        dataModel = new DataModel(this);
        sharedPrefs = getSharedPreferences("ToDoListUser", MODE_PRIVATE);
        dateTextField = findViewById(R.id.datePickerInDetailsView);
        timeTextField = findViewById(R.id.timePickerInDetailsView);
        descriptionTextField = findViewById(R.id.descriptionInDetailsView);


        selectedID = getIntent().getStringExtra("id");

        Cursor data = dataModel.fetchItem(Integer.parseInt(selectedID), sharedPrefs);


        data.moveToFirst();
        String date, time, description;
        description = data.getString(1);
        date = data.getString(2);
        time = data.getString(3);


        dateTextField.setText(date);
        timeTextField.setText(time);
        descriptionTextField.setText(description);


        String name = sharedPrefs.getString("userName", "");
        setTitle("Hello, " + name);

    }

    public void openCalendar(android.view.View view) {
        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        // date picker dialog
        datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePickerView, int year, int monthOfYear, int dayOfMonth) {
                        ((TextView) view).setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                }, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    public void openTimePicker(android.view.View view) {
        final Calendar cldr = Calendar.getInstance();
        int hour = cldr.get(Calendar.HOUR_OF_DAY);
        int minute = cldr.get(Calendar.MINUTE);
        // date picker dialog
        timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePickerView, int hour, int minute) {
                        ((TextView) view).setText(hour + ":" + (minute < 10 ? "0" + minute : minute));
                    }
                }, hour, minute, true);
        timePickerDialog.show();
    }

    public void deleteItemUsingId(android.view.View view) {
        System.out.println("inside delete");
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setMessage("Confirm Delete?").setPositiveButton("Yes", dialogClickListener).setNegativeButton("No", dialogClickListener).show();
    }

    public void updateItemUsingId(android.view.View view) {
        boolean result = dataModel.updateItem(
                Integer.parseInt(selectedID),
                descriptionTextField.getText().toString(),
                dateTextField.getText().toString(),
                timeTextField.getText().toString(),
                sharedPrefs
        );
        if (result) {
            Toast.makeText(this, "Item Updated Successfully!", Toast.LENGTH_SHORT).show();
            Intent toDoListintent = new Intent(this, ToDoListActivity.class);
            toDoListintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(toDoListintent);
            finish();
        }
    }
}