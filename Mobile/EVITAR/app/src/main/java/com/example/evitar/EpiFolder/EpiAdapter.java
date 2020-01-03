package com.example.evitar.EpiFolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.evitar.R;

import java.util.List;

public class EpiAdapter extends RecyclerView.Adapter<EpiAdapter.EpiViewHolder> {


    public interface OnItemClickListener {
        void onItemClick(Epi epi);
    }

    private Context mContext;
    private List<Epi> mEpi;
    private EpiAdapter.OnItemClickListener listener;


    public EpiAdapter(Context context, List<Epi> epi, EpiAdapter.OnItemClickListener listener) {
        mContext = context;
        mEpi = epi;
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return mEpi.size();
    }

    @Override
    public EpiAdapter.EpiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View epiView = inflater.inflate(R.layout.epi_line, parent, false);
        return new EpiAdapter.EpiViewHolder(epiView);
    }

    @Override
    public void onBindViewHolder(EpiAdapter.EpiViewHolder viewHolder, int position) {
        Epi epi = mEpi.get(position);

        TextView idEpi = viewHolder.idEpi;
        TextView nomeEpi = viewHolder.nomeEpi;

        idEpi.setText(String.valueOf(epi.getIdEPI()));
        nomeEpi.setText(epi.getNomeEPI());


        viewHolder.bind(mEpi.get(position), listener);


    }


    public class EpiViewHolder extends RecyclerView.ViewHolder {
        public TextView idEpi;
        public TextView nomeEpi;

        public EpiViewHolder(View itemView) {
            super(itemView);
            idEpi = itemView.findViewById(R.id.idEpi);
            nomeEpi = itemView.findViewById(R.id.nomeEpi);
        }

        public void bind(final Epi epi, final OnItemClickListener listener) {
            idEpi.setText(String.valueOf(epi.getIdEPI()));
            nomeEpi.setText(epi.getNomeEPI());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(epi);
                }
            });
        }
    }
}
