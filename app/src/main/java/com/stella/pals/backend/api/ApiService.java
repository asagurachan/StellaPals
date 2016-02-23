package com.stella.pals.backend.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Asa on 2016/02/18.
 * StellaPals
 */
public interface ApiService {

    @Headers({"Connection:keep-alive", "User-Agent: StellaPals", "DNT:1"})
    @FormUrlEncoded
    @POST("/login.php")
    Call<ResponseBody> login(@Field("action") String action, @Field("login") String username, @Field("password") String password, @Field("auto_login") String autoLogin);

    @FormUrlEncoded
    @POST("/pm.php")
    Call<ResponseBody> getMessageGroups(@Field("view") String viewType, @Field("page") int page);

    @FormUrlEncoded
    @POST("/async/signup.php")
    Call<ResponseBody> signup(@Field("step") int step, @Field("username") String username, @Field("email") String email, @Field("password") String password, @Field("day") int day, @Field("month") int month, @Field("year") int year, @Field("country") String country);

    @FormUrlEncoded
    @POST("/pn.php")
    Call<ResponseBody> getMessages(@Field("thread_id") String threadId, @Field("page") int page);

}
