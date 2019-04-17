package com.unnc.zy18717.expensestracker;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
        setContentView(R.layout.activity_main);

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
                if (b) {
                    showDatePickDlg();
                }
            }
        });
    }

    protected void showDatePickDlg() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                datePicker.setText(year + "/" + monthOfYear + "/" + dayOfMonth);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    public void add(View view) {

        final EditText categoryField = (EditText) findViewById(R.id.categoryPicker);
        String category = categoryField.getText().toString();

        final EditText amountField = (EditText) findViewById(R.id.amountPicker);
        String amount = amountField.getText().toString();

        if (datePicker.getText() == null || category.length() == 0 || amount.length() == 0) {
            Toast.makeText(MainActivity.this, "Input at least one character", Toast.LENGTH_SHORT).show();
            return;
        }
        ContentValues newValues = new ContentValues();
        newValues.put(MyProviderContract.DATE, datePicker.getText().toString());
        newValues.put(MyProviderContract.CATEGORY, category);
        newValues.put(MyProviderContract.AMOUNT, amount);

        getContentResolver().insert(MyProviderContract.EXPENSES_URI, newValues);
    }

    public void contentProvider2(View view) {
        Intent intent = new Intent(MainActivity.this, ContentProviderUser.class);
        startActivity(intent);
    }
}