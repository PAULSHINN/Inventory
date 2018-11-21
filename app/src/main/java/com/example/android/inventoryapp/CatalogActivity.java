import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.android.inventoryapp.data.BookContract;
import com.example.android.inventoryapp.data.EditorActivity;

import java.util.List;

public class CatalogActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {
    /**
     * Constant value for the URL LOADER.
     **/
    private static final int URL_LOADER = 0;
    CursorAdapter mCursorAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);
        private void insertBook () {
            Log.i("MainActivity fart", "insertBook: clicked insertBook");
            // Create a ContentValues object where column names are the keys,
            // and Toto's pet attributes are the values.
            ContentValues values = new ContentValues();
            values.put(BookContract.BookEntry.COLUMN_PRODUCT_NAME, "I am the cheese.");
            values.put(BookContract.BookEntry.COLUMN_PRICE, 11.99);
            values.put(BookContract.BookEntry.COLUMN_QUANTITY, 45);
            values.put(BookContract.BookEntry.COLUMN_SUPPLIER_NAME, "Barnes and Noble");
            values.put(BookContract.BookEntry.COLUMN_SUPPLIER_PHONE, "(555) 555-5555");
            // Find the ListView which will be populated with the pet data
            ListView list = findViewById(R.id.list);
            // Find and set empty view on the ListView, so that it only shows when the list has 0 items.
            View emptyView = findViewById(R.id.empty_view);
            list.setEmptyView(emptyView);
            // Setup an Adapter to create a list item for each row of pet data in the Cursor.
            // There is no pet data yet (until the loader finishes) so pass in null for the Cursor.
            mCursorAdapter = new CursorAdapter(this, null);
            list.setAdapter(mCursorAdapter);
            // Setup the item click listener
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    // Create new intent to go to {@link EditorActivity}
                    Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                    // Form the content URI that represents the specific pet that was clicked on,
                    // by appending the "id" (passed as input to this method) onto the
                    // {@link PetEntry#CONTENT_URI}.
                    // For example, the URI would be "content://com.example.android.pets/pets/2"
                    // if the pet with ID 2 was clicked on.
                    Uri currentBookUri = ContentUris.withAppendedId(BookEntry.CONTENT_URI, id);
                    // Set the URI on the data field of the intent
                    intent.setData(currentBookUri);
                    // Launch the {@link EditorActivity} to display the data for the current pet.
                    startActivity(intent);
                }
            });
            // Kick off the loader
            getLoaderManager().initLoader(URL_LOADER, null, this);
        }
        Uri newUri = getContentResolver().insert(BookContract.BookEntry.CONTENT_URI, values);
        Log.i("MainActivity fart", "insertBook: newUri is: " + newUri);
    }
    /**
     * Helper method to delete all books in the database.
     */
    private void deleteAllBooks() {
        int rowsDeleted = getContentResolver().delete(BookContract.BookEntry.CONTENT_URI, null, null);
        Log.v("CatalogActivity", rowsDeleted + " rows deleted from book database");
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
    public Loader<List<Books>> onCreateLoader(int i, Bundle bundle) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        StringBuilder stringBuilder = new StringBuilder();
        return new BooksLoader(this, stringBuilder.toString());
    }
    @Override
    public void onLoadFinished(Loader<List<Books>> loader, List<Books> booksList) {
        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);
        // Set empty state text to display "No Articles Found"
        mEmptyStateTextView.setText(R.string.no_books);
        // Clear the adapter of previous article data
        mAdapter.clear();
        // If there is a valid list of {@link Books} articles, then add them to the adapter's data set
        // This will trigger the ListView to update
        if (books != null && !books.isEmpty()) {
            mAdapter.addAll(books);
        }
    }
    @Override
    public void onLoaderReset(Loader<List<BookContract.BookEntry>> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }
}