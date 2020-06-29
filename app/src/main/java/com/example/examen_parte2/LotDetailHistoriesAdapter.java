package com.example.examen_parte2;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;


public class LotDetailHistoriesAdapter extends RecyclerView.Adapter<LotDetailHistoriesAdapter.DetailHistoriesViewHolder> {

    private final LayoutInflater mInflater;
    private List<History> mHistories = Collections.emptyList();
    private List<Lot> mLots = Collections.emptyList();

    LotDetailHistoriesAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    void setHistoriesByLotId(List<History> histories){
        mHistories = histories;
        notifyDataSetChanged();
    }

    void setLotByLotId(List<Lot> lots){
        mLots = lots;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mHistories.size();
    }

    @Override
    public DetailHistoriesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.item_detail_history, parent, false);
        return new DetailHistoriesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DetailHistoriesViewHolder holder, final int position) {
        final History current = mHistories.get(position);
        for(Lot lot:mLots) {
            if (current.getLotId() == lot.getId()) {
                holder.historiesStartDate.setText(current.getStart());
                holder.historiesEndDate.setText(current.getEnd());
                holder.lotName.setText(lot.getLot());
                if (current.getColor().equals("green")) {
                    holder.historiesStartDate.setTextColor(Color.parseColor("#5AAC70")); //green
                    holder.historiesEndDate.setTextColor(Color.parseColor("#5AAC70")); //green
                    holder.lotName.setTextColor(Color.parseColor("#5AAC70")); //green
                } else if (current.getColor().equals("red")) {
                    holder.historiesStartDate.setTextColor(Color.parseColor("#BC0606")); //red
                    holder.historiesEndDate.setTextColor(Color.parseColor("#BC0606")); //red
                    holder.lotName.setTextColor(Color.parseColor("#BC0606")); //red
                } else {
                    holder.historiesStartDate.setTextColor(Color.parseColor("#FF6E40")); //orange
                    holder.historiesEndDate.setTextColor(Color.parseColor("#FF6E40")); //orange
                    holder.lotName.setTextColor(Color.parseColor("#FF6E40")); //orange
                }
            }
        }
    }

    class DetailHistoriesViewHolder extends RecyclerView.ViewHolder {
        private final TextView historiesStartDate;
        private final TextView historiesEndDate;
        private final TextView lotName;
        private DetailHistoriesViewHolder(final View itemView) {
            super(itemView);
            historiesStartDate = itemView.findViewById(R.id.detailStartDate);
            historiesEndDate = itemView.findViewById(R.id.detailEndDate);
            lotName=itemView.findViewById(R.id.lotName);
        }
    }
}
