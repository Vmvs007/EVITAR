package com.example.evitar;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class NotificationsFragment extends Fragment implements NotificationDialog.ExampleDialogListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView mRecyclerView;
    private NotificationAdapter mAdapter;

    private Context mContext;

    private OnFragmentInteractionListener mListener;

    public NotificationsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment1.
     */
    // TODO: Rename and change types and number of parameters
    public static NotificationsFragment newInstance(String param1, String param2) {
        NotificationsFragment fragment = new NotificationsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        mContext=getActivity();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View mContentView = inflater.inflate(R.layout.notifications, container, false);

        Notification por=new Notification("June 30, 2019", "Everyting went well today!", "Qualquer  coisa");
        Notification ame=new Notification("June 29, 2019", "Missing work boots in Zone A2", "Qualquer  coisa");
        Notification ang=new Notification("June 28, 2019", "Welcome to our App!", "Qualquer  coisa");
        Notification por1=new Notification("June 30, 2019", "Everyting went well today!", "Qualquer  coisa");
        Notification ame1=new Notification("June 29, 2019", "Missing work boots in Zone A2", "Qualquer  coisa");
        Notification ang1=new Notification("June 28, 2019", "Welcome to our App!", "Qualquer  coisa");


        List<Notification> notif= Arrays.asList(por, ame, ang, por1, ame1, ang1);



        mAdapter=new NotificationAdapter(mContext, notif,new NotificationAdapter.OnItemClickListener() {
            @Override public void onItemClick(Notification notif) {
                mListener.onButtonclick(notif);
            }});

        mRecyclerView=mContentView.findViewById(R.id.recycler_view);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        RecyclerView.ItemDecoration itemDecoration=new DividerItemDecoration(mContext,DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);

        return mContentView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Notification uri) {
        if (mListener != null) {
            mListener.onButtonclick(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {/*
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");*/
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
}
    public interface OnFragmentInteractionListener {
        void onButtonclick(Notification notif);
    }



}
