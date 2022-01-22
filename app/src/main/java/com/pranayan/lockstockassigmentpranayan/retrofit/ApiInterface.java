package com.pranayan.lockstockassigmentpranayan.retrofit;

import com.pranayan.lockstockassigmentpranayan.model.Root;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("3/movie/top_rated?")
    Call<Root> getMovies(@Query("api_key") String api_key, @Query("page") Integer page);


}
