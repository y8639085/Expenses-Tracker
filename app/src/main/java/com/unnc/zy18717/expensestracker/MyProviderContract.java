package com.unnc.zy18717.expensestracker;

import android.net.Uri;

public class MyProviderContract {
    public static final String AUTHORITY = "com.unnc.zy18717.expensestracker.MyProvider";

    public static final Uri EXPENSES_URI = Uri.parse("content://"+AUTHORITY+"/expenses");
    public static final Uri ALL_URI = Uri.parse("content://"+AUTHORITY+"/");

    public static final String _ID = "_id";
    public static final String DATE = "date";
    public static final String CATEGORY = "category";
    public static final String AMOUNT = "amount";

    // MIME type string
    public static final String CONTENT_TYPE_SINGLE = "vnd.android.cursor.item/MyProvider.data.text";
    public static final String CONTENT_TYPE_MULTIPLE = "vnd.android.cursor.dir/MyProvider.data.text";
}