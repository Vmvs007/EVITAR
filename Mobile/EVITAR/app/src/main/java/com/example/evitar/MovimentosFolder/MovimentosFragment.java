package com.example.evitar.MovimentosFolder;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.evitar.R;
import com.example.evitar.Services.RetrofitClient;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovimentosFragment extends Fragment implements MovimentoDialog.ExampleDialogListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView mRecyclerView;
    private MovimentoAdapter mAdapter;

    private Context mContext;

    private List<Movimento> notif;
    private View mContentView;

    private ProgressBar pbar;

    SharedPreferences mUser;

    private OnFragmentInteractionListener mListener;

    public MovimentosFragment() {
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
    public static MovimentosFragment newInstance(String param1, String param2) {
        MovimentosFragment fragment = new MovimentosFragment();
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

        mContentView = inflater.inflate(R.layout.movements, container, false);
        mUser= PreferenceManager.getDefaultSharedPreferences(mContext);
        pbar=mContentView.findViewById(R.id.progressBar);
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


                    mAdapter=new MovimentoAdapter(mContext, notif,new MovimentoAdapter.OnItemClickListener() {
                        @Override public void onItemClick(Movimento notif) {
                            mListener.onButtonclick(notif);
                        }});

                    mRecyclerView=mContentView.findViewById(R.id.recycler_view);
                    mRecyclerView.setAdapter(mAdapter);

                    mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

                    RecyclerView.ItemDecoration itemDecoration=new DividerItemDecoration(mContext,DividerItemDecoration.VERTICAL);
                    mRecyclerView.addItemDecoration(itemDecoration);
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
        void onButtonclick(Movimento notif);
    }



}
