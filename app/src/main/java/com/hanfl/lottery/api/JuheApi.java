package com.hanfl.lottery.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface  JuheApi {

    @GET("lottery/history")
    Call<ResponseBody> getLotteryInfo(@Query("key") String key,@Query("lottery_id") String lottery_id,@Query("page") String page);
}
