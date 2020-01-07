package com.example.evitar.TipoEpiFolder;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import com.example.evitar.EpiFolder.Epi;
import com.example.evitar.EpiFolder.EpiFragment;
import com.example.evitar.R;
import com.example.evitar.Services.RetrofitClient;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTipoEpiFragment extends Fragment {
    SharedPreferences mUser;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Context mContext;

    private View mContentView;

    private ProgressBar pbar;

    private EditText edNomeTipoEpi;




    private AddTipoEpiFragment.OnFragmentInteractionListener mListener;

    public AddTipoEpiFragment() {
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
    public static AddTipoEpiFragment newInstance(String param1, String param2) {
        AddTipoEpiFragment fragment = new AddTipoEpiFragment();
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

        mContentView = inflater.inflate(R.layout.add_tipoepi_layout, container, false);
        mUser= PreferenceManager.getDefaultSharedPreferences(mContext);

        pbar=mContentView.findViewById(R.id.progressBar);
        Button addButton= (Button) mContentView.findViewById(R.id.adicionarbutton);
        Button cancelButton= (Button) mContentView.findViewById(R.id.cancelbutton);
        edNomeTipoEpi = (EditText) mContentView.findViewById(R.id.editText2);


        addButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                AddTipoEpi tipoepi=new AddTipoEpi(edNomeTipoEpi.getText().toString());
                pbar.setVisibility(View.VISIBLE);
                addTipoEpi(tipoepi);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new TipoEpiFragment())
                        .commit();
            }
        });


        return mContentView;
    }

    public void addTipoEpi(AddTipoEpi epi){
        Call<TipoEpis> call = RetrofitClient
                .getInstance()
                .getApi()
                .addTipoEpi("Bearer "+mUser.getString("token", ""), epi);

        call.enqueue(new Callback<TipoEpis>() {
            @Override
            public void onResponse(Call<TipoEpis> call, Response<TipoEpis> response) {
                Log.d("cc", response.toString());
                if(response.code()==201){
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, new TipoEpiFragment())
                            .commit();
                    pbar.setVisibility(View.GONE);
                }else{
                    pbar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<TipoEpis> call, Throwable t) {
                pbar.setVisibility(View.GONE);
            }
        });

    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String u) {
        if (mListener != null) {
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AddTipoEpiFragment.OnFragmentInteractionListener) {
            mListener = (AddTipoEpiFragment.OnFragmentInteractionListener) context;
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
