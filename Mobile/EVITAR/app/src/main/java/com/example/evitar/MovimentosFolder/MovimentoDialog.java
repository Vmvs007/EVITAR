package com.example.evitar.MovimentosFolder;

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

import com.example.evitar.R;

public class MovimentoDialog extends AppCompatDialogFragment {

    private ExampleDialogListener listener;
    private TextView data, tipo, texto, nomeC;
    private Movimento notif;

    public MovimentoDialog(Movimento notif) {
        this.notif = notif;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.movdialog, null);

        data=(TextView) view.findViewById(R.id.textView7);
        tipo=(TextView) view.findViewById(R.id.textView10);
        nomeC=(TextView) view.findViewById(R.id.textView12);
        texto=(TextView) view.findViewById(R.id.textView13);

        String da=notif.getDataHora();
        String[] a=da.split("\\.");
        String date=a[0].replace("T", "  ");

        data.setText(date);
        if (notif.getTypeMov().charAt(0)=='E'){
            tipo.setText("Entry");
        }else{
            tipo.setText("Exit");
        }
        if (notif.getCheck()==1){
            texto.setText("Everything's fine!");
        }else{
            texto.setText("Something happened!");
        }
        nomeC.setText(notif.getPrimeiroNomeCol()+" "+notif.getUltimoNomeCol());



        builder.setView(view)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setPositiveButton("See Details", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MovimentoDetailsDialog notifdetailsDialog = new MovimentoDetailsDialog(notif);
                        notifdetailsDialog.show(getFragmentManager(), "notif details dialog");
                    }
                });


        AlertDialog dialog=builder.create();

        dialog.show();

        Button cancel = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        cancel.setBackgroundColor(Color.GRAY);
        cancel.setWidth(420);

        Button sd = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        sd.setBackgroundColor(Color.RED);
        sd.setWidth(420);


        return dialog;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (ExampleDialogListener) context;
        } catch (ClassCastException e) {
/*            throw new ClassCastException(context.toString() +
                    "must implement ExampleDialogListener");*/
        }
    }

    public interface ExampleDialogListener {
    }
}
