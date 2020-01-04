package com.example.evitar.MovimentosFolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.evitar.R;

import java.util.List;

public class FlowAdapter extends RecyclerView.Adapter<FlowAdapter.NotificationViewHolder> {


    public interface OnItemClickListener {
        void onItemClick(Movimento notif);
    }

    private Context mContext;
    private List<Movimento> mNotif;
    private OnItemClickListener listener;


    public FlowAdapter(Context context, List<Movimento> notif, OnItemClickListener listener) {
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

        View notifView = inflater.inflate(R.layout.flow_line, parent, false);
        return new NotificationViewHolder(notifView);
    }

    @Override
    public void onBindViewHolder(NotificationViewHolder viewHolder, int position) {
        Movimento movimento = mNotif.get(position);

        TextView tipomov = viewHolder.tipoMov;
        if (movimento.getTypeMov().charAt(0)=='E'){
            tipomov.setText("Entry");
        }else{
            tipomov.setText("Exit");
        }

        TextView nomeColab=viewHolder.nomecolab;
        nomeColab.setText(movimento.getPrimeiroNomeCol()+""+movimento.getUltimoNomeCol());

        TextView data=viewHolder.data;
        String da=movimento.getDataHora();
        String[] a=da.split("\\.");
        String date=a[0].replace("T", "  ");

        data.setText(date);


        viewHolder.bind(mNotif.get(position), listener);


    }


    public class NotificationViewHolder extends RecyclerView.ViewHolder {
        public TextView tipoMov;
        public TextView nomecolab;
        public TextView data;


        public NotificationViewHolder(View itemView) {
            super(itemView);
            tipoMov = itemView.findViewById(R.id.tipo);
            nomecolab = itemView.findViewById(R.id.colab);
            data=itemView.findViewById(R.id.data);
        }

        public void bind(final Movimento notif, final OnItemClickListener listener) {
            if (notif.getTypeMov().charAt(0)=='E'){
                tipoMov.setText("Entry");
            }else{
                tipoMov.setText("Exit");
            }
            nomecolab.setText(notif.getPrimeiroNomeCol()+" "+notif.getUltimoNomeCol());
            String da=notif.getDataHora();
            String[] a=da.split("\\.");
            String date=a[0].replace("T", "  ");

            data.setText(date);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(notif);
                }
            });
        }
    }
}
