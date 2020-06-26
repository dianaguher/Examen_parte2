package com.example.examen_parte2;

import android.content.Intent;
import android.content.res.ColorStateList;
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

public class LotStatusFragment extends Fragment {

    private LotViewModel mLotViewModel;

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

        FloatingActionButton fab = getActivity().findViewById(R.id.fab);
        fab.hide();


        return root;
    }

}