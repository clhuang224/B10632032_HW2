package tw.com.mydomain.b10632032_hw2;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import tw.com.mydomain.b10632032_hw2.data.WaitlistContract;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {

    private Context mContext;
    private Cursor mCursor;

    ListAdapter(Context context, Cursor cursor) {
        this.mContext = context;
        this.mCursor = cursor;
    }

    static class ListViewHolder extends RecyclerView.ViewHolder {

        TextView guestAmountTextView, guestNameTextView;

        ListViewHolder(View itemView) {
            super(itemView);
            guestAmountTextView = itemView.findViewById(R.id.tv_guestAmount);
            guestNameTextView = itemView.findViewById(R.id.tv_guestName);
        }
    }

    @NonNull
    @Override
    public ListAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                         int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.list_item, parent, false);
        return new ListViewHolder(view);
    }

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
