package net.kartikchawla.todolist;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;



import androidx.appcompat.app.AppCompatActivity;

import net.kartikchawla.todolist.models.DataModel;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class AddItemActivity extends AppCompatActivity {

    String name = "";
    private DataModel dataModel;
    private EditText dateTextField;
    private EditText timeTextField;
    private EditText descriptionTextField;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private Date todayDate;

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
        String description=descriptionTextField.getText().toString();
         String date=dateTextField.getText().toString();
         String time=timeTextField.getText().toString();
        if (!TextUtils.isEmpty(description) && !TextUtils.isEmpty(date) && !TextUtils.isEmpty(time)) {
                dataModel.addData(description, date, time);
                Intent toDoListIntent = new Intent(getApplicationContext(), ToDoListActivity.class);
                toDoListIntent.putExtra("name", name);
                toDoListIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(toDoListIntent);
                finish();

        }
        else {
            Toast.makeText(AddItemActivity.this, "Fields Mandatory: Please enter data !", Toast.LENGTH_SHORT).show();
        }



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
    private String currentTime(){
        LocalTime myDateObj = LocalTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("HH:MM");
        String formattedTime = myDateObj.format(myFormatObj);
        return formattedTime;
    }
    private String currentDate(){
        LocalDate myDateObj = LocalDate.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formatted = myDateObj.format(myFormatObj);
        return formatted;
    }
}