package com.example.evitar.TipoEpiFolder;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.evitar.EpiFolder.Epi;
import com.example.evitar.R;

import java.util.List;

public class TipoEpiAdapter extends RecyclerView.Adapter<TipoEpiAdapter.TipoEpiViewHolder> {


    public interface OnItemClickListener {
        void onItemClick(TipoEpis epi);
    }

    private Context mContext;
    private List<TipoEpis> mEpi;
    private TipoEpiAdapter.OnItemClickListener listener;


    public TipoEpiAdapter(Context context, List<TipoEpis> epi, TipoEpiAdapter.OnItemClickListener listener) {
        mContext = context;
        mEpi = epi;
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return mEpi.size();
    }

    @Override
    public TipoEpiAdapter.TipoEpiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View epiView = inflater.inflate(R.layout.tipoepi_line, parent, false);
        return new TipoEpiAdapter.TipoEpiViewHolder(epiView);
    }

    @Override
    public void onBindViewHolder(TipoEpiAdapter.TipoEpiViewHolder viewHolder, int position) {
        TipoEpis epi = mEpi.get(position);

        TextView idTipoEpi = viewHolder.idTipoEpi;
        TextView nomeTipoEpi = viewHolder.nomeTipoEpi;

        idTipoEpi.setText(String.valueOf(epi.getIdTipoEPI()));
        nomeTipoEpi.setText(epi.getNomeTipoEPI());



        viewHolder.bind(mEpi.get(position), listener);


    }


    public class TipoEpiViewHolder extends RecyclerView.ViewHolder {
        public TextView idTipoEpi;
        public TextView nomeTipoEpi;

        public TipoEpiViewHolder(View itemView) {
            super(itemView);
            idTipoEpi = itemView.findViewById(R.id.idTipoEpi);
            nomeTipoEpi = itemView.findViewById(R.id.nomeTipoEpi);
        }

        public void bind(final TipoEpis epi, final OnItemClickListener listener) {
            idTipoEpi.setText(String.valueOf(epi.getIdTipoEPI()));
            nomeTipoEpi.setText(epi.getNomeTipoEPI());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(epi);
                }
            });
        }
    }
}
