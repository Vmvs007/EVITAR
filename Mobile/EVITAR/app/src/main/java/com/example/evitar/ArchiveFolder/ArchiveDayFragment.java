package com.example.evitar.ArchiveFolder;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import com.example.evitar.NotificationFolder.Notification;
import com.example.evitar.R;
import com.example.evitar.Services.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArchiveDayFragment   extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Context mContext;

    private List<Notification> notif;
    private View mContentView;

    private ProgressBar pbar;
    private TableLayout tableLayout;

    SharedPreferences mUser;

    private ArchiveDayFragment.OnFragmentInteractionListener mListener;

    public ArchiveDayFragment() {
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
    public static ArchiveDayFragment newInstance(String param1, String param2) {
        ArchiveDayFragment fragment = new ArchiveDayFragment();
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
            mParam1 = getArguments().getString("data");
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        mContext=getActivity();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mContentView = inflater.inflate(R.layout.archive_day, container, false);
        mUser= PreferenceManager.getDefaultSharedPreferences(mContext);
        pbar=mContentView.findViewById(R.id.progressBar);
        tableLayout=(TableLayout)mContentView.findViewById(R.id.tableLayout);
        TextView data=(TextView) mContentView.findViewById(R.id.textView11);
        data.setText(mParam1);
        pbar.setVisibility(View.VISIBLE);
        getNotificationsServer();






        return mContentView;
    }

    private void getNotificationsServer() {
        Call<List<Notification>> call = RetrofitClient
                .getInstance()
                .getApi()
                .getMovimentosporDia("Bearer "+mUser.getString("token", ""), mParam1);
        call.enqueue(new Callback<List<Notification>>() {
            @Override
            public void onResponse(Call<List<Notification>> call, Response<List<Notification>> response) {
                if(response.code()==200){
                    notif=response.body();



                    for(Notification mov:notif){
                        View tableRow = LayoutInflater.from(mContext).inflate(R.layout.flow_line,null,false);
                        TextView tipo  = (TextView) tableRow.findViewById(R.id.tipo);
                        TextView colab  = (TextView) tableRow.findViewById(R.id.colab);
                        TextView data  = (TextView) tableRow.findViewById(R.id.data);

                        tipo.setText(mov.getTypeMov());
                        colab.setText(String.valueOf(mov.getIdColaborador()));
                        data.setText(mov.getDataHora());

                        tableLayout.addView(tableRow);
                    }


                    pbar.setVisibility(View.GONE);

                }else{
                    Toast.makeText(mContext, "Talvez ainda não tens Movimentos" + response.message(), Toast.LENGTH_SHORT).show();
                    pbar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<Notification>> call, Throwable t) {
                Toast.makeText(mContext, "Pedidos movimentos Failed "+ t.getMessage(), Toast.LENGTH_SHORT).show();
                pbar.setVisibility(View.GONE);
            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Notification uri) {
        if (mListener != null) {
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ArchiveDayFragment.OnFragmentInteractionListener) {
            mListener = (ArchiveDayFragment.OnFragmentInteractionListener) context;
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
    }



}

