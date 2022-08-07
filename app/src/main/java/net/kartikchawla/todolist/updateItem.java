package net.kartikchawla.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import net.kartikchawla.todolist.models.DataModel;

import java.util.Calendar;

public class updateItem extends AppCompatActivity {


    private DataModel dataModel;
    private EditText dateTextField;
    private EditText timeTextField;
    private EditText descriptionTextField;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    public String EXTRA_TEXT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_item);


        dataModel = new DataModel(this);
        dateTextField = findViewById(R.id.datePicker);
        timeTextField = findViewById(R.id.timePicker);
        descriptionTextField = findViewById(R.id.description);

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

        dateTextField.setText(date);
        timeTextField.setText(time);
        descriptionTextField.setText(description);

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
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
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

    public void updatItem(View view) {

         boolean result=dataModel.updateItem(
                Integer.parseInt(EXTRA_TEXT),
                descriptionTextField.getText().toString(),
                dateTextField.getText().toString(),
                timeTextField.getText().toString()
                );
        if (result){
            Toast.makeText(updateItem.this, "Item Updated Successfully!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(updateItem.this, ToDoListActivity.class);
            startActivity(intent);
            finish();
        }
    }
}