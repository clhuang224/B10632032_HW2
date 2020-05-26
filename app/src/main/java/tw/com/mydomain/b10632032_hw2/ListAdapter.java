package tw.com.mydomain.b10632032_hw2;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.res.ResourcesCompat;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import tw.com.mydomain.b10632032_hw2.data.WaitlistContract;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {

    private Context mContext;
    private Cursor mCursor;

    ListAdapter(Context context, Cursor cursor) {
        this.mContext = context;
        this.mCursor = cursor;
    }

    class ListViewHolder extends RecyclerView.ViewHolder implements SharedPreferences.OnSharedPreferenceChangeListener {

        TextView guestAmountTextView, guestNameTextView;

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        ListViewHolder(View itemView) {
            super(itemView);
            guestAmountTextView = itemView.findViewById(R.id.tv_guestAmount);
            guestNameTextView = itemView.findViewById(R.id.tv_guestName);
            setupSharedPreferences();
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        private void setupSharedPreferences() {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
            loadColorFromPreferences(sharedPreferences);
            sharedPreferences.registerOnSharedPreferenceChangeListener(this);
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        private void loadColorFromPreferences(SharedPreferences sharedPreferences) {
            String color = sharedPreferences.getString(mContext.getString(R.string.color_key), mContext.getString(R.string.red_value));
            switch (color) {
                case "red":
                    guestAmountTextView.setBackground(ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.red_circle, null));
                    break;
                case "blue":
                    guestAmountTextView.setBackground(ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.blue_circle, null));
                    break;
                case "green":
                    guestAmountTextView.setBackground(ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.green_circle, null));
                    break;
            }
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if (key.equals(mContext.getString(R.string.color_key))) {
                loadColorFromPreferences(sharedPreferences);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @NonNull
    @Override
    public ListAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                         int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.list_item, parent, false);
        return new ListViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(@NonNull ListViewHolder listViewHolder, int position) {
        if (mCursor.moveToPosition(position)) {
            listViewHolder.itemView.setTag(mCursor.getLong(mCursor.getColumnIndex(WaitlistContract.WaitlistEntry._ID)));
            listViewHolder.guestAmountTextView.setText(String.valueOf(mCursor.getInt(mCursor.getColumnIndex(WaitlistContract.WaitlistEntry.COLUMN_GUEST_AMOUNT))));
            listViewHolder.guestNameTextView.setText(mCursor.getString(mCursor.getColumnIndex(WaitlistContract.WaitlistEntry.COLUMN_GUEST_NAME)));
        }
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    void swapCursor(Cursor newCursor) {
        if (mCursor != null) mCursor.close();
        mCursor = newCursor;
        if (newCursor != null) {
            this.notifyDataSetChanged();
        }
    }
}
