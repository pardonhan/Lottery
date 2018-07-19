package com.hanfl.lottery.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.POST;

public interface  JuheApi {

    @POST("lottery/history")
    Call<ResponseBody> getLotteryInfo();
}
