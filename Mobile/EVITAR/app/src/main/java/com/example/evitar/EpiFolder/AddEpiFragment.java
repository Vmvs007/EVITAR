package com.example.evitar.EpiFolder;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.evitar.R;
import com.example.evitar.Services.RetrofitClient;

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


        pbar=mContentView.findViewById(R.id.progressBar);
        TextView idColab= (TextView) mContentView.findViewById(R.id.textView34);
        Button addButton= (Button) mContentView.findViewById(R.id.adicionarbutton);
        EditText edIdEpi = (EditText) mContentView.findViewById(R.id.editText);
        EditText edNomeEpi = (EditText) mContentView.findViewById(R.id.editText2);
        EditText edDataReg = (EditText) mContentView.findViewById(R.id.editText3);
        EditText edDataVal = (EditText) mContentView.findViewById(R.id.editText4);

        idColab.setText(String.valueOf(mUser.getInt("user_id", 0)));

        final Epi epi=new Epi(edIdEpi.getId(), edNomeEpi.getText().toString(),edDataReg.getText().toString(), edDataVal.getText().toString(), mUser.getInt("user_id", 0));

        addButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                pbar.setVisibility(View.VISIBLE);
                addEpi(epi);
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
                if(response.code()==200){
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
