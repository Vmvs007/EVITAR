package com.example.evitar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class NotificationsFragment extends Fragment implements NotificationDialog.ExampleDialogListener{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        openDialog();
        return inflater.inflate(R.layout.notifications, null);
    }

    public void openDialog() {
        NotificationDialog notifDialog = new NotificationDialog();
        notifDialog.show(getFragmentManager(), "notif dialog");
    }

    @Override
    public void seeDetails() {
    }

}
