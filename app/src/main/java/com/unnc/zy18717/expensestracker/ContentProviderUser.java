package com.unnc.zy18717.expensestracker;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.unnc.zy18717.expensestracker.MyProviderContract;
import com.unnc.zy18717.expensestracker.R;

import java.util.Calendar;

public class ContentProviderUser extends Activity {

    SimpleCursorAdapter dataAdapter;
    private EditText datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_provider_user);

        datePicker = (EditText) findViewById(R.id.dateField);
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
        queryContentProvider();
    }

    public void queryContentProvider() {

        String[] projection = new String[] {
                MyProviderContract._ID,
                MyProviderContract.DATE,
                MyProviderContract.CATEGORY,
                MyProviderContract.AMOUNT
        };

        String[] colsToDisplay = new String[] {
                MyProviderContract.DATE,
                MyProviderContract.CATEGORY,
                MyProviderContract.AMOUNT
        };

        int[] colResIds = new int[] {
                R.id.value1,
                R.id.value2,
                R.id.value3
        };

        Cursor cursor = getContentResolver().query(MyProviderContract.EXPENSES_URI, projection, null, null, null);

        dataAdapter = new SimpleCursorAdapter(
                this,
                R.layout.db_item_layout,
                cursor,
                colsToDisplay,
                colResIds,
                0);

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(dataAdapter);
    }

    public void add(View v) {

        final EditText dateField = (EditText) findViewById(R.id.dateField);
        String date = dateField.getText().toString();

        final EditText categoryField = (EditText) findViewById(R.id.categoryField);
        String category = categoryField.getText().toString();

        final EditText amountField = (EditText) findViewById(R.id.amountField);
        String amount = amountField.getText().toString();

        if (dateField.getText() == null || categoryField.length() == 0 || amountField.length() == 0) {
            Toast.makeText(ContentProviderUser.this, "Input at least one character", Toast.LENGTH_SHORT).show();
            return;
        }

        ContentValues newValues = new ContentValues();
        newValues.put(MyProviderContract.DATE, date);
        newValues.put(MyProviderContract.CATEGORY, category);
        newValues.put(MyProviderContract.AMOUNT, amount);

        getContentResolver().insert(MyProviderContract.EXPENSES_URI, newValues);

        queryContentProvider();
    }

    protected void showDatePickDlg() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(ContentProviderUser.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                datePicker.setText(year + "/" + monthOfYear + "/" + dayOfMonth);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }
}
