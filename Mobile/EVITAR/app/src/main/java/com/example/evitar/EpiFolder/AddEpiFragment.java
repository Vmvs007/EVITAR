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
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import com.example.evitar.R;
import com.example.evitar.Services.RetrofitClient;
import com.example.evitar.TipoEpiFolder.TipoEpis;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddEpiFragment extends Fragment {
    SharedPreferences mUser;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Context mContext;

    private View mContentView;

    private ProgressBar pbar;

    private EditText edNomeEpi;
    private EditText edDataVal;
    private EditText edIdEpi;

    private int tipo;

    private Spinner mSpinner;
    private String[] mTipos;
    private Integer[] mTiposIds;
    private List<TipoEpis> tiposEpi;




    private AddEpiFragment.OnFragmentInteractionListener mListener;

    public AddEpiFragment() {
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
    public static AddEpiFragment newInstance(String param1, String param2) {
        AddEpiFragment fragment = new AddEpiFragment();
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

        mContentView = inflater.inflate(R.layout.add_epi_layout, container, false);
        mUser= PreferenceManager.getDefaultSharedPreferences(mContext);


        getTipoEpis();


        pbar=mContentView.findViewById(R.id.progressBar);
        Button addButton= (Button) mContentView.findViewById(R.id.adicionarbutton);
        Button cancelButton= (Button) mContentView.findViewById(R.id.cancelbutton);
        edNomeEpi = (EditText) mContentView.findViewById(R.id.editText2);
        edDataVal = (EditText) mContentView.findViewById(R.id.editText4);
        edIdEpi = (EditText) mContentView.findViewById(R.id.editText21);


        addButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Date currentTime = Calendar.getInstance().getTime();
                DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
                DateFormat dateFormat1 = new SimpleDateFormat("HH:mm:ss");
                String strDate = dateFormat.format(currentTime)+"T"+dateFormat1.format(currentTime);
                Log.d("cc", strDate);
                Epi epi=new Epi(Long.parseLong(edIdEpi.getText().toString()),edNomeEpi.getText().toString(), strDate, edDataVal.getText().toString(), mUser.getLong("user_id", 0), 1, tipo);
                pbar.setVisibility(View.VISIBLE);
                addEpi(epi);
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

    public void addEpi(Epi epi){
        Call<Epi> call = RetrofitClient
                .getInstance()
                .getApi()
                .addEpi("Bearer "+mUser.getString("token", ""), epi);

        call.enqueue(new Callback<Epi>() {
            @Override
            public void onResponse(Call<Epi> call, Response<Epi> response) {
                Log.d("cc", response.toString());
                if(response.code()==201){
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
                Log.d("cc", t.getMessage());
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
        if (context instanceof AddEpiFragment.OnFragmentInteractionListener) {
            mListener = (AddEpiFragment.OnFragmentInteractionListener) context;
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
