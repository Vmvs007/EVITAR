package com.example.evitar.EpiFolder;

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

import com.example.evitar.R;
import com.example.evitar.Services.RetrofitClient;
import com.example.evitar.TipoEpiFolder.TipoEpis;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditEpiFragment extends Fragment {
    SharedPreferences mUser;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private Long idepi;
    private String nomeepi;
    private String regepi;
    private String valepi;
    private Long idcolab;
    private int valido;
    private int idtipoepi;
    private String nometipoepi;
    private String nomecolab;

    private Context mContext;

    private View mContentView;

    private ProgressBar pbar;

    private Epi epiEdit;
    private Epi epi;
    private int check;

    private CheckBox checkBox;
    private EditText edDataVal;

    private int tipo;

    private Spinner mSpinner;
    private String[] mTipos;
    private Integer[] mTiposIds;
    private List<TipoEpis> tiposEpi;






    private EditEpiFragment.OnFragmentInteractionListener mListener;

    public EditEpiFragment() {
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
    public static EditEpiFragment newInstance(String param1, String param2) {
        EditEpiFragment fragment = new EditEpiFragment();
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
            idepi = getArguments().getLong("idepi");
            nomeepi = getArguments().getString("nomeepi");
            regepi = getArguments().getString("regepi");
            valepi = getArguments().getString("valepi");
            idcolab = getArguments().getLong("idcolab");
            valido = getArguments().getInt("valido");
            idtipoepi = getArguments().getInt("idtipoepi");
            nometipoepi = getArguments().getString("nometipoepi");
            nomecolab = getArguments().getString("nomecolab");
        }
        mContext=getActivity();




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mContentView = inflater.inflate(R.layout.edit_epi_layout, container, false);
        mUser= PreferenceManager.getDefaultSharedPreferences(mContext);
        mSpinner=mContentView.findViewById(R.id.spinner);

        epi=new Epi(idepi,nomeepi,regepi,valepi,idcolab,valido,idtipoepi,nometipoepi,nomecolab);

        getTipoEpis();

        pbar=mContentView.findViewById(R.id.progressBar);
        TextView nomeColab= (TextView) mContentView.findViewById(R.id.textView34);
        Button editButton= (Button) mContentView.findViewById(R.id.editarbutton);
        TextView edIdEpi = (TextView) mContentView.findViewById(R.id.idepi);
        TextView edNomeEpi = (TextView) mContentView.findViewById(R.id.nomeepi);
        TextView edDataReg = (TextView) mContentView.findViewById(R.id.dataregisto);
        edDataVal = (EditText) mContentView.findViewById(R.id.editText4);
        Button cancelButton= (Button) mContentView.findViewById(R.id.cancelbutton);
        checkBox=(CheckBox) mContentView.findViewById(R.id.checkBox);

        if(epi.getValido()==1){
            checkBox.setChecked(true);
        } else{
            checkBox.setChecked(false);
        }




        edIdEpi.setText(String.valueOf(epi.getIdEPI()));
        edNomeEpi.setText(epi.getNomeEPI());
        edDataReg.setText(epi.getDataRegistoEPI());
        edDataVal.setText(epi.getDataValidadeEPI());
        nomeColab.setText(epi.getNomeInspector());






        editButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (checkBox.isChecked()){
                    epiEdit=new Epi(epi.getIdEPI(), epi.getNomeEPI(), epi.getDataRegistoEPI(), edDataVal.getText().toString(), mUser.getLong("user_id", 0), 1,tipo);
                } else{
                    epiEdit=new Epi(epi.getIdEPI(), epi.getNomeEPI(), epi.getDataRegistoEPI(), edDataVal.getText().toString(), mUser.getLong("user_id", 0), 0,tipo);
                }
                pbar.setVisibility(View.VISIBLE);
                editEpi(epiEdit);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new EpiFragment())
                        .commit();
            }
        });


        return mContentView;
    }

    private int getPositionTipo(int id){
        int count=0;
        for (int i : mTiposIds){
            if(i==id){
                return count;
            }
            count++;
        }
        return 0;
    }

    public void editEpi(Epi epiEdited){
        Call<Epi> call = RetrofitClient
                .getInstance()
                .getApi()
                .editEpi("Bearer "+mUser.getString("token", ""), epiEdited.getIdEPI() ,epiEdited);

        call.enqueue(new Callback<Epi>() {
            @Override
            public void onResponse(Call<Epi> call, Response<Epi> response) {
                Log.d("cc", response.toString());
                if(response.code()==204){
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, new EpiFragment())
                            .commit();
                    pbar.setVisibility(View.GONE);
                }else{
                    pbar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<Epi> call, Throwable t) {
                pbar.setVisibility(View.GONE);
            }
        });

    }

    private void getTipoEpis(){
        Call<List<TipoEpis>> call = RetrofitClient
                .getInstance()
                .getApi()
                .getTipoEpis("Bearer "+mUser.getString("token", ""));

        call.enqueue(new Callback<List<TipoEpis>>() {
            @Override
            public void onResponse(Call<List<TipoEpis>> call, Response<List<TipoEpis>> response) {
                Log.d("cc", response.toString());
                if(response.code()==200){
                    tiposEpi=response.body();
                    int count=0;

                    for(TipoEpis t : tiposEpi){
                        if(count>0){
                            mTipos=append(mTipos, t.getNomeTipoEPI());
                            mTiposIds=append(mTiposIds, t.getIdTipoEPI());
                        }else{
                            mTiposIds=new Integer[]{t.getIdTipoEPI()};
                            mTipos=new String[]{t.getNomeTipoEPI()};
                            count++;
                        }
                    }
                    mSpinner=mContentView.findViewById(R.id.spinner);

                    try {
                        ArrayAdapter aa=new ArrayAdapter(mContext, android.R.layout.simple_spinner_item, mTipos);
                        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        mSpinner.setAdapter(aa);
                    }catch (NullPointerException n){

                    }




                    mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            tipo=mTiposIds[position];
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                    mSpinner.setSelection(getPositionTipo(idtipoepi));
                }else{
                }
            }

            @Override
            public void onFailure(Call<List<TipoEpis>> call, Throwable t) {
                Log.d("cc", t.getMessage());
                pbar.setVisibility(View.GONE);
            }
        });
    }

    private <T> T[] append(T[] arr, T element) {
        final int N = arr.length;
        arr = Arrays.copyOf(arr, N + 1);
        arr[N] = element;
        return arr;
    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String u) {
        if (mListener != null) {
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof EditEpiFragment.OnFragmentInteractionListener) {
            mListener = (EditEpiFragment.OnFragmentInteractionListener) context;
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
