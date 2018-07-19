package com.hanfl.lottery;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class LotteryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<LotteryBean> list;

    public LotteryAdapter(Context context,List<LotteryBean> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lottery_layout,parent,false);
        return new LotteryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof LotteryViewHolder){
            LotteryViewHolder holder = (LotteryViewHolder) viewHolder;
            holder.tvLotteryRes.setText(list.get(position).getLottery_res());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
