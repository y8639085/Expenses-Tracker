package com.unnc.zy18717.expensestracker;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private EditText datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // adaptation of portrait or landscape
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT)
            setContentView(R.layout.activity_main);
        else if (orientation == Configuration.ORIENTATION_LANDSCAPE)
            setContentView(R.layout.activity_main_landscape);

        // set calendar
        datePicker = (EditText) findViewById(R.id.datePicker);
        datePicker.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    showDatePickDlg();
                    return true;
                }
                return false;
            }
        });
        datePicker.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b)
                    showDatePickDlg();
            }
        });
    }

    // show date in textview
    protected void showDatePickDlg() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                monthOfYear += 1;
                if (monthOfYear < 10 && dayOfMonth < 10)
                    datePicker.setText(year + "/0" + monthOfYear + "/0" + dayOfMonth);
                else if (monthOfYear < 10 && dayOfMonth >= 10)
                    datePicker.setText(year + "/0" + monthOfYear + "/" + dayOfMonth);
                else if (monthOfYear >= 10 && dayOfMonth < 10)
                    datePicker.setText(year + "/" + monthOfYear + "/0" + dayOfMonth);
                else if (monthOfYear >= 10 && dayOfMonth >= 10)
                    datePicker.setText(year + "/" + monthOfYear + "/" + dayOfMonth);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    // add an item into the database
    public void add(View view) {

        final EditText categoryField = (EditText) findViewById(R.id.categoryPicker);
        String category = categoryField.getText().toString();

        final EditText amountField = (EditText) findViewById(R.id.amountPicker);
        String amount = amountField.getText().toString();

        // all fields are need to be filled
        if (datePicker.getText() == null || category.length() == 0 || amount.length() == 0) {
            Toast.makeText(MainActivity.this, "Input at least one character", Toast.LENGTH_SHORT).show();
            return;
        }
        // put data to contentprovider
        ContentValues newValues = new ContentValues();
        newValues.put(MyProviderContract.DATE, datePicker.getText().toString());
        newValues.put(MyProviderContract.CATEGORY, category);
        newValues.put(MyProviderContract.AMOUNT, amount);
        getContentResolver().insert(MyProviderContract.EXPENSES_URI, newValues);
        Intent intent = new Intent(MainActivity.this, ContentProviderUser.class);
        startActivity(intent);
    }

    // browse all items in database
    public void browse(View view) {
        Intent intent = new Intent(MainActivity.this, ContentProviderUser.class);
        startActivity(intent);
    }
}