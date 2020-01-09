package com.example.evitar.MovimentosFolder;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.evitar.R;
import com.example.evitar.Services.RetrofitClient;
import com.example.evitar.TipoEpiFolder.TipoEpiFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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
    private List<Movimento> currentNotif;
    private List<Movimento> newNotif;
    private View mContentView;

    private ProgressBar pbar;
    private Timer timer;

    private MovimentoViewModel mvm;

    private MediaPlayer player;

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
        Button refresh=(Button) mContentView.findViewById(R.id.button4);
        refresh.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new MovimentosFragment())
                        .commit();
            }
        });
        refresh.setVisibility(View.GONE);
        pbar.setVisibility(View.VISIBLE);
        getNotificationsServer();

        timer = new Timer();
        timer.scheduleAtFixedRate( new TimerTask()
        {
            public void run() {
                /*
               getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new MovimentosFragment())
                        .commit();*/
                getNotifications();
            }
        }, 3000, 3000);


/*
        mvm= ViewModelProviders.of(getActivity()).get(MovimentoViewModel.class);
        mvm.init(mUser.getString("token", ""));
        mvm.getMovimentos().observe(this, new Observer<List<Movimento>>() {
            @Override
            public void onChanged(List<Movimento> movimentos) {
                initRecyclerView(movimentos);
            }
        });*/





        return mContentView;
    }

    @Override
    public void onPause() {
        super.onPause();
        timer.cancel();
    }

    private void initRecyclerView(List<Movimento> movimentos){
        mAdapter=new MovimentoAdapter(mContext, movimentos ,new MovimentoAdapter.OnItemClickListener() {
            @Override public void onItemClick(Movimento notif) {
                mListener.onButtonclick(notif);
            }});

        mRecyclerView=mContentView.findViewById(R.id.recycler_view);


        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        RecyclerView.ItemDecoration itemDecoration=new DividerItemDecoration(mContext,DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);
    }

    private void getNotificationsServer() {
        Date currentTime = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
        String strDate = dateFormat.format(currentTime);
        Call<List<Movimento>> call = RetrofitClient
                .getInstance()
                .getApi()
                .getMovimentosporDia("Bearer "+mUser.getString("token", ""), strDate);
        call.enqueue(new Callback<List<Movimento>>() {

            @Override
            public void onResponse(Call<List<Movimento>> call, Response<List<Movimento>> response) {
                Log.d("cc", response.toString());
                if(response.code()==200){
                    notif=response.body();
                    currentNotif=notif;


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


    private void getNotifications() {
        Date currentTime = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
        String strDate = dateFormat.format(currentTime);
        Call<List<Movimento>> call = RetrofitClient
                .getInstance()
                .getApi()
                .getMovimentosporDia("Bearer "+mUser.getString("token", ""), strDate);
        call.enqueue(new Callback<List<Movimento>>() {

            @Override
            public void onResponse(Call<List<Movimento>> call, Response<List<Movimento>> response) {
                Log.d("cc", response.toString());
                if(response.code()==200){
                    notif=response.body();

                    mAdapter=new MovimentoAdapter(mContext, notif,new MovimentoAdapter.OnItemClickListener() {
                        @Override public void onItemClick(Movimento notif) {
                            mListener.onButtonclick(notif);
                        }});

                    mRecyclerView=mContentView.findViewById(R.id.recycler_view);
                    mRecyclerView.setAdapter(mAdapter);

                    mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

                    if (notif.size()>currentNotif.size()){
                        int dif=notif.size()-currentNotif.size();

                        for (int i=0;i<dif;i++){
                            if (notif.get(i).getCheck()==0){
                                if (player==null){
                                    player=MediaPlayer.create(mContext,R.raw.alarm);
                                }
                                player.start();
                            }
                        }
                    }
                    currentNotif=notif;
                    if (newNotif!=null){
                        newNotif.clear();
                    }


                }else{
                }
            }

            @Override
            public void onFailure(Call<List<Movimento>> call, Throwable t) {
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
