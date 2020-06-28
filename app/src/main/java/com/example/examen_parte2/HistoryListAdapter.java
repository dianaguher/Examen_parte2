package com.example.examen_parte2;

import android.content.Context;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HistoryListAdapter extends RecyclerView.Adapter<HistoryListAdapter.HistoryViewHolder> {

    private final LayoutInflater mInflater;
    private List<History> mHistories = Collections.emptyList();
    private List<Lot> mLots = Collections.emptyList();
    private HistoryListAdapter.onItemClickListener listener;
    private HistoryListAdapter.onItemLongClickListener listener2;

    HistoryListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    void setHistory(List<History> histories) {
        mHistories = histories;
        notifyDataSetChanged();
    }

    void setLots(List<Lot> lots) {
        mLots = lots;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mHistories.size();
    }

    @Override
    public HistoryListAdapter.HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.item_lot, parent, false);
        return new HistoryListAdapter.HistoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(HistoryListAdapter.HistoryViewHolder holder, final int position) {
        final History current = mHistories.get(position);
        for(Lot lot:mLots){
            if(current.getLotId() == lot.getId()){
                holder.historyItemView.setText(lot.getLot());
                if(current.getColor().equals("green")){
                    holder.historyItemView.setTextColor(Color.parseColor("#5AAC70")); //green
                }else if(current.getColor().equals("red")){
                    holder.historyItemView.setTextColor(Color.parseColor("#BC0606")); //red
                }else{
                    holder.historyItemView.setTextColor(Color.parseColor("#F69576")); //orange
                }
            }
        }
    }

    class HistoryViewHolder extends RecyclerView.ViewHolder {
        private final TextView historyItemView;
        private HistoryViewHolder(View itemView) {
            super(itemView);
            historyItemView = itemView.findViewById(R.id.lot);

            historyItemView.setOnClickListener( new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(listener!= null && position != RecyclerView.NO_POSITION){
                        listener.onItemClick(mHistories.get(position));
                    }
                }
            });

            historyItemView.setOnLongClickListener(new View.OnLongClickListener(){
                @Override
                public boolean onLongClick(View v) {
                    int position = getAdapterPosition();
                    if(listener2!= null && position != RecyclerView.NO_POSITION){
                        listener2.onItemLongClick(mHistories.get(position));
                    }
                    return true;
                }
            });
        }
    }

    public interface onItemClickListener{
        void onItemClick(History history);
    }
    public  void setOnItemClickListener(HistoryListAdapter.onItemClickListener listener){
        this.listener = listener;
    }


    public interface onItemLongClickListener{
        void onItemLongClick(History history);
    }
    public  void setOnItemLongClickListener(HistoryListAdapter.onItemLongClickListener listener){
        this.listener2 = listener;
    }


}
