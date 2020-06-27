package com.example.examen_parte2;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.Collections;
import java.util.List;

public class LotListAdapter extends RecyclerView.Adapter<LotListAdapter.LotViewHolder> {

    private final LayoutInflater mInflater;
    private List<Lot> mLots = Collections.emptyList();
    private List<History> mHistories = Collections.emptyList();
    private onItemClickListener listener;
    private onItemLongClickListener listener2;

    LotListAdapter(Context context) {
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
    }

    class LotViewHolder extends RecyclerView.ViewHolder {
        private final TextView lotItemView;
        private LotViewHolder(View itemView) {
            super(itemView);
            lotItemView = itemView.findViewById(R.id.lot);

            lotItemView.setOnClickListener( new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(listener!= null && position != RecyclerView.NO_POSITION){
                        listener.onItemClick(mLots.get(position));
                    }
                }
            });

            lotItemView.setOnLongClickListener(new View.OnLongClickListener(){
                @Override
                public boolean onLongClick(View v) {
                    int position = getAdapterPosition();
                    if(listener2!= null && position != RecyclerView.NO_POSITION){
                        Lot lot = mLots.get(position);
                        if(mHistories.isEmpty()){
                            listener2.onItemLongClick(lot);
                        }else{
                            for(History history : mHistories){
                                if(lot.getId() == history.getLotId()){
                                    listener2.onItemLongClick(lot,history);
                                }else{
                                    listener2.onItemLongClick(lot);
                                }
                            }
                        }
                    }
                    return true;
                }
            });
        }
    }

    public interface onItemClickListener{
        void onItemClick(Lot lot);
    }
    public  void setOnItemClickListener(onItemClickListener listener){
        this.listener = listener;
    }


    public interface onItemLongClickListener{
        void onItemLongClick(Lot lot);
        void onItemLongClick(Lot lot,History history);
    }
    public  void setOnItemLongClickListener(onItemLongClickListener listener){
        this.listener2 = listener;
    }
}
