package com.example.examen_parte2;

import android.content.Context;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import static androidx.core.app.ActivityCompat.startActivityForResult;
import static androidx.core.content.ContextCompat.createDeviceProtectedStorageContext;
import static androidx.core.content.ContextCompat.startActivity;


public class LotStatusAdapter extends RecyclerView.Adapter<LotStatusAdapter.LotViewHolder> {

    private final LayoutInflater mInflater;
    private List<Lot> mLots = Collections.emptyList();
    private List<History> mHistories = Collections.emptyList();
    private onItemClickListener listener;

    LotStatusAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    void setLots(List<Lot> lots) {
        mLots = lots;
        notifyDataSetChanged();
    }

    void setHistories(List<History> histories){
        mHistories = histories;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mLots.size();
    }

    @Override
    public LotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.item_lot, parent, false);
        return new LotViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(LotViewHolder holder, final int position) {
        final Lot current = mLots.get(position);
        holder.lotItemView.setText(current.getLot());
        if(holder.lotItemView.getCurrentTextColor() == -16777216){
            holder.lotItemView.setTextColor(Color.parseColor("#5AAC70")); //green
        }
      /*  if (mHistories.isEmpty()){
            holder.lotItemView.setTextColor(Color.parseColor("#5AAC70")); //green
        }else{
            for(History history : mHistories)
            {
                if(current.getId() == history.getLotId()){
                    if(history.getColor().equals("red")){
                        holder.lotItemView.setTextColor(Color.parseColor("#BC0606")); //red
                    } else if(history.getColor().equals("orange")){
                        holder.lotItemView.setTextColor(Color.parseColor("#FF6E40")); //orange
                    }else{
                        holder.lotItemView.setTextColor(Color.parseColor("#5AAC70")); //green
                    }
                }else{
                    holder.lotItemView.setTextColor(Color.parseColor("#5AAC70")); //green
                }
            }
        }*/
       // holder.lotItemView.setTextColor(Color.parseColor("#5AAC70")); //green
    }

    class LotViewHolder extends RecyclerView.ViewHolder {
        private final TextView lotItemView;
        private LotViewHolder(final View itemView) {
            super(itemView);
            lotItemView = itemView.findViewById(R.id.lot);

            lotItemView.setOnClickListener( new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(listener!= null && position != RecyclerView.NO_POSITION){
                        listener.onItemClick(mLots.get(position),lotItemView.getCurrentTextColor());
                    }
                }
            });
        }
    }

    public interface onItemClickListener{
        void onItemClick(Lot lot,int color);
    }

    public  void setOnItemClickListener(onItemClickListener listener){
        this.listener = listener;
    }

}
