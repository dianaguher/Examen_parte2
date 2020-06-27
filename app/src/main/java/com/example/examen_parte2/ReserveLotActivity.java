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

    public static final String EXTRA_LOT_ID = "com.example.android.lotlistsql.EXTRA_LOT_ID";
    public static final String EXTRA_START = "com.example.android.historylistsql.START";
    public static final String EXTRA_COLOR = "com.example.android.historylistsql.COLOR";
    public static final String EXTRA_END = "com.example.android.historylistsql.END";

   // DatePickerDialog.OnDateSetListener mDateSetListener;
   // DatePickerDialog.OnDateSetListener mDateSetListener2;

   // private TextView mStartView;
   // private TextView mEndView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_lot);
       // mStartView = (TextView) findViewById(R.id.reserve_start);
       // mEndView = (TextView) findViewById(R.id.reserve_end);

        final Intent intent = getIntent();
        if(intent.hasExtra(EXTRA_LOT_ID)){
            setTitle("Reserve lot");
        }

      /*  mStartView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        ReserveLotActivity.this,
                        android.R.style.Theme_DeviceDefault_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day
                );
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mEndView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        ReserveLotActivity.this,
                        android.R.style.Theme_DeviceDefault_Dialog_MinWidth,
                        mDateSetListener2,
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
                Log.d("onDateSet: mm/dd/yyyy:",dayOfMonth+ "/" +month+ "/" +year);

                String date = dayOfMonth + "/" + month + "/" + year;
                mStartView.setText(date);
            }
        };

        mDateSetListener2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                Log.d("onDateSet: mm/dd/yyyy:",dayOfMonth+ "/" +month+ "/" +year);

                String date = dayOfMonth + "/" + month + "/" + year;
                mEndView.setText(date);
            }
        };



        Button btnReserve = (Button) findViewById(R.id.button_reserve);
        btnReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if (mStartView.getText().toString().equals("Select start date")
                        || mEndView.getText().toString().equals("Select end date")) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String start = mStartView.getText().toString();
                    String end = mEndView.getText().toString();
                    String color = "red";

                    replyIntent.putExtra(EXTRA_START,start);
                    replyIntent.putExtra(EXTRA_END,end);
                    replyIntent.putExtra(EXTRA_COLOR,color);

                    int id = intent.getIntExtra(EXTRA_LOT_ID,-1);
                    if(id != -1){
                        replyIntent.putExtra(EXTRA_LOT_ID,id);
                    }

                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });


       */

      //prueba
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

                int id = intent.getIntExtra(EXTRA_LOT_ID,-1);
                if(id != -1){
                    replyIntent.putExtra(EXTRA_LOT_ID,id);
                }

                setResult(RESULT_OK, replyIntent);
                finish();
            }
        });

    }



    public Date addDays(Date date, int days){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date); // Configuramos la fecha que se recibe
        calendar.add(Calendar.DAY_OF_YEAR, days);  // numero de días a añadir, o restar en caso de días<0
        return calendar.getTime(); // Devuelve el objeto Date con los nuevos días añadidos
    }
}
