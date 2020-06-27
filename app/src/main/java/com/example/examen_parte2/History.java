package com.example.examen_parte2;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "history_table")
public class History {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "start")
    private String mStart;

    @NonNull
    @ColumnInfo(name = "end")
    private String mEnd;

    @NonNull
    @ColumnInfo(name = "color")
    private String mColor;

    @NonNull
    @ColumnInfo(name = "lotId")
    private int mLotId;


    public History(@NonNull String start, @NonNull String end, @NonNull String color, @NonNull int lotId) {
        this.mStart = start;
        this.mEnd = end;
        this.mColor = color;
        this.mLotId = lotId;
    }

    @NonNull
    public String getStart() {
        return this.mStart;
    }

    @NonNull
    public String getEnd() {
        return this.mEnd;
    }

    @NonNull
    public String getColor() {
        return this.mColor;
    }

    @NonNull
    public int getLotId() {
        return this.mLotId;
    }

    @NonNull
    public int getId() {
        return this.id;
    }

    public void setId(int id) { this.id = id; }
}
