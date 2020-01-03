package com.example.evitar.EpiFolder;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;

import com.example.evitar.Colaborador;
import com.example.evitar.R;
import com.example.evitar.Services.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EpiDialog extends AppCompatDialogFragment {

    private EpiDialog.ExampleDialogListener listener;
    private Epi epi;

    public EpiDialog(Epi epi) {
        this.epi = epi;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.epidialog, null);

        TextView idepi=(TextView) view.findViewById(R.id.idEpi);
        TextView nomeepi=(TextView) view.findViewById(R.id.nomeEpi);
        TextView reg=(TextView) view.findViewById(R.id.datareg);
        TextView val=(TextView) view.findViewById(R.id.dataval);
        TextView valido=(TextView) view.findViewById(R.id.valido);
        TextView tipoepi=(TextView) view.findViewById(R.id.tipoEpi);
        TextView colabname=(TextView) view.findViewById(R.id.colabName);

        idepi.setText(String.valueOf(epi.getIdEPI()));
        nomeepi.setText(epi.getNomeEPI());
        reg.setText(epi.getDataRegistoEPI());
        val.setText(epi.getDataValidadeEPI());
        if(epi.getValido()==1){
            valido.setText("YES");
        }else{
            valido.setText("NO");
        }
        tipoepi.setText(epi.getNomeTipoEPI());
        colabname.setText(epi.getNomeInspector());




        builder.setView(view)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setPositiveButton("Editar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Bundle args=new Bundle();
                        args.putInt("idepi", epi.getIdEPI());
                        args.putString("nomeepi", epi.getNomeEPI());
                        args.putString("regepi", epi.getDataRegistoEPI());
                        args.putString("valepi", epi.getDataValidadeEPI());
                        args.putInt("idcolab", epi.getIdColaborador());
                        args.putInt("valido", epi.getValido());
                        args.putInt("idtipoepi", epi.getIdTipoEPI());
                        args.putString("nomecolab", epi.getNomeInspector());
                        args.putString("nometipoepi", epi.getNomeEPI());
                        Fragment fragment=new EditEpiFragment();
                        fragment.setArguments(args);
                        getFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container, fragment)
                                .commit();
                    }
                });


        AlertDialog dialog=builder.create();

        dialog.show();

        Button cancel = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        cancel.setBackgroundColor(Color.GRAY);
        cancel.setWidth(1000);

        Button sd = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        sd.setBackgroundColor(Color.RED);
        sd.setWidth(1000);


        return dialog;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (EpiDialog.ExampleDialogListener) context;
        } catch (ClassCastException e) {
/*            throw new ClassCastException(context.toString() +
                    "must implement ExampleDialogListener");*/
        }
    }

    public interface ExampleDialogListener {
    }
}
