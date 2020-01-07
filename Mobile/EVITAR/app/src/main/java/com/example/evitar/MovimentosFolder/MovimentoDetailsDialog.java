package com.example.evitar.MovimentosFolder;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.evitar.EpiFolder.Epi;
import com.example.evitar.EpiFolder.EpiAlert;
import com.example.evitar.R;
import com.example.evitar.Services.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovimentoDetailsDialog extends AppCompatDialogFragment {


    private ExampleDialogListener listener;
    private Movimento notif;
    private TextView data, tipo, epis, colab;
    private List<EpiAlert> epiList;

    SharedPreferences mUser;

    public MovimentoDetailsDialog(Movimento notif) {
        this.notif = notif;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.movdetailsdialog, null);
        mUser= PreferenceManager.getDefaultSharedPreferences(getActivity());


        data=(TextView) view.findViewById(R.id.textView7);
        tipo=(TextView) view.findViewById(R.id.textView12);
        colab=(TextView) view.findViewById(R.id.textView15);
        epis=(TextView) view.findViewById(R.id.textView19);

        String da=notif.getDataHora();
        String[] a=da.split("\\.");
        String date=a[0].replace("T", "  ");

        data.setText(date);

        if (notif.getCheck()==1){
            tipo.setText("Fine!");
            tipo.setTextColor(Color.GREEN);
        }else{
            tipo.setText("ALERT!");
            tipo.setTextColor(Color.RED);
            getAlerts();
        }
        colab.setText(notif.getPrimeiroNomeCol()+" "+notif.getUltimoNomeCol()+" ("+notif.getIdColaborador()+")");






        builder.setView(view)
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });

        AlertDialog dialog=builder.create();

        dialog.show();

        Button cancel = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        cancel.setBackgroundColor(Color.GRAY);
        cancel.setWidth(1000);

        return dialog;
    }

    private void getAlerts() {
        Call<List<EpiAlert>> call = RetrofitClient
                .getInstance()
                .getApi()
                .getAlerts("Bearer "+mUser.getString("token", ""), notif.getIdMovimento());
        call.enqueue(new Callback<List<EpiAlert>>() {
            @Override
            public void onResponse(Call<List<EpiAlert>> call, Response<List<EpiAlert>> response) {
                if(response.code()==200){
                    epiList=response.body();

                    String s=new StringBuilder().append("Missing ").toString();

                    if (epiList.size()==1){
                        epis.setText("Missing "+epiList.get(0).getNomeTipoEPI());
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
                        epis.setText(s);
                    }



                }else{
                    Toast.makeText(getActivity(), "Talvez ainda não tens Movimentos" + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<EpiAlert>> call, Throwable t) {
                Toast.makeText(getActivity(), "Pedidos movimentos Failed "+ t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (ExampleDialogListener) context;
        } catch (ClassCastException e) {
      /*      throw new ClassCastException(context.toString() +
                    "must implement ExampleDialogListener");*/
        }
    }

    public interface ExampleDialogListener {
    }
}

