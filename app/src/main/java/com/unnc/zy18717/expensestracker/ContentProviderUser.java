package com.unnc.zy18717.expensestracker;

import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import com.unnc.zy18717.expensestracker.MyProviderContract;
import com.unnc.zy18717.expensestracker.R;

public class ContentProviderUser extends Activity {

    SimpleCursorAdapter dataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_provider_user);
        queryContentProvider();
//        queryContentProvider2();
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

/*    public void queryContentProvider2() {

        String[] projection = new String[] {
                MyProviderContract._ID,
                MyProviderContract.NAME,
                MyProviderContract.EMAIL
        };

        String colsToDisplay [] = new String[] {
                MyProviderContract._ID,
                MyProviderContract.NAME,
                MyProviderContract.EMAIL
        };

        int[] colResIds = new int[] {
                R.id.value1,
                R.id.value2,
                R.id.value3
        };

        Cursor cursor = getContentResolver().query(MyProviderContract.PEOPLE_URI, projection, null, null, null);

        dataAdapter = new SimpleCursorAdapter(
                this,
                R.layout.db_item_layout,
                cursor,
                colsToDisplay,
                colResIds,
                0);

        ListView listView = (ListView) findViewById(R.id.listView2);
        listView.setAdapter(dataAdapter);
    }*/

    public void add(View v) {

        final EditText nameField = (EditText) findViewById(R.id.editText3);
        String date = nameField.getText().toString();

        final EditText foodField = (EditText) findViewById(R.id.editText4);
        String category = foodField.getText().toString();

        final EditText emailField = (EditText) findViewById(R.id.editText5);
        String amount = emailField.getText().toString();

        ContentValues newValues = new ContentValues();
        newValues.put(MyProviderContract.DATE, date);
        newValues.put(MyProviderContract.CATEGORY, category);
        newValues.put(MyProviderContract.AMOUNT, amount);

        getContentResolver().insert(MyProviderContract.EXPENSES_URI, newValues);

        queryContentProvider();
//        queryContentProvider2();
    }
}
