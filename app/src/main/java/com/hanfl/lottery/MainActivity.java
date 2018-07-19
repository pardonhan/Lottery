package com.hanfl.lottery;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;

import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.chanven.lib.cptr.loadmore.SwipeRefreshHelper;
import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hanfl.lottery.api.JuheApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = MainActivity.class.getSimpleName();
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
        lotteryAdapter = new LotteryAdapter(this, lotteryList);
        recyclerAdapterWithHF = new RecyclerAdapterWithHF(lotteryAdapter);
        recyclerView.setAdapter(recyclerAdapterWithHF);

        swipeRefreshHelper.setOnSwipeRefreshListener(new SwipeRefreshHelper.OnSwipeRefreshListener() {
            @Override
            public void onfresh() {
                isRefresh = true;
                currentPage = 1;
                getLotteryInfo(currentPage);
            }
        });
        swipeRefreshHelper.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void loadMore() {
                isLoadMore = true;
                currentPage++;
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


    private void getLotteryInfo(int cPage) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://apis.juhe.cn/")
                .build();
        JuheApi api = retrofit.create(JuheApi.class);
        Call<ResponseBody> call = api.getLotteryInfo("b41683df277e929cf4991816c0e7b35d", "ssq", cPage + "");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String result = response.body().string();
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        String resJson = jsonObject.getJSONObject("result").getString("lotteryResList");
                        Gson gson = new Gson();
                        List<LotteryBean> list = gson.fromJson(resJson,new TypeToken<List<LotteryBean>>(){}.getType());
                        if (!list.isEmpty()){
                            lotteryList.addAll(list);
                        }
                        recyclerAdapterWithHF.notifyDataSetChangedHF();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.i(TAG, "onResponse: " + result);
                    if (isRefresh) {
                        swipeRefreshHelper.refreshComplete();
                        swipeRefreshHelper.setLoadMoreEnable(true);
                        isRefresh = false;
                    }
                    if (isLoadMore) {
                        isLoadMore = false;
                        swipeRefreshHelper.loadMoreComplete(true);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
