/*
package com.unnc.zy18717.expensestracker;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.content.ContentResolver;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import com.unnc.zy18717.expensestracker.R;

public class ContactsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_activity);

        String columns[] = new String[]
                {
                        ContactsContract.Contacts._ID,
                        ContactsContract.Contacts.DISPLAY_NAME,
                        ContactsContract.Contacts.STARRED
                };

        String colsToDisplay [] = new String[]
                {
                        ContactsContract.Contacts.DISPLAY_NAME
                };

        int[] colResIds = new int[]
                {
                        R.id.textView2
                };

        ContentResolver cr = getContentResolver();

        Cursor c = cr.query(ContactsContract.Contacts.CONTENT_URI,
                columns,
                ContactsContract.Contacts.STARRED + "=0",
                null,
                null);

        ListView lv = (ListView) findViewById(R.id.listView3);
        lv.setAdapter(new SimpleCursorAdapter(this,
                R.layout.contacts,
                c, colsToDisplay,
                colResIds, 0));
    }

    public void onClickButton(View v) {
        Intent intent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts/people"));
        startActivityForResult(intent, PICK_CONTACT);
    }

    public static final int PICK_CONTACT = 0;

    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        switch (reqCode) {
            case (PICK_CONTACT) :
                if (resultCode == Activity.RESULT_OK) {
                    Uri contactData = data.getData();

                    Log.d("ae3mdp", "success");

                    Cursor c =  getContentResolver().query(contactData, null, null, null, null);

                    if (c.moveToFirst()) {
                        String name = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));

                        TextView et = (TextView) findViewById(R.id.textView);
                        et.setText(name);
                        Log.d("ae3mdp", name);
                    }
                }
                break;
        }
    }
}*/
