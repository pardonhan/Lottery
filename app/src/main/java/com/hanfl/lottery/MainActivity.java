package com.hanfl.lottery;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.chanven.lib.cptr.loadmore.SwipeRefreshHelper;
import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {


    SwipeRefreshHelper swipeRefreshHelper;
    RecyclerAdapterWithHF recyclerAdapterWithHF;

    @BindView(R.id.swipe_refresh_layout)
    public SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.recycler_view)
    public RecyclerView recyclerView;
    LotteryAdapter lotteryAdapter;
    List<LotteryBean> lotteryList = new ArrayList<>();

    private boolean isRefresh = false;
    private boolean isLoadMore = false;
    private int currentPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initActivity();
    }

    private void initActivity() {
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        swipeRefreshHelper = new SwipeRefreshHelper(swipeRefreshLayout);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        lotteryAdapter = new LotteryAdapter(this,lotteryList);
        recyclerAdapterWithHF = new RecyclerAdapterWithHF(lotteryAdapter);
        recyclerView.setAdapter(recyclerAdapterWithHF);

        swipeRefreshHelper.setOnSwipeRefreshListener(new SwipeRefreshHelper.OnSwipeRefreshListener() {
            @Override
            public void onfresh() {
                isRefresh = true;
                currentPage =1;
                getLotteryInfo(currentPage);
            }
        });
        swipeRefreshHelper.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void loadMore() {
                isLoadMore = true;
                currentPage ++;
                getLotteryInfo(currentPage);
            }
        });
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshHelper.autoRefresh();
            }
        });
    }


    private void getLotteryInfo(int cPage){

    }
}
