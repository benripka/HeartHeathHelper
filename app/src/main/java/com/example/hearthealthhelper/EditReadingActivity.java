package com.example.hearthealthhelper;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EditReadingActivity extends AppCompatActivity {

    private EditText heartRateField, systolicPressureField, diastolicPressureField, commentField, dateField, timeField;
    private FloatingActionButton submitReadingButton, cancelButton;
    private Date dateMeasured;
    private Reading reading;
    private Boolean editing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_reading_activity);

        initializeComponents();

        Bundle extras = getIntent().getExtras();

        // Check to see if editing an existing reading or creating a new one
        if(extras != null) {
            reading = (Reading) extras.getSerializable("reading");
            dateMeasured = reading.getDateMeasured();
            setComponentValues(reading);
            editing = true;
        } else {
            dateMeasured = new Date();
        }

        setupTimePicker();
        setupDatePicker();

        setUpClickListeners();
    }

    private void initializeComponents() {
        this.heartRateField = findViewById(R.id.heart_rate_field);
        this.systolicPressureField = findViewById(R.id.systolic_pressure_field);
        this.diastolicPressureField = findViewById(R.id.diastolic_pressure_field);
        this.commentField = findViewById(R.id.comment_field);
        this.dateField = findViewById(R.id.date_field);
        this.timeField = findViewById(R.id.time_field);
        this.submitReadingButton = findViewById(R.id.submit_reading_button);
        this.cancelButton = findViewById(R.id.cancel_button);
    }

    private void setupTimePicker() {
        timeField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(EditReadingActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime(dateMeasured);
                                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                calendar.set(Calendar.MINUTE, minute);
                                dateMeasured = calendar.getTime();
                                SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
                                timeField.setText(formatter.format(dateMeasured));
                            }
                        }, 1, 1, true);
                timePickerDialog.show();
            }
        });
    }

    private void setupDatePicker() {
        dateField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(dateMeasured);

                int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
                int currentMonth = calendar.get(Calendar.MONTH);
                int currentYear = calendar.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(EditReadingActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime(dateMeasured);
                                calendar.set(Calendar.YEAR, year);
                                calendar.set(Calendar.MONTH, month);
                                calendar.set(Calendar.DAY_OF_MONTH, day);
                                dateMeasured = calendar.getTime();
                                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                                dateField.setText(formatter.format(dateMeasured));
                            }
                        }, currentYear, currentMonth, currentDay);
                datePickerDialog.show();
            }
        });
    }

    // Setup the submit and cancel floating point button click listeners
    private void setUpClickListeners() {
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        submitReadingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptSubmission();
            }
        });
    }

    // Initialize the values of each field
    private void setComponentValues(Reading reading) {

        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateFormatter.format(dateMeasured);
        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm");
        String time = timeFormatter.format(dateMeasured);

        heartRateField.setText(String.valueOf(reading.getHeartRate()));
        systolicPressureField.setText(String.valueOf(reading.getSystolicPressure()));
        diastolicPressureField.setText(String.valueOf(reading.getDiastolicPressure()));
        commentField.setText(reading.getComment());
        dateField.setText(date);
        timeField.setText(time);
    }

    private void attemptSubmission() {

        int heartRate = 0;
        int systolicPressure = 0;
        int diastolicPressure = 0;
        String comment;
        Boolean error = false;

        if(heartRateField.getText().toString().trim().isEmpty()) {
            heartRateField.setError("Heart rate is a mandatory field");
            error = true;
        } else {
            heartRate = Integer.parseInt(heartRateField.getText().toString().trim());
        }

        if(systolicPressureField.getText().toString().trim().isEmpty()) {
            systolicPressureField.setError("Systolic pressure is a mandatory field");
            error = true;
        } else {
            systolicPressure = Integer.parseInt(systolicPressureField.getText().toString().trim());
        }

        if(diastolicPressureField.getText().toString().trim().isEmpty()) {
            diastolicPressureField.setError("Diastolic pressure is a mandatory field");
            error = true;
        } else {
            diastolicPressure = Integer.parseInt(diastolicPressureField.getText().toString().trim());
        }

        comment = commentField.getText().toString().trim();

        if(error) {
            Toast.makeText(this, "An error occured, please review submisison.", Toast.LENGTH_SHORT).show();
            return;
        } else if(editing) {
            reading.setSystolicPressure(systolicPressure);
            reading.setComment(comment);
            reading.setDateMeasured(dateMeasured);
            reading.setDiastolicPressure(diastolicPressure);
            reading.setHeartRate(heartRate);
            saveReading();
        } else {
            reading = new Reading(dateMeasured, systolicPressure, diastolicPressure, heartRate, comment);
            saveReading();
        }
    }

    private void saveReading() {

        Intent resultIntent = new Intent();
        resultIntent.putExtra("reading", reading);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }
}
