package com.example.android.inventoryapp;

import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.inventoryapp.data.BookContract;


public class CursorAdapter extends android.widget.CursorAdapter {


    public CursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Inflate a list item view using the layout specified in list_item.xml
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }


    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        // Find individual views that we want to modify in the list item layout
        TextView productNameTextView = view.findViewById(R.id.productName);
        TextView priceTextView = view.findViewById(R.id.price);
        TextView quantityTextView = view.findViewById(R.id.quantity);
        TextView supplierNameTextView = view.findViewById(R.id.supplierName);
        TextView supplierPhoneTextView = view.findViewById(R.id.supplierPhone);

        //TODO: make a new TextView variableName = view.findViewById() and get your sale button
        // look at like 33 above this for the above task

        // Find the columns of books attributes that we're interested in
        int productNameColumnIndex = cursor.getColumnIndex(BookContract.BookEntry.COLUMN_PRODUCT_NAME);
        int priceColumnIndex = cursor.getColumnIndex(BookContract.BookEntry.COLUMN_PRICE);
        int quantityColumnIndex = cursor.getColumnIndex(BookContract.BookEntry.COLUMN_QUANTITY);
        int supplierNameColumnIndex = cursor.getColumnIndex(BookContract.BookEntry.COLUMN_SUPPLIER_NAME);
        int supplierPhoneColumnIndex = cursor.getColumnIndex(BookContract.BookEntry.COLUMN_SUPPLIER_PHONE);
        // Read the book attributes from the Cursor for the current book
        String productName = cursor.getString(productNameColumnIndex);
        double price = cursor.getDouble(priceColumnIndex);
        int quantity = cursor.getInt(quantityColumnIndex);
        String supplierName = cursor.getString(supplierNameColumnIndex);
        String supplierPhone = cursor.getString(supplierPhoneColumnIndex);


        if (TextUtils.isEmpty(productName)) {
            productName = context.getString(R.string.unknown_book);
        }

        // Update the TextViews with the attributes for the current book
        productNameTextView.setText(productName);
        priceTextView.setText(String.valueOf(price));
        quantityTextView.setText(String.valueOf(quantity));
        supplierNameTextView.setText(supplierName);
        supplierPhoneTextView.setText(supplierPhone);

        //TODO: put a setOnClickListener on your sale button
        /**
         *
         * example 1, b.setOnClickListener. https://www.programcreek.com/java-api-examples/?class=android.widget.Button&method=setOnClickListener
         *
         * the logic inside of this is simple.
         * If the quantity > zero, update the database by subtracting 1 from the quantity
         *      you need the row id. to get the ID you need, look at line 43. instead of an int, make a long the_id = get column index BookEntry then _ID
         *      you will make a new Uri uri = ContentUris.withAppendedId(you_content_uri_goes_here, the_id_goes_here);  // use your insert dummy data method in CatalogActivty as a guide*
         *      make a new ContentValues object (CatalogActivity line 78)
         *      for the .put use the (quantity column name from the contract class, and for the value use quantity - 1)
         *      do a getContentResolver (CatalogActivity line 85) but use update instead of insert
         *
         * update the layout.
         *      do a swapCursor(cursor) to update the view
         *
         * that's it. push the sale button and see what happens. my advice is before you do any of the database code, you make
         * a simple toast that says "clicked" and see if it shows up when you click the button. Then do the other stuff.
         *
         * once this is working we will set up going to the edit activity when the other part of the list item is clicked.
         */
    }
}

