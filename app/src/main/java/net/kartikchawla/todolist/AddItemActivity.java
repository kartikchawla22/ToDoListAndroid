package net.kartikchawla.todolist;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import net.kartikchawla.todolist.models.DataModel;

import java.util.Calendar;

public class AddItemActivity extends AppCompatActivity {

    String name = "";
    private DataModel dataModel;
    private EditText dateTextField;
    private EditText timeTextField;
    private EditText descriptionTextField;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        dataModel = new DataModel(this);
        dateTextField = findViewById(R.id.datePicker);
        timeTextField = findViewById(R.id.timePicker);
        descriptionTextField = findViewById(R.id.description);
        name = getIntent().getStringExtra("name");
        setTitle("Welcome " + name);

    }

    public void addItem(android.view.View view) {
        dataModel.addData(descriptionTextField.getText().toString(), dateTextField.getText().toString(), timeTextField.getText().toString());
        Intent toDoListIntent = new Intent(getApplicationContext(), ToDoListActivity.class);
        toDoListIntent.putExtra("name", name);
        toDoListIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(toDoListIntent);
        finish();
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
                        ((TextView) view).setText(hour + ":" + (minute));
                    }
                }, hour, minute, true);
        timePickerDialog.show();
    }
}