package com.example.evitar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {


    public interface OnItemClickListener {
        void onItemClick(Notification notif);
    }

    private Context mContext;
    private List<Notification> mNotif;
    private OnItemClickListener listener;


    public NotificationAdapter(Context context, List<Notification> notif, OnItemClickListener listener) {
        mContext = context;
        mNotif = notif;
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return mNotif.size();
    }

    @Override
    public NotificationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View notifView = inflater.inflate(R.layout.line_notif, parent, false);
        return new NotificationViewHolder(notifView);
    }

    @Override
    public void onBindViewHolder(NotificationViewHolder viewHolder, int position) {
        Notification notification = mNotif.get(position);

        TextView date = viewHolder.dateTextView;
        date.setText(notification.getDate());
        TextView title = viewHolder.titleTextView;
        title.setText(notification.getTitle());
        TextView description = viewHolder.descriptionTextView;
        description.setText(notification.getDescription());


        viewHolder.bind(mNotif.get(position), listener);


    }


    public class NotificationViewHolder extends RecyclerView.ViewHolder {
        public TextView dateTextView;
        public TextView titleTextView;
        public TextView descriptionTextView;

        public NotificationViewHolder(View itemView) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.textView10);
            titleTextView = itemView.findViewById(R.id.textView22);
            descriptionTextView = itemView.findViewById(R.id.textView23);
        }

        public void bind(final Notification notif, final OnItemClickListener listener) {
            dateTextView.setText(notif.getDate());
            titleTextView.setText(notif.getTitle());
            descriptionTextView.setText(notif.getDescription());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(notif);
                }
            });
        }
    }
}
