package tw.com.mydomain.b10632032_hw2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import tw.com.mydomain.b10632032_hw2.data.WaitlistContract;
import tw.com.mydomain.b10632032_hw2.data.WaitlistDBHelper;

public class MainActivity extends AppCompatActivity{

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mi_add:
                Intent intentToStartInputActivity = new Intent(MainActivity.this, InputActivity.class);
                startActivityForResult(intentToStartInputActivity, 1);
                return true;
            case R.id.mi_setting:
                Intent intentToStartSettingActivity = new Intent(MainActivity.this, SettingsActivity.class);
                startActivityForResult(intentToStartSettingActivity, 2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (data.getBooleanExtra("add", false)) {
                    if (addNewTuple(data.getStringExtra("guestName"),
                            data.getIntExtra("guestAmount", 1))) {
                        mAdapter.swapCursor(getAllTuples());
                    }
                }
                break;
            case 2:
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + requestCode);
        }
    }

    RecyclerView recyclerView;
    ListAdapter mAdapter;
    RecyclerView.LayoutManager layoutManager;

    SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rv_list);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        WaitlistDBHelper dbHelper = new WaitlistDBHelper(this);
        mDb = dbHelper.getWritableDatabase();

        mAdapter = new ListAdapter(this, getAllTuples());
        recyclerView.setAdapter(mAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int direction) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage(R.string.deleteMessage);
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mAdapter.swapCursor(getAllTuples());
                        dialog.cancel();
                    }
                });
                builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        long itemId = (long) viewHolder.itemView.getTag();
                        if (removeTuple(itemId)) {
                            mAdapter.swapCursor(getAllTuples());
                        }
                        dialog.cancel();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        }).attachToRecyclerView(recyclerView);
    }

    private Cursor getAllTuples() {
        return mDb.query(
                WaitlistContract.WaitlistEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                WaitlistContract.WaitlistEntry.COLUMN_TIMESTAMP
        );
    }

    private boolean addNewTuple(String guestName, int guestAmount) {
        ContentValues cv = new ContentValues();
        cv.put(WaitlistContract.WaitlistEntry.COLUMN_GUEST_NAME, guestName);
        cv.put(WaitlistContract.WaitlistEntry.COLUMN_GUEST_AMOUNT, guestAmount);
        return mDb.insert(WaitlistContract.WaitlistEntry.TABLE_NAME, null, cv) != -1;
    }

    private boolean removeTuple(Long id) {
        return mDb.delete(WaitlistContract.WaitlistEntry.TABLE_NAME, WaitlistContract.WaitlistEntry._ID + "=" + id, null) > 0;
    }


}
