package com.example.examen_parte2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class UpdateLotActivity extends AppCompatActivity {

    public static final String EXTRA_ID = "com.example.android.wordlistsql.EXTRA_ID";
    public static final String EXTRA_LOT = "com.example.android.wordlistsql.LOT";
    public static final String EXTRA_METER = "com.example.android.wordlistsql.METER";
    public static final String EXTRA_DATE = "com.example.android.lotlistsql.DATE";
    public static final String EXTRA_COLOR = "com.example.android.lotlistsql.COLOR";
    public static final String EXTRA_PHOTO = "com.example.android.wordlistsql.PHOTO";
    public static final String EXTRA_VIDEO = "com.example.android.wordlistsql.VIDEO";


    private EditText mEditLotView;
    private EditText mEditMeterView;
    private EditText mEditPhotoView;
    private EditText mEditVideoView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_lot);
        mEditLotView = findViewById(R.id.update_lot);
        mEditMeterView = findViewById(R.id.update_meter);
        mEditPhotoView = findViewById(R.id.update_photo);
        mEditVideoView = findViewById(R.id.update_video);

        final Intent intent = getIntent();
        if(intent.hasExtra(EXTRA_ID)){
            setTitle("Update lot");
            mEditLotView.setText(intent.getStringExtra(EXTRA_LOT));
            mEditMeterView.setText(intent.getStringExtra(EXTRA_METER));
            mEditPhotoView.setText(intent.getStringExtra(EXTRA_PHOTO));
            mEditVideoView.setText(intent.getStringExtra(EXTRA_VIDEO));
        }else{
            setTitle("New lot");
        }

        Button btnUpdate = findViewById(R.id.button_update);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(mEditLotView.getText())
                        ||TextUtils.isEmpty(mEditMeterView.getText())
                        ||TextUtils.isEmpty(mEditPhotoView.getText())
                        ||TextUtils.isEmpty(mEditVideoView.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                }else if(mEditLotView.getText().toString().equals(intent.getStringExtra(EXTRA_LOT))
                        && mEditMeterView.getText().toString().equals(intent.getStringExtra(EXTRA_METER))
                        && mEditPhotoView.getText().toString().equals(intent.getStringExtra(EXTRA_PHOTO))
                        && mEditVideoView.getText().toString().equals(intent.getStringExtra(EXTRA_VIDEO))){
                    setResult(RESULT_FIRST_USER, replyIntent);
                } else {
                    String lot = mEditLotView.getText().toString();
                    String meter = mEditMeterView.getText().toString();
                    String photo = mEditPhotoView.getText().toString();
                    String video = mEditVideoView.getText().toString();
                    String color = intent.getStringExtra(EXTRA_COLOR);
                    String date = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(new Date());

                    replyIntent.putExtra(EXTRA_LOT,lot);
                    replyIntent.putExtra(EXTRA_DATE,date);
                    replyIntent.putExtra(EXTRA_METER,meter);
                    replyIntent.putExtra(EXTRA_COLOR,color);
                    replyIntent.putExtra(EXTRA_PHOTO,photo);
                    replyIntent.putExtra(EXTRA_VIDEO,video);

                    int id = intent.getIntExtra(EXTRA_ID,-1);
                    if(id != -1){
                        replyIntent.putExtra(EXTRA_ID,id);
                    }

                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }
}
