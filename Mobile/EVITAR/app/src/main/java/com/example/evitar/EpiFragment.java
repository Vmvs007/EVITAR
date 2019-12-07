package com.example.evitar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EpiFragment extends Fragment implements EpiDialog.ExampleDialogListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView mRecyclerView;
    private EpiAdapter mAdapter;

    private Context mContext;
    List<Epi> epis;


    private EpiFragment.OnFragmentInteractionListener mListener;

    public EpiFragment() {
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
    public static EpiFragment newInstance(String param1, String param2) {
        EpiFragment fragment = new EpiFragment();
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
        getAllEpis();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View mContentView = inflater.inflate(R.layout.epi_layout, container, false);






/*
        Epi epi1=new Epi(1,"ddd", "123.123122.31", "12321,123123,1232", 1);
        Epi epi2=new Epi(2,"ddd", "123.123122.31", "12321,123123,1232", 1);
        Epi epi3=new Epi(3,"ddd", "123.123122.31", "12321,123123,1232", 1);


        List<Epi> epis=Arrays.asList(epi1,epi2,epi3);*/
        Log.d("cc","aki");
        mAdapter=new EpiAdapter(mContext, epis ,new EpiAdapter.OnItemClickListener() {
            @Override public void onItemClick(Epi epi) {
                mListener.onButtonclick(epi);
            }});

        mRecyclerView=mContentView.findViewById(R.id.recycler_view);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        RecyclerView.ItemDecoration itemDecoration=new DividerItemDecoration(mContext,DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);

        return mContentView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Epi epi) {
        if (mListener != null) {
            mListener.onButtonclick(epi);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof EpiFragment.OnFragmentInteractionListener) {
            mListener = (EpiFragment.OnFragmentInteractionListener) context;
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
        void onButtonclick(Epi epi);
    }

    public void getAllEpis(){
        Log.d("cc", "asdasdsad");
        Call<List<Epi>> call = RetrofitClient
                .getInstance()
                .getApi()
                .getEpis();
        Log.d("cc", "jhgjgjghj");

        call.enqueue(new Callback<List<Epi>>() {
            @Override
            public void onResponse(Call<List<Epi>> call, Response<List<Epi>> response) {
                Log.d("cc", response.toString());
                if(response.code()==200){
                    epis=response.body();
                }else{
                    Toast.makeText(mContext, "Pedidos Epi Wrong!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Epi>> call, Throwable t) {
                Toast.makeText(mContext, "Pedidos epi Failed "+ t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        Log.d("cc", "done");
    }



}
