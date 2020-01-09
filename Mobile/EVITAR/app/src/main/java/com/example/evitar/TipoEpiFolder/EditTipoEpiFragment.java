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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.evitar.EpiFolder.Epi;
import com.example.evitar.EpiFolder.EpiFragment;
import com.example.evitar.R;
import com.example.evitar.Services.RetrofitClient;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditTipoEpiFragment extends Fragment {
    SharedPreferences mUser;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private int idtipoepi;
    private String nometipoepi;

    private Context mContext;

    private View mContentView;

    private ProgressBar pbar;

    private TipoEpis tipoepiEdit;
    private TipoEpis tipoepi;

    private EditText edNomeTipoEpi;






    private EditTipoEpiFragment.OnFragmentInteractionListener mListener;

    public EditTipoEpiFragment() {
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
    public static EditTipoEpiFragment newInstance(String param1, String param2) {
        EditTipoEpiFragment fragment = new EditTipoEpiFragment();
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
            idtipoepi = getArguments().getInt("idtipoepi");
            nometipoepi = getArguments().getString("nometipoepi");
        }
        mContext=getActivity();




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mContentView = inflater.inflate(R.layout.edit_tipoepi_layout, container, false);
        mUser= PreferenceManager.getDefaultSharedPreferences(mContext);

        tipoepi=new TipoEpis(idtipoepi,nometipoepi);

        pbar=mContentView.findViewById(R.id.progressBar);
        Button editButton= (Button) mContentView.findViewById(R.id.editarbutton);
        TextView edIdTipoEpi = (TextView) mContentView.findViewById(R.id.idepi);
        edNomeTipoEpi = (EditText) mContentView.findViewById(R.id.editText4);
        Button cancelButton= (Button) mContentView.findViewById(R.id.cancelbutton);




        edIdTipoEpi.setText(String.valueOf(tipoepi.getIdTipoEPI()));
        edNomeTipoEpi.setText(tipoepi.getNomeTipoEPI());






        editButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                tipoepiEdit=new TipoEpis(tipoepi.getIdTipoEPI(),edNomeTipoEpi.getText().toString());
                pbar.setVisibility(View.VISIBLE);
                editTipoEpi(tipoepiEdit);
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

    public void editTipoEpi(TipoEpis tipoepiEdited){
        Call<TipoEpis> call = RetrofitClient
                .getInstance()
                .getApi()
                .editTipoEpi("Bearer "+mUser.getString("token", ""), tipoepiEdited.getIdTipoEPI() ,tipoepiEdited);

        call.enqueue(new Callback<TipoEpis>() {
            @Override
            public void onResponse(Call<TipoEpis> call, Response<TipoEpis> response) {
                Log.d("cc", response.toString());
                if(response.code()==204){
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
        if (context instanceof EditTipoEpiFragment.OnFragmentInteractionListener) {
            mListener = (EditTipoEpiFragment.OnFragmentInteractionListener) context;
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
