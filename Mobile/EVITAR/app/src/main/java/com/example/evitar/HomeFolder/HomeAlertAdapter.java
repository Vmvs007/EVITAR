package com.example.evitar.HomeFolder;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.evitar.EpiFolder.EpiAlert;
import com.example.evitar.MovimentosFolder.Movimento;
import com.example.evitar.R;
import com.example.evitar.Services.RetrofitClient;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeAlertAdapter extends RecyclerView.Adapter<HomeAlertAdapter.NotificationViewHolder> {


    public interface OnItemClickListener {
        void onItemClick(Movimento notif);
    }

    private Context mContext;
    private List<Movimento> mNotif;
    private OnItemClickListener listener;
    private List<EpiAlert> epiList;
    private Context context;
    SharedPreferences mUser;


    public HomeAlertAdapter(Context context, List<Movimento> notif, OnItemClickListener listener) {
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
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        mUser= PreferenceManager.getDefaultSharedPreferences(context);

        View notifView = inflater.inflate(R.layout.home_alert_layout, parent, false);
        return new NotificationViewHolder(notifView);
    }

    @Override
    public void onBindViewHolder(NotificationViewHolder viewHolder, int position) {
        Movimento movimento = mNotif.get(position);


        TextView idcolab = viewHolder.idcolab;
        TextView nomecolab = viewHolder.nomecolab;
        TextView data = viewHolder.data;
        TextView tipo = viewHolder.tipo;

        idcolab.setText(String.valueOf(movimento.getIdColaborador()));
        nomecolab.setText(movimento.getPrimeiroNomeCol()+" "+movimento.getUltimoNomeCol());
        String da=movimento.getDataHora();
        String[] a=da.split("\\.");
        String date=a[0].replace("T", "  ");
        data.setText(date);



        viewHolder.bind(mNotif.get(position), listener);


    }


    public class NotificationViewHolder extends RecyclerView.ViewHolder {
        public TextView idcolab;
        public TextView nomecolab;
        public TextView data;
        public TextView tipo;
        public TextView dif;


        public NotificationViewHolder(View itemView) {
            super(itemView);
            idcolab = itemView.findViewById(R.id.idcolab);
            nomecolab = itemView.findViewById(R.id.nomecolab);
            data = itemView.findViewById(R.id.data);
            tipo = itemView.findViewById(R.id.texto);
            dif= itemView.findViewById(R.id.textView23);
        }

        public void bind(final Movimento notif, final OnItemClickListener listener) {
            idcolab.setText(String.valueOf(notif.getIdColaborador()));
            nomecolab.setText(notif.getPrimeiroNomeCol()+" "+notif.getUltimoNomeCol());
            String da=notif.getDataHora();
            String[] a=da.split("\\.");
            String date=a[0].replace("T", " ");
            data.setText(date);
            getAlerts(notif.getIdMovimento());
            Date currentTime = Calendar.getInstance().getTime();
            DateFormat dateFormat = new SimpleDateFormat("MM/dd/YYYY");
            DateFormat dateFormat1 = new SimpleDateFormat("HH:mm:ss");
            String hora=date.split(" ")[1];
            String newdate=date.split(" ")[0].split("-")[1]+"/"+date.split(" ")[0].split("-")[2]+"/"+date.split(" ")[0].split("-")[0]+" "+hora;
            String strDate = dateFormat.format(currentTime)+" "+dateFormat1.format(currentTime);
            dif.setText(getDiff(strDate,newdate));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(notif);
                }
            });
        }

        private void getAlerts(int id) {
            Call<List<EpiAlert>> call = RetrofitClient
                    .getInstance()
                    .getApi()
                    .getAlerts("Bearer "+mUser.getString("token", ""), id);
            call.enqueue(new Callback<List<EpiAlert>>() {
                @Override
                public void onResponse(Call<List<EpiAlert>> call, Response<List<EpiAlert>> response) {
                    if(response.code()==200){
                        epiList=response.body();

                        String s=new StringBuilder().append("Missing ").toString();

                        if (epiList.size()==1){
                            tipo.setText("Missing "+epiList.get(0).getNomeTipoEPI());
                        }else{
                            int count=0;
                            for(EpiAlert epi:epiList) {
                                if(count==epiList.size()-1){
                                    s = new StringBuilder().append(s).append(epi.getNomeTipoEPI()+"!").toString();
                                }else if(count==epiList.size()-2){
                                    s = new StringBuilder().append(s).append(epi.getNomeTipoEPI()+" and ").toString();
                                }else {
                                    s = new StringBuilder().append(s).append(epi.getNomeTipoEPI()+" , ").toString();
                                }
                                count++;
                            }
                            tipo.setText(s);
                        }



                    }else{
                        Toast.makeText(context, "Talvez ainda não tens Movimentos" + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<EpiAlert>> call, Throwable t) {
                    Toast.makeText(context, "Pedidos movimentos Failed "+ t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        public String getDiff(String today, String dat) {
            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

            Date d1 = null;
            Date d2 = null;
            String dif="";

            try {
                d1 = format.parse(dat);
                d2 = format.parse(today);

                //in milliseconds
                long diff = d2.getTime() - d1.getTime();

                long diffSeconds = diff / 1000 % 60;
                long diffMinutes = diff / (60 * 1000) % 60;
                long diffHours = diff / (60 * 60 * 1000) % 24;
                long diffDays = diff / (24 * 60 * 60 * 1000);

                dif=diffHours + "h "+diffMinutes + "m "+diffSeconds + "s";

            } catch (Exception e) {
                e.printStackTrace();
            }
            return dif;
        }
    }


}
