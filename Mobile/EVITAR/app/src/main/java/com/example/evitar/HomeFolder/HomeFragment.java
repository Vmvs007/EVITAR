package com.example.evitar.HomeFolder;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.evitar.DashFolder.Stats;
import com.example.evitar.EpiFolder.Epi;
import com.example.evitar.EpiFolder.EpiFragment;
import com.example.evitar.R;
import com.example.evitar.Services.RetrofitClient;
import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Context mContext;

    private View mContentView;

    private int movDay;

    SharedPreferences mUser;

    private LastMonths lm;

    private String[] meses={"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};

    private Integer currentMes;
    private Date currentTime;

    private int count;





    private HomeFragment.OnFragmentInteractionListener mListener;

    public HomeFragment() {
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
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

        mContentView = inflater.inflate(R.layout.home, container, false);
        mUser= PreferenceManager.getDefaultSharedPreferences(mContext);
        getStats();

        FloatingActionButton floatingActionButton = (FloatingActionButton) mContentView.findViewById(R.id.floating_action_button);

        floatingActionButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new EpiFragment())
                        .commit();
            }
        });

        TextView nome=mContentView.findViewById(R.id.nome);
        TextView timeday=mContentView.findViewById(R.id.timeday);

        currentTime = Calendar.getInstance().getTime();
        DateFormat dateFormat1 = new SimpleDateFormat("HH");
        Integer hora =Integer.parseInt(dateFormat1.format(currentTime));

        String tipodia="Good Day,";

        if (hora>6 && hora<13){
            tipodia="Good Morning,";
        }else if (hora>12 && hora<20){
            tipodia="Good Afternoon,";
        }else if ((hora>19 && hora<24)||(hora>=0 && hora<7) ){
            tipodia="Good Night,";
        }

        timeday.setText(tipodia);
        nome.setText(mUser.getString("nome", "Undefined"));



        getMeses();





        return mContentView;
    }

    private void getMeses() {
        DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
        DateFormat dateFormat2 = new SimpleDateFormat("MM");
        currentMes =Integer.parseInt(dateFormat2.format(currentTime));
        String strDate = dateFormat.format(currentTime);
        Call<LastMonths> call = RetrofitClient
                .getInstance()
                .getApi()
                .getLastMonths("Bearer "+mUser.getString("token", ""), strDate);
        call.enqueue(new Callback<LastMonths>() {
            @Override
            public void onResponse(Call<LastMonths> call, Response<LastMonths> response) {
                Log.d("cc", response.toString());
                if(response.code()==200){
                    lm=response.body();
                    Integer[] warnings={lm.getMes5(),lm.getMes4(),lm.getMes3(),lm.getMes2(),lm.getMes1(),lm.getMes()};
                    count=0;

                    LineChart chart = (LineChart) mContentView.findViewById(R.id.chart);
                    List<Entry> entries = new ArrayList<Entry>();

                    ArrayList<String> xLabel = new ArrayList<>();
                    for (int i =0; i<6 ; i++) {
                        entries.add(new Entry(i, warnings[i] ) );
                        xLabel.add(meses[currentMes-1]);
                        currentMes--;
                        if (currentMes==0){
                            currentMes=12;
                        }
                    }
                    ArrayList novoArray=reverseArrayList(xLabel);

                    LineDataSet dataSet = new LineDataSet(entries, "Monthly Warnings");
                    LineData lineData = new LineData(dataSet);

                    ValueFormatter xAxisFormatter = new MonthAxisValueFormatter(chart,novoArray);
                    XAxis xAxis = chart.getXAxis();
                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    xAxis.setDrawGridLines(false);
                    xAxis.setGranularity(1f);
                    xAxis.setLabelCount(6);
                    xAxis.setValueFormatter(xAxisFormatter);

                    chart.setData(lineData);
                    chart.invalidate();






                }else{
                    Toast.makeText(mContext, "Fail" + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LastMonths> call, Throwable t) {
                Toast.makeText(mContext, "Failed "+ t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public class MonthAxisValueFormatter extends ValueFormatter {
        private final LineChart chart;
        private final ArrayList<String> xLabel;
        public MonthAxisValueFormatter(LineChart chart, ArrayList<String> xLabel) {
            this.chart = chart;
            this.xLabel = xLabel;
        }
        @Override
        public String getFormattedValue(float value) {
            return xLabel.get((int)value);
        }
    }

    public ArrayList<String> reverseArrayList(ArrayList<String> alist)
    {
        ArrayList<String> revArrayList = new ArrayList<String>();
        for (int i = alist.size() - 1; i >= 0; i--) {
            revArrayList.add(alist.get(i));
        }
        return revArrayList;
    }



    private void getStats() {
        Date currentTime = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
        String strDate = dateFormat.format(currentTime);
        Call<Stats> call = RetrofitClient
                .getInstance()
                .getApi()
                .getStats("Bearer "+mUser.getString("token", ""), strDate);
        call.enqueue(new Callback<Stats>() {
            @Override
            public void onResponse(Call<Stats> call, Response<Stats> response) {
                if(response.code()==200){
                    movDay=response.body().getMovimentosDiarios();

                    TextView day=mContentView.findViewById(R.id.textView39);

                    day.setText(String.valueOf(movDay));


                }else{
                    Toast.makeText(mContext, "Fail" + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Stats> call, Throwable t) {
                Toast.makeText(mContext, "Failed "+ t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Epi epi) {
        if (mListener != null) {
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof HomeFragment.OnFragmentInteractionListener) {
            mListener = (HomeFragment.OnFragmentInteractionListener) context;
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
