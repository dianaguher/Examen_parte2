package com.example.examen_parte2;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Activity for entering a word.
 */

public class NewLotActivity extends AppCompatActivity {

    public static final String EXTRA_LOT = "com.example.android.lotlistsql.LOT";
    public static final String EXTRA_METER = "com.example.android.lotlistsql.METER";
    public static final String EXTRA_DATE = "com.example.android.lotlistsql.DATE";
    public static final String EXTRA_COLOR = "com.example.android.lotlistsql.COLOR";
    public static final String EXTRA_END = "com.example.android.lotlistsql.END";
    public static final String EXTRA_PHOTO = "com.example.android.lotlistsql.PHOTO";
    public static final String EXTRA_VIDEO = "com.example.android.lotlistsql.VIDEO";

    private EditText mLotView;
    private EditText mMeterView;
    private EditText mPhotoView;
    private EditText mVideoView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_lot);
        mLotView = findViewById(R.id.new_lot);
        mMeterView = findViewById(R.id.new_meter);
        mPhotoView = findViewById(R.id.new_photo);
        mVideoView = findViewById(R.id.new_video);

        setTitle("Add lot");
        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(mLotView.getText())||TextUtils.isEmpty(mMeterView.getText())
                        ||TextUtils.isEmpty(mPhotoView.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String lot = mLotView.getText().toString();
                    String meter = mMeterView.getText().toString();
                    String photo = mPhotoView.getText().toString();
                    String video = mVideoView.getText().toString();
                    String color = "green";
                    String endDate = "";
                    String date = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(new Date());
                    replyIntent.putExtra(EXTRA_LOT, lot);
                    replyIntent.putExtra(EXTRA_METER, meter);
                    replyIntent.putExtra(EXTRA_DATE, date);
                    replyIntent.putExtra(EXTRA_COLOR, color);
                    replyIntent.putExtra(EXTRA_END,endDate);
                    replyIntent.putExtra(EXTRA_PHOTO, photo);
                    replyIntent.putExtra(EXTRA_VIDEO, video);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }
}

