package com.hanfl.lottery;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LotteryViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.id_lottery_res)
    public TextView tvLotteryRes;
    public LotteryViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
