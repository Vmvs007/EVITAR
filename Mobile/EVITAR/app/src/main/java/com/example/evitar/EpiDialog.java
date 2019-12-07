package com.example.evitar;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class EpiDialog extends AppCompatDialogFragment {

    private EpiDialog.ExampleDialogListener listener;
    Epi epi;

    public EpiDialog(Epi epi) {
        this.epi = epi;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.epidialog, null);


        builder.setView(view)
                .setNegativeButton("Editar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setPositiveButton("Remover", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
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
