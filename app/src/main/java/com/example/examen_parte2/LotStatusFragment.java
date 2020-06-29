package com.example.examen_parte2;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.examen_parte2.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class LotStatusFragment extends Fragment implements LotStatusAdapter.AdapterCallback {

    public static final int RESERVE_LOT_ACTIVITY_REQUEST_CODE = 3;
    public static final int DETAIL_HISTORIES_ACTIVITY_REQUEST_CODE = 4;
    private LotViewModel mLotViewModel;

    private int id;
    private String lotName,meter,date,color,photo,video;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private List<Lot> mLots = Collections.emptyList();
    private List<History> mHistories = Collections.emptyList();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mLotViewModel = ViewModelProviders.of(this).get(LotViewModel.class);
        View root = inflater.inflate(R.layout.fragment_lot_status, container, false);


        RecyclerView recyclerView = root.findViewById(R.id.recyclerViewStatus);
        final LotStatusAdapter adapter = new LotStatusAdapter(getContext(),this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mLotViewModel = ViewModelProviders.of(this).get(LotViewModel.class);


        mLotViewModel.getAllWords().observe(this, new Observer<List<Lot>>() {
            @Override
            public void onChanged(@Nullable final List<Lot> lots) {
                adapter.setLots(lots);
                mLots=lots;
            }
        });

        mLotViewModel.getAllHistories().observe(this, new Observer<List<History>>() {
            @Override
            public void onChanged(@Nullable final List<History> histories) {
                adapter.setHistories(histories);
                mHistories = histories;
            }
        });

        adapter.setOnItemClickListener(new LotStatusAdapter.onItemClickListener() {
            @Override
            public void onItemClick(Lot lot) {
                if(lot.getColor().equals("green")){
                    id=lot.getId();
                    lotName=lot.getLot();
                    meter=lot.getMeter();
                    date=lot.getDate();
                    color = lot.getColor();
                    photo = lot.getPhoto();
                    video= lot.getVideo();

                    Intent intent = new Intent(getContext(), ReserveLotActivity.class);
                    intent.putExtra(ReserveLotActivity.EXTRA_ID, lot.getId());
                    startActivityForResult(intent,RESERVE_LOT_ACTIVITY_REQUEST_CODE);
                }else{
                    Toast.makeText(getContext(),R.string.can_not_reserve, Toast.LENGTH_LONG).show();
                }
            }
        });

        adapter.setOnLongItemClickListener(new LotStatusAdapter.onLongItemClickListener() {
            @Override
            public void onLongItemClick(Lot lot) {
                id=lot.getId();
                Intent intent = new Intent(getContext(), LotDetailHistoriesActivity.class);
                intent.putExtra(LotDetailHistoriesActivity.EXTRA_ID, lot.getId());
                startActivityForResult(intent,DETAIL_HISTORIES_ACTIVITY_REQUEST_CODE);
            }
        });

        FloatingActionButton fab = getActivity().findViewById(R.id.fab);
        fab.hide();

        return root;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //reserve lot
        if (requestCode == RESERVE_LOT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {

            int id = data.getIntExtra(ReserveLotActivity.EXTRA_ID,-1);
            if(id == -1){
                Toast.makeText(getContext(), R.string.can_not_reserve, Toast.LENGTH_LONG).show();
                return;
            }
            //new history
            History history = new History(Objects.requireNonNull(data.getStringExtra(ReserveLotActivity.EXTRA_START)),
                    Objects.requireNonNull(data.getStringExtra(ReserveLotActivity.EXTRA_END)),
                    Objects.requireNonNull(data.getStringExtra(ReserveLotActivity.EXTRA_COLOR)), id);
            mLotViewModel.insert(history);


            Date start = Calendar.getInstance().getTime();
            Date end = addDays(start,5);
            String endDate = new SimpleDateFormat("dd/MM/yyyy",
                    Locale.getDefault()).format(end);
            //update lot
          Lot lot = new Lot(lotName,meter,date,"red",endDate,photo,video);
            lot.setId(id);
            mLotViewModel.update(lot);

            Toast.makeText(getContext(), R.string.lot_reserved, Toast.LENGTH_LONG).show();
        }else if(requestCode == RESERVE_LOT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_CANCELED){
            Toast.makeText(getContext(), R.string.not_reserved, Toast.LENGTH_LONG).show();
        }
    }

    //get new dates for history
    public Date addDays(Date date, int days){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, days);
        return calendar.getTime();
    }

    @Override
    public void onMethodCallback(Lot lot) {
        String currentDate =  new SimpleDateFormat("dd/MM/yyyy",
                Locale.getDefault()).format(new Date());
        Date start = Calendar.getInstance().getTime();
        Date end = addDays(start,15);
        String endDate = new SimpleDateFormat("dd/MM/yyyy",
                Locale.getDefault()).format(end);

        for(Lot myLot:mLots){
            if(myLot==lot){
                if(lot.getEnd().equals(currentDate) && lot.getColor().equals("red")){
                    //update lot
                    Lot newLot = new Lot(lot.getLot(),lot.getMeter(),
                            lot.getDate(),"orange",endDate,
                            lot.getPhoto(),lot.getVideo());
                    newLot.setId(lot.getId());
                    mLotViewModel.update(newLot);

                    History newHistory = new History(currentDate,endDate,
                            "orange",lot.getId());
                    mLotViewModel.insert(newHistory);
                    //  addHistory(lot.getId(),startDate,endDate);
                } else if(lot.getEnd().equals(currentDate) && lot.getColor().equals("orange")){
                    //update lot
                    Lot newLot = new Lot(lot.getLot(),lot.getMeter(),
                            lot.getDate(),"green","",
                            lot.getPhoto(),lot.getVideo());
                    newLot.setId(lot.getId());
                    mLotViewModel.update(newLot);
                }
            }
        }

    }
}