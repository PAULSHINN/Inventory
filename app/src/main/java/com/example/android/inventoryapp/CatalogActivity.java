package com.example.android.inventoryapp;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.android.inventoryapp.data.BookContract;

// TODO 06: make CatalogActivity implement LoaderManager.LoaderCallbacks<Cursor>
// this task will make cataogactivity red until you implement some methods
public class CatalogActivity extends AppCompatActivity {

    // TODO 07: create a CONSTANT for url_loader and set it to 0

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        // TODO 01: make a variable for your list/recycle view, emptyview (if you use a listview) and assign it to the view
        /**
         * your activity_catalog.xml looks like its already set up from copying the pets layout. go find the id you need
         */


        // TODO 02  Make a new layout xml file list_item.xml
        /**
         * you can use the list_item from the pets app as a guide here. You need to have:
         * the product name, the quantity, and a sale button in the layout. You can add any other info you want, but those are the things required.
         * when you make the button view, add android:focusable="false" to the xml or you wont be able to click it later
         */



        // TODO 03: you need to make an adapter for your list/recyclerview.
        /**
         * this adapter needs to extend CursorAdapter because you will be passing in a cursor.
         * use the pets adapter as a guide if you use a standard listview
         */



        // TODO 04: now that you have an adapter, made a new variable for it but set it to (this, null) so its empty


        // TODO 05: set the adapter on the list/recycleview



        // TODO 12: you need to set an onclick listener to the entire list item. this is different than the one you will make for the button
        /**
         * if you use the listview and not a recyclerview, you can use the AdapterView.onItemClickListener when you do .setOnItemClickListener. its in the pets app.
         * this will launch an intent to the editor screen. you will send the id and the content uri in the intent
         */




        // TODO 08: use getLoaderManager.initLoader using the (url_loader variable, null, this) as arguments

    }

    // TODO 09: add override method for onCreateLoader
    /**
     * this will have a switch statement using the id
     * if case is the url loader make a new CursorAdaptor and return that adapter
     * if its not the url loader return null
     */


    // TODO 10: add override method for onLoaderFinished
    /**
     * this method will simple use .swapCursor on the adapter you made passing in the data variable
     */



    // TODO 11: add override method for onLoaderReset
    /**
     * this is the same as onLoaderFinished but you pass in null to swapCursor
     */




    private void insertBook() {
        Log.i("MainActivity fart", "insertBook: clicked insertBook");
        // Create a ContentValues object where column names are the keys,
        // and Toto's pet attributes are the values.
        ContentValues values = new ContentValues();
        values.put(BookContract.BookEntry.COLUMN_PRODUCT_NAME, "I am the cheese.");
        values.put(BookContract.BookEntry.COLUMN_PRICE, 11.99);
        values.put(BookContract.BookEntry.COLUMN_QUANTITY, 45);
        values.put(BookContract.BookEntry.COLUMN_SUPPLIER_NAME, "Barnes and Noble");
        values.put(BookContract.BookEntry.COLUMN_SUPPLIER_PHONE, "(555) 555-5555");

        Uri newUri = getContentResolver().insert(BookContract.BookEntry.CONTENT_URI, values);
        Log.i("MainActivity fart", "insertBook: newUri is: " + newUri);
    }

    /**
     * Helper method to delete all pets in the database.
     */
    private void deleteAllBooks() {
        int rowsDeleted = getContentResolver().delete(BookContract.BookEntry.CONTENT_URI, null, null);
        Log.v("CatalogActivity", rowsDeleted + " rows deleted from pet database");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_insert_dummy_data:
                Log.i("Options fart", "onOptionsItemSelected: dummy data clicked");
                insertBook();

            case R.id.action_delete_all_entries:
                deleteAllBooks();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }
}

// TODO 13: make a new activity called EditorActivity
/**
 * once you are this far we will revisit your current progress
 */
