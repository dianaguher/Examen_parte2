package com.example.examen_parte2;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class ReserveLotActivity extends AppCompatActivity {
    public static final String EXTRA_ID = "com.example.android.wordlistsql.EXTRA_ID";
    public static final String EXTRA_START = "com.example.android.historylistsql.START";
    public static final String EXTRA_COLOR = "com.example.android.historylistsql.COLOR";
    public static final String EXTRA_END = "com.example.android.historylistsql.END";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_lot);



        Button btnReserve = (Button) findViewById(R.id.button_reserve);
        btnReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                Date start = Calendar.getInstance().getTime();
                Date end = addDays(start,5);


                String startDate =  new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(start);
                String endDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(end);
                String color = "red";

                replyIntent.putExtra(EXTRA_START,startDate);
                replyIntent.putExtra(EXTRA_END,endDate);
                replyIntent.putExtra(EXTRA_COLOR,color);

                //lot intent
                final Intent intent = getIntent();
                final int id = intent.getIntExtra(EXTRA_ID,-1);
                if(id != -1){
                    replyIntent.putExtra(EXTRA_ID,id);
                }

                setResult(RESULT_OK, replyIntent);
                finish();
            }
        });

    }

    public Date addDays(Date date, int days){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, days);
        return calendar.getTime();
    }
}
