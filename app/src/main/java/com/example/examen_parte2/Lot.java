package com.example.examen_parte2;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "lot_table")
public class Lot {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "lot")
    private String mLot;

    @NonNull
    @ColumnInfo(name = "meter")
    private String mMeter;

    @NonNull
    @ColumnInfo(name = "date")
    private String mDate;

    @NonNull
    @ColumnInfo(name = "start")
    private String mStart;

    @NonNull
    @ColumnInfo(name = "end")
    private String mEnd;

    @NonNull
    @ColumnInfo(name = "photo")
    private String mPhoto;

    @NonNull
    @ColumnInfo(name = "video")
    private String mVideo;

    public Lot(@NonNull String lot,String meter, String date, String start, String end, String photo, String video) {
        this.mLot = lot;
        this.mMeter = meter;
        this.mDate = date;
        this.mStart = start;
        this.mEnd = end;
        this.mPhoto = photo;
        this.mVideo = video;
    }

    @NonNull
    public String getLot() {
        return this.mLot;
    }

    @NonNull
    public String getMeter() {
        return this.mMeter;
    }

    @NonNull
    public String getDate() {
        return this.mDate;
    }

    @NonNull
    public String getStart() {
        return this.mStart;
    }

    @NonNull
    public String getEnd() { return this.mEnd; }

    @NonNull
    public String getPhoto() {
        return this.mPhoto;
    }

    @NonNull
    public String getVideo() {
        return this.mVideo;
    }

    @NonNull
    public int getId() {
        return this.id;
    }

    public void setId(int id) { this.id = id; }
}
