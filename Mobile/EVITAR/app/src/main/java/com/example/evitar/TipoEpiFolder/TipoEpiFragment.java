package com.example.evitar.TipoEpiFolder;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.evitar.EpiFolder.AddEpiFragment;
import com.example.evitar.EpiFolder.Epi;
import com.example.evitar.EpiFolder.EpiAdapter;
import com.example.evitar.EpiFolder.EpiDialog;
import com.example.evitar.EpiFolder.EpiFragment;
import com.example.evitar.R;
import com.example.evitar.Services.RetrofitClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TipoEpiFragment extends Fragment implements TipoEpiDialog.ExampleDialogListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView mRecyclerView;
    private TipoEpiAdapter mAdapter;

    private Context mContext;
    private List<TipoEpis> epis;

    private View mContentView;
    private ProgressBar pbar;

    SharedPreferences mUser;





    private TipoEpiFragment.OnFragmentInteractionListener mListener;

    public TipoEpiFragment() {
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
    public static TipoEpiFragment newInstance(String param1, String param2) {
        TipoEpiFragment fragment = new TipoEpiFragment();
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

        mContentView = inflater.inflate(R.layout.tipoepi_layout, container, false);
        mUser= PreferenceManager.getDefaultSharedPreferences(mContext);
        pbar=mContentView.findViewById(R.id.progressBar);
        pbar.setVisibility(View.VISIBLE);

        FloatingActionButton floatingActionButton = (FloatingActionButton) mContentView.findViewById(R.id.floating_action_button);

        floatingActionButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                addEpi();
            }
        });

        Button backbutton=(Button) mContentView.findViewById(R.id.button3);

        backbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new EpiFragment())
                        .commit();
            }
        });




        getTipoEpisServer();



/*

        epiViewModel= ViewModelProviders.of(getActivity()).get(EpiViewModel.class);
        epiViewModel.init();


        mRecyclerView=mContentView.findViewById(R.id.recycler_view);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        RecyclerView.ItemDecoration itemDecoration=new DividerItemDecoration(mContext,DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);



        epiViewModel.getEpis().observe(getActivity(), new Observer<List<Epi>>() {
            @Override
            public void onChanged(List<Epi> epis) {
                mAdapter = new EpiAdapter(mContext, epiViewModel.getEpis().getValue(), new EpiAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Epi epi) {
                        mListener.onButtonclick(epi);
                    }
                });
            }
        });*/

        return mContentView;
    }

    private void getTipoEpisServer() {
        Call<List<TipoEpis>> call = RetrofitClient
                .getInstance()
                .getApi()
                .getTipoEpis("Bearer "+mUser.getString("token", ""));
        call.enqueue(new Callback<List<TipoEpis>>() {
            @Override
            public void onResponse(Call<List<TipoEpis>> call, Response<List<TipoEpis>> response) {
                if(response.code()==200){
                    epis=response.body();

                    mAdapter=new TipoEpiAdapter(mContext, epis ,new TipoEpiAdapter.OnItemClickListener() {
                        @Override public void onItemClick(TipoEpis epi) {
                            mListener.onButtonclick(epi);
                        }});
                    mRecyclerView=mContentView.findViewById(R.id.recycler_view);
                    mRecyclerView.setAdapter(mAdapter);

                    mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

                    RecyclerView.ItemDecoration itemDecoration=new DividerItemDecoration(mContext,DividerItemDecoration.VERTICAL);
                    mRecyclerView.addItemDecoration(itemDecoration);
                    pbar.setVisibility(View.GONE);

                }else{
                    Toast.makeText(mContext, "Pedidos Epi Wrong!", Toast.LENGTH_SHORT).show();
                    pbar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<TipoEpis>> call, Throwable t) {
                Toast.makeText(mContext, "Pedidos epi Failed "+ t.getMessage(), Toast.LENGTH_SHORT).show();
                pbar.setVisibility(View.GONE);
            }
        });
    }

    private void addEpi(){
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new AddTipoEpiFragment())
                .commit();

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(TipoEpis epi) {
        if (mListener != null) {
            mListener.onButtonclick(epi);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof TipoEpiFragment.OnFragmentInteractionListener) {
            mListener = (TipoEpiFragment.OnFragmentInteractionListener) context;
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
        void onButtonclick(TipoEpis epi);
    }




}
