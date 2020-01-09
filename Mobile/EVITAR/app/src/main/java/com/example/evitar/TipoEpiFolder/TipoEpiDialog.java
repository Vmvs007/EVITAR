package com.example.evitar.TipoEpiFolder;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;

import com.example.evitar.EpiFolder.EditEpiFragment;
import com.example.evitar.EpiFolder.Epi;
import com.example.evitar.R;

public class TipoEpiDialog extends AppCompatDialogFragment {

    private TipoEpiDialog.ExampleDialogListener listener;
    private TipoEpis epi;

    public TipoEpiDialog(TipoEpis epi) {
        this.epi = epi;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.tipoepidialog, null);

        TextView idtipoepi=(TextView) view.findViewById(R.id.idTipoEpi);
        TextView nometipoepi=(TextView) view.findViewById(R.id.nomeTipoEpi);

        idtipoepi.setText(String.valueOf(epi.getIdTipoEPI()));
        nometipoepi.setText(epi.getNomeTipoEPI());




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
                        args.putInt("idtipoepi", epi.getIdTipoEPI());
                        args.putString("nometipoepi", epi.getNomeTipoEPI());
                        Fragment fragment=new EditTipoEpiFragment();
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
            listener = (TipoEpiDialog.ExampleDialogListener) context;
        } catch (ClassCastException e) {
/*            throw new ClassCastException(context.toString() +
                    "must implement ExampleDialogListener");*/
        }
    }

    public interface ExampleDialogListener {
    }
}
