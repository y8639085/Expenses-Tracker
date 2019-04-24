package com.unnc.zy18717.expensestracker;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.content.ContentValues;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import java.util.Calendar;

public class RecordsActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener{

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
                if (b)
                    showDatePickDlg();
            }
        });
        queryContentProvider(null);
        initRadio();
    }

    protected void showDatePickDlg() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(RecordsActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                monthOfYear += 1;
                if (monthOfYear < 10 && dayOfMonth < 10)
                    datePicker.setText(year + "/0" + monthOfYear + "/0" + dayOfMonth);
                else if (monthOfYear < 10 && dayOfMonth >= 10)
                    datePicker.setText(year + "/0" + monthOfYear + "/" + dayOfMonth);
                else if (monthOfYear >= 10 && dayOfMonth < 10) {
                    datePicker.setText(year + "/" + monthOfYear + "/0" + dayOfMonth);
                }
                else if (monthOfYear >= 10 && dayOfMonth >= 10)
                    datePicker.setText(year + "/" + monthOfYear + "/" + dayOfMonth);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    public void add(View v) {

        final EditText dateField = (EditText) findViewById(R.id.dateField);
        String date = dateField.getText().toString();

        final EditText categoryField = (EditText) findViewById(R.id.categoryField);
        String category = categoryField.getText().toString();

        final EditText amountField = (EditText) findViewById(R.id.amountField);
        String amount = amountField.getText().toString();

        if (dateField.getText() == null || categoryField.length() == 0 || amountField.length() == 0) {
            Toast.makeText(RecordsActivity.this, "Input at least one character", Toast.LENGTH_SHORT).show();
            return;
        }

        ContentValues newValues = new ContentValues();
        newValues.put(MyProviderContract.DATE, date);
        newValues.put(MyProviderContract.CATEGORY, category);
        newValues.put(MyProviderContract.AMOUNT, amount);

        getContentResolver().insert(MyProviderContract.EXPENSES_URI, newValues);

        queryContentProvider(null);
    }

    // query database using given order
    public void queryContentProvider(String sortOrder) {

        String[] projection = new String[] {
                MyProviderContract._ID,
                MyProviderContract.DATE,
                MyProviderContract.CATEGORY,
                MyProviderContract.AMOUNT
        };

        int[] colResIds = new int[] {
                R.id.value1,
                R.id.value2,
                R.id.value3,
                R.id.value4
        };

        Cursor cursor = getContentResolver().query(MyProviderContract.EXPENSES_URI, projection, null, null, sortOrder);

        dataAdapter = new SimpleCursorAdapter(
                this,
                R.layout.db_item_layout,
                cursor,
                projection,
                colResIds,
                0);

        // put data into listview
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(dataAdapter);
    }

    // initialize all radiobuttons
    private void initRadio() {
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        RadioButton sortById = (RadioButton) findViewById(R.id.sortById);
        // set "sortById" as default
        radioGroup.check(sortById.getId());
        radioGroup.setOnCheckedChangeListener(this);
    }

    // sort data by click radiobuttons
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (group.getCheckedRadioButtonId()){
            case R.id.sortById:
                queryContentProvider("_id");
                break;
            case R.id.sortByDate:
                queryContentProvider("date");
            break;
            case R.id.sortByCategory:
                queryContentProvider("category");
                break;
            case R.id.sortByAmount:
                queryContentProvider("amount");
                break;
        }
    }
}