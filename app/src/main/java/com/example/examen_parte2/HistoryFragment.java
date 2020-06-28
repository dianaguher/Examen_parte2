package com.example.examen_parte2;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class HistoryFragment extends Fragment {

    private LotViewModel mViewModel;
    private TextView mDateView;
    DatePickerDialog.OnDateSetListener mDateSetListener;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(this).get(LotViewModel.class);
        View root = inflater.inflate(R.layout.fragment_history, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.recyclerViewHistory);
        final HistoryListAdapter adapter = new HistoryListAdapter(getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mViewModel.getAllHistories().observe(this, new Observer<List<History>>() {
            @Override
            public void onChanged(@Nullable final List<History> histories) {
                adapter.setHistory(histories);
            }
        });

        mViewModel.getAllWords().observe(this, new Observer<List<Lot>>() {
            @Override
            public void onChanged(@Nullable final List<Lot> lots) {
                adapter.setLots(lots);
            }
        });

        FloatingActionButton fab = getActivity().findViewById(R.id.fab);
        fab.hide();

        mDateView = (TextView) root.findViewById(R.id.date_search);

        mDateView.setText( new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date()));
        //mViewModel.findHistoryByDate(date);
        //search
          mDateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        requireContext(),
                        android.R.style.Theme_DeviceDefault_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day
                );
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                String date = dayOfMonth + "/" + month + "/" + year;
                mDateView.setText(date);
            }
        };

        Button btnReserve = (Button) root.findViewById(R.id.button_search);
        btnReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if (mDateView.getText().toString().equals("Select start date")) {
                    Toast.makeText(requireContext(), R.string.can_not_search, Toast.LENGTH_LONG).show();
                } else {
                    String date = mDateView.getText().toString();
                    Toast.makeText(requireContext(), "Fecha: "+date, Toast.LENGTH_LONG).show();
                    //mViewModel.findHistoryByDate(date);
                }
            }
        });

        return root;
    }

}