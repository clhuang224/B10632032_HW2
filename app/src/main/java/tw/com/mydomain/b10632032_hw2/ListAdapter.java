package tw.com.mydomain.b10632032_hw2;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {

    private Context mContext;
    private String[] test = {"1","2","3"};

    ListAdapter(Context context) {
        this.mContext = context;
    }

    static class ListViewHolder extends RecyclerView.ViewHolder {

        TextView guestAmountTextView, guestNameTextView;

        ListViewHolder(View itemView) {
            super(itemView);
            guestAmountTextView =itemView.findViewById(R.id.tv_guestAmount);
            guestNameTextView =itemView.findViewById(R.id.tv_guestName);
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
    public void onBindViewHolder(ListViewHolder listViewHolder, int position) {
        listViewHolder.guestAmountTextView.setText(test[position]);
        listViewHolder.guestNameTextView.setText(test[position]);
    }

    @Override
    public int getItemCount() {
        return test.length;
    }
}
