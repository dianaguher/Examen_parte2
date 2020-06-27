package com.example.examen_parte2;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.examen_parte2.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class LotFragment extends Fragment {

    public static final int UPDATE_LOT_ACTIVITY_REQUEST_CODE = 2;
    private LotViewModel mLotViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mLotViewModel = ViewModelProviders.of(this).get(LotViewModel.class);
        View root = inflater.inflate(R.layout.fragment_lot, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.recyclerView);
        final LotListAdapter adapter = new LotListAdapter(getContext());
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

        //delete lot
        adapter.setOnItemLongClickListener(new LotListAdapter.onItemLongClickListener() {
            @Override
            public void onItemLongClick(Lot lot) {
                mLotViewModel.delete(lot);
                Toast.makeText(getContext(),  R.string.lot_deleted, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onItemLongClick(Lot lot, History history) {
                mLotViewModel.delete(lot);
                mLotViewModel.delete(history);
                Toast.makeText(getContext(),  R.string.lot_deleted, Toast.LENGTH_LONG).show();
            }
        });

        adapter.setOnItemClickListener(new LotListAdapter.onItemClickListener() {
            @Override
            public void onItemClick(Lot lot) {
                Intent intent = new Intent(getContext(), UpdateLotActivity.class);
                intent.putExtra(UpdateLotActivity.EXTRA_ID, lot.getId());
                intent.putExtra(UpdateLotActivity.EXTRA_LOT, lot.getLot());
                intent.putExtra(UpdateLotActivity.EXTRA_METER, lot.getMeter());
                intent.putExtra(UpdateLotActivity.EXTRA_COLOR, lot.getColor());
                intent.putExtra(UpdateLotActivity.EXTRA_PHOTO, lot.getPhoto());
                intent.putExtra(UpdateLotActivity.EXTRA_VIDEO, lot.getVideo());
                startActivityForResult(intent,UPDATE_LOT_ACTIVITY_REQUEST_CODE);
            }
        });

        FloatingActionButton fab = getActivity().findViewById(R.id.fab);
        fab.show();

        return root;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //update lot
        if (requestCode == UPDATE_LOT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            int id = data.getIntExtra(UpdateLotActivity.EXTRA_ID,-1);
            if(id == -1){
                Toast.makeText(getContext(), R.string.can_not_update, Toast.LENGTH_LONG).show();
                return;
            }
            Lot lot = new Lot(data.getStringExtra(UpdateLotActivity.EXTRA_LOT),
                    data.getStringExtra(UpdateLotActivity.EXTRA_METER),
                    data.getStringExtra(UpdateLotActivity.EXTRA_DATE),
                    data.getStringExtra(UpdateLotActivity.EXTRA_COLOR),
                    data.getStringExtra(UpdateLotActivity.EXTRA_PHOTO),
                    data.getStringExtra(UpdateLotActivity.EXTRA_VIDEO));
            lot.setId(id);
            mLotViewModel.update(lot);
            Toast.makeText(getContext(), R.string.lot_updated, Toast.LENGTH_LONG).show();

        }else if(requestCode == UPDATE_LOT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_CANCELED) {
            Toast.makeText(getContext(), R.string.not_updated, Toast.LENGTH_LONG).show();
        }
    }


}