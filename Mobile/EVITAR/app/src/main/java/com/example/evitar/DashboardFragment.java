package com.example.evitar;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.evitar.MovimentosFolder.Movimento;
import com.example.evitar.Services.RetrofitClient;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardFragment  extends Fragment{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Context mContext;

    private List<Movimento> notif;
    private View mContentView;

    private ProgressBar pbar;
    private TableLayout tableLayout;

    SharedPreferences mUser;

    private DashboardFragment.OnFragmentInteractionListener mListener;

    public DashboardFragment() {
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
    public static DashboardFragment newInstance(String param1, String param2) {
        DashboardFragment fragment = new DashboardFragment();
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

        mContentView = inflater.inflate(R.layout.dashboard, container, false);
        mUser= PreferenceManager.getDefaultSharedPreferences(mContext);
        pbar=mContentView.findViewById(R.id.progressBar);
        tableLayout=(TableLayout)mContentView.findViewById(R.id.tableLayout);
        pbar.setVisibility(View.VISIBLE);
        getNotificationsServer();






        return mContentView;
    }

    private void getNotificationsServer() {
        Call<List<Movimento>> call = RetrofitClient
                .getInstance()
                .getApi()
                .getNotifications("Bearer "+mUser.getString("token", ""));
        call.enqueue(new Callback<List<Movimento>>() {
            @Override
            public void onResponse(Call<List<Movimento>> call, Response<List<Movimento>> response) {
                if(response.code()==200){
                    notif=response.body();



                    for(Movimento mov:notif){
                        View tableRow = LayoutInflater.from(mContext).inflate(R.layout.flow_line,null,false);
                        TextView tipo  = (TextView) tableRow.findViewById(R.id.tipo);
                        TextView colab  = (TextView) tableRow.findViewById(R.id.colab);
                        TextView data  = (TextView) tableRow.findViewById(R.id.data);


                        if (mov.getTypeMov().charAt(0)=='E'){
                            tipo.setText("Entry");
                        }else{
                            tipo.setText("Exit");
                        }
                        colab.setText(mov.getPrimeiroNomeCol()+" "+mov.getUltimoNomeCol());

                        String da=mov.getDataHora();
                        String[] a=da.split("\\.");
                        String date=a[0].replace("T", "  ");

                        data.setText(date);

                        tableLayout.addView(tableRow);
                    }




                    pbar.setVisibility(View.GONE);

                }else{
                    Toast.makeText(mContext, "Talvez ainda não tens Movimentos" + response.message(), Toast.LENGTH_SHORT).show();
                    pbar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<Movimento>> call, Throwable t) {
                Toast.makeText(mContext, "Pedidos movimentos Failed "+ t.getMessage(), Toast.LENGTH_SHORT).show();
                pbar.setVisibility(View.GONE);
            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Movimento uri) {
        if (mListener != null) {
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DashboardFragment.OnFragmentInteractionListener) {
            mListener = (DashboardFragment.OnFragmentInteractionListener) context;
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
