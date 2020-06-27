package com.example.examen_parte2;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
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

import java.util.List;
import java.util.Objects;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class LotStatusFragment extends Fragment {

    public static final int RESERVE_LOT_ACTIVITY_REQUEST_CODE = 3;
    private LotViewModel mLotViewModel;

    private int id;
    private String lotName,meter,date,color,photo,video;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mLotViewModel = ViewModelProviders.of(this).get(LotViewModel.class);
        View root = inflater.inflate(R.layout.fragment_lot_status, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.recyclerViewStatus);
        final LotStatusAdapter adapter = new LotStatusAdapter(getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mLotViewModel = ViewModelProviders.of(this).get(LotViewModel.class);

        mLotViewModel.getAllWords().observe(this, new Observer<List<Lot>>() {
            @Override
            public void onChanged(@Nullable final List<Lot> lots) {
                adapter.setLots(lots);
            }
        });

        mLotViewModel.getAllHistories().observe(this, new Observer<List<History>>() {
            @Override
            public void onChanged(@Nullable final List<History> histories) {
                adapter.setHistories(histories);
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
            History history = new History(data.getStringExtra(ReserveLotActivity.EXTRA_START),
                    data.getStringExtra(ReserveLotActivity.EXTRA_END),
                    data.getStringExtra(ReserveLotActivity.EXTRA_COLOR), id);
            mLotViewModel.insert(history);

            //update lot
          Lot lot = new Lot(lotName,meter,date,"red",photo,video);
            lot.setId(id);
            mLotViewModel.update(lot);

            Toast.makeText(getContext(), R.string.lot_reserved, Toast.LENGTH_LONG).show();
        }else if(requestCode == RESERVE_LOT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_CANCELED){
            Toast.makeText(getContext(), R.string.not_reserved, Toast.LENGTH_LONG).show();
        }
    }
}