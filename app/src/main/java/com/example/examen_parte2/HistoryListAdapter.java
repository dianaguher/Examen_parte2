package com.example.examen_parte2;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

public class HistoryListAdapter extends RecyclerView.Adapter<HistoryListAdapter.HistoryViewHolder> {

    private final LayoutInflater mInflater;
    private List<History> mHistories = Collections.emptyList();
    private HistoryListAdapter.onItemClickListener listener;
    private HistoryListAdapter.onItemLongClickListener listener2;

    HistoryListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    void setHistory(List<History> histories) {
        mHistories = histories;
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
        holder.historyItemView.setText(current.getStart());
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
