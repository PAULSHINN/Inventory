package com.example.android.inventoryapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.android.inventoryapp.data.BookContract;

public class CatalogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);
    }

    private void insertBook() {

        // Create a ContentValues object where column names are the keys,
        // and Toto's pet attributes are the values.
        ContentValues values = new ContentValues();
        values.put(BookContract.BookEntry.COLUMN_PRODUCT_NAME, "I am the cheese.");
        values.put(BookContract.BookEntry.COLUMN_PRICE, 11.99);
        values.put(BookContract.BookEntry.COLUMN_QUANTITY, 45);
        values.put(BookContract.BookEntry.COLUMN_SUPPLIER_NAME, "Barnes and Noble");
        values.put(BookContract.BookEntry.COLUMN_SUPPLIER_PHONE, "(555) 555-5555");

        Uri newUri = getContentResolver().insert(BookContract.BookEntry.CONTENT_URI, values);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            updateUI();
        }
    }

    /**
     * Helper method to delete all pets in the database.
     */
    private void deleteAllBooks() {
        int rowsDeleted = getContentResolver().delete(BookContract.BookEntry.CONTENT_URI, null, null);

        Toast.makeText(this, rowsDeleted + " rows deleted from pet database", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_insert_dummy_data:
                insertBook();

                return true;

            case R.id.action_delete_all_entries:
                deleteAllBooks();

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_catalog, menu);

        return true;
    }

    private void updateUI() {

        Cursor cursor = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            cursor = getContentResolver().query(BookContract.BookEntry.CONTENT_URI, null, null, null);
        }
        if (cursor == null || cursor.getCount() < 1) {

            return;
        }

        String dummytext = "";
        if (cursor.moveToFirst()) {

            int nameColumnIndex = cursor.getColumnIndex(BookContract.BookEntry.COLUMN_PRODUCT_NAME);
            String name = cursor.getString(nameColumnIndex);
            dummytext += name + ", ";
        }

        Toast.makeText(this, "Books in Database: " + dummytext, Toast.LENGTH_SHORT).show();
    }
}

