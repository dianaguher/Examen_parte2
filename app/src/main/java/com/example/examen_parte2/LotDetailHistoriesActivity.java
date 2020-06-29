package com.example.examen_parte2;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class LotDetailHistoriesActivity extends AppCompatActivity {
    public static final String EXTRA_ID = "com.example.android.wordlistsql.EXTRA_ID";
    private LotViewModel mLotViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_histories);
        Intent intent =  getIntent();
        mLotViewModel = ViewModelProviders.of(this).get(LotViewModel.class);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewDetailHistories);
        final LotDetailHistoriesAdapter adapter = new LotDetailHistoriesAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mLotViewModel = ViewModelProviders.of(this).get(LotViewModel.class);

        int id=intent.getIntExtra(EXTRA_ID,-1);
        if(id!=-1){
            mLotViewModel.getHistoriesByLotId(id).observe(this, new Observer<List<History>>() {
                @Override
                public void onChanged(@Nullable final List<History> histories) {
                    adapter.setHistoriesByLotId(histories);
                }
            });
        }
    }
}