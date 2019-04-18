package com.unnc.zy18717.expensestracker;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;
import com.unnc.zy18717.expensestracker.MyProviderContract;

public class MyProvider extends ContentProvider {

    private DBHelper dbHelper = null;
    private static final UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(MyProviderContract.AUTHORITY, "expenses", 1);
        uriMatcher.addURI(MyProviderContract.AUTHORITY, "expenses/#", 2);
        uriMatcher.addURI(MyProviderContract.AUTHORITY, "*", 7);
    }

    @Override
    public boolean onCreate() {
        Log.d("ae3cw3", "contentprovider oncreate");
        dbHelper = new DBHelper(this.getContext(), "mydb", null, 6);
        return true;
    }

    @Override
    public String getType(Uri uri) {

        String contentType;

        if (uri.getLastPathSegment() == null)
            contentType = MyProviderContract.CONTENT_TYPE_MULTIPLE;
        else
            contentType = MyProviderContract.CONTENT_TYPE_SINGLE;
        return contentType;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String tableName;

        switch(uriMatcher.match(uri)) {
            case 1:
                tableName = "expenses";
                break;
            default:
                tableName = "expenses";
                break;
        }

        long id = db.insert(tableName, null, values);
        db.close();
        Uri nu = ContentUris.withAppendedId(uri, id);

        Log.d("ae3cw3", nu.toString());

        getContext().getContentResolver().notifyChange(nu, null);

        return nu;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        Log.d("ae3cw3", uri.toString() + " " + uriMatcher.match(uri));

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        switch(uriMatcher.match(uri)) {
            case 2:
                selection = "_ID = " + uri.getLastPathSegment();
            case 1:
                return db.query("expenses", projection, selection, selectionArgs, null, null, sortOrder);
            default:
                return null;
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException("not implemented");
    }
}