package com.example.android.inventoryapp;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
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
    public void bindView(View view, final Context context, final Cursor cursor) {
        // Find individual views that we want to modify in the list item layout
        TextView productNameTextView = view.findViewById(R.id.productName);
        TextView priceTextView = view.findViewById(R.id.price);
        TextView quantityTextView = view.findViewById(R.id.quantity);
        TextView supplierNameTextView = view.findViewById(R.id.supplierName);
        TextView supplierPhoneTextView = view.findViewById(R.id.supplierPhone);
        TextView saleTextView = view.findViewById(R.id.sale);

        // Find the columns of books attributes that we're interested in
        int productIdColumnIndex = cursor.getColumnIndex(BookContract.BookEntry._ID);
        int productNameColumnIndex = cursor.getColumnIndex(BookContract.BookEntry.COLUMN_PRODUCT_NAME);
        int priceColumnIndex = cursor.getColumnIndex(BookContract.BookEntry.COLUMN_PRICE);
        int quantityColumnIndex = cursor.getColumnIndex(BookContract.BookEntry.COLUMN_QUANTITY);
        int supplierNameColumnIndex = cursor.getColumnIndex(BookContract.BookEntry.COLUMN_SUPPLIER_NAME);
        int supplierPhoneColumnIndex = cursor.getColumnIndex(BookContract.BookEntry.COLUMN_SUPPLIER_PHONE);
        // Read the book attributes from the Cursor for the current book
        final int rowId = cursor.getInt(productIdColumnIndex);
        String productName = cursor.getString(productNameColumnIndex);
        double price = cursor.getDouble(priceColumnIndex);
        final int quantity = cursor.getInt(quantityColumnIndex);
        String supplierName = cursor.getString(supplierNameColumnIndex);
        String supplierPhone = cursor.getString(supplierPhoneColumnIndex);

        if (TextUtils.isEmpty(productName)) {
            productName = context.getString(R.string.unknown_book);
        }

        // Update the TextViews with the attributes for the current book
        productNameTextView.setText(productName);
        priceTextView.setText(String.valueOf(price));
        if (quantity == 0) {
            quantityTextView.setText(view.getResources().getString(R.string.out_of_stock));

        } else {
            quantityTextView.setText(String.valueOf(quantity));
        }

        supplierNameTextView.setText(supplierName);
        supplierPhoneTextView.setText(supplierPhone);

        /**
         * Creates a button which can be clicked to go to the end of the test view.
         *
         * @return the button, not null
         */
        Button saleButton = view.findViewById(R.id.sale);
        saleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                if (quantity > 0) {
                    Toast.makeText(context, "SOLD!!", Toast.LENGTH_SHORT).show();
                    Uri uri = ContentUris.withAppendedId(BookContract.BookEntry.CONTENT_URI, rowId);
                    ContentValues values = new ContentValues();
                    values.put(BookContract.BookEntry.COLUMN_QUANTITY, quantity - 1);
                    context.getContentResolver().update(uri, values, null, null);
                    swapCursor(cursor);
                }
            }
        });
    }
}

