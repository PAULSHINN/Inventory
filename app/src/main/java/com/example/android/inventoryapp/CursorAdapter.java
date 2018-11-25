package com.example.android.inventoryapp;

import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
    public void bindView(View view, Context context, Cursor cursor) {
        // Find individual views that we want to modify in the list item layout
        TextView productNameTextView = view.findViewById(R.id.productName);
        TextView priceTextView = view.findViewById(R.id.price);
        TextView quantityTextView = view.findViewById(R.id.quantity);
        TextView supplierNameTextView = view.findViewById(R.id.supplierName);
        TextView supplierPhoneTextView = view.findViewById(R.id.supplierPhone);

        // Find the columns of books attributes that we're interested in
        int productNameColumnIndex = cursor.getColumnIndex(BookContract.BookEntry.COLUMN_PRODUCT_NAME);
        int priceColumnIndex = cursor.getColumnIndex(BookContract.BookEntry.COLUMN_PRICE);
        int quantityColumnIndex = cursor.getColumnIndex(BookContract.BookEntry.COLUMN_QUANTITY);
        int supplierNameColumnIndex = cursor.getColumnIndex(BookContract.BookEntry.COLUMN_SUPPLIER_NAME);
        int supplierPhoneColumnIndex = cursor.getColumnIndex(BookContract.BookEntry.COLUMN_SUPPLIER_PHONE);
        // Read the book attributes from the Cursor for the current book
        String productName = cursor.getString(productNameColumnIndex);
        String price = cursor.getString(priceColumnIndex);
        String quantity = cursor.getString(quantityColumnIndex);
        String supplierName = cursor.getString(supplierNameColumnIndex);
        String supplierPhone = cursor.getString(supplierPhoneColumnIndex);
        // If the book breed is empty string or null, then use some default text
        // that says "Unknown book", so the TextView isn't blank.
        if (TextUtils.isEmpty(productName)) {
            productName = context.getString(R.string.unknown_book);
        }

        // Update the TextViews with the attributes for the current book
        productNameTextView.setText(productName);
        priceTextView.setText(price);
        quantityTextView.setText(quantity);
        supplierNameTextView.setText(supplierName);
        supplierPhoneTextView.setText(supplierPhone);


    }
}

