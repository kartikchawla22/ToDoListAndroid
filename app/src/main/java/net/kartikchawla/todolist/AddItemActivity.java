package net.kartikchawla.todolist;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import net.kartikchawla.todolist.models.DataModel;

import java.util.Calendar;

public class AddItemActivity extends AppCompatActivity {

    /**
     * Class variables
     * sharedPrefs is used to handle logged in user data.
     * dataModel is an object of DataModel Class.
     * dateTextField, timeTextField and descriptionTextField are the text fields shown in the view.
     * datePickerDialog and timePickerDialog are the dialogues that open when we want to select data / time.
     */

    SharedPreferences sharedPrefs;
    private DataModel dataModel;
    private EditText dateTextField;
    private EditText timeTextField;
    private EditText descriptionTextField;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;

    /**
     * This method is called when this class comes into the memory.
     * This is called just after the constructor (if this class had one).
     * This is used to initialize all class variables.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        dataModel = new DataModel(this);
        dateTextField = findViewById(R.id.datePicker);
        timeTextField = findViewById(R.id.timePicker);
        descriptionTextField = findViewById(R.id.description);
        sharedPrefs = getSharedPreferences("ToDoListUser", MODE_PRIVATE);
        String name = sharedPrefs.getString("userName", "");
        setTitle("Hello, " + name);

    }

    /**
     * This method is called when we click on add button.
     * This is will the data in database and take the user back to list page.
     *
     * @param view
     */

    public void addItem(android.view.View view) {
        String description = descriptionTextField.getText().toString();
        String date = dateTextField.getText().toString();
        String time = timeTextField.getText().toString();
        if (!TextUtils.isEmpty(description) && !TextUtils.isEmpty(date) && !TextUtils.isEmpty(time)) {
            dataModel.addData(description, date, time, sharedPrefs);
            Intent toDoListIntent = new Intent(getApplicationContext(), ToDoListActivity.class);
            toDoListIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(toDoListIntent);
            finish();
        } else {
            Toast.makeText(AddItemActivity.this, "Fields Mandatory: Please enter data !", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * This method is used to open the calendar dialogue.
     *
     * @param view
     */

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

    /**
     * This method is used to open timePicker dialogue.
     *
     * @param view
     */

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
}