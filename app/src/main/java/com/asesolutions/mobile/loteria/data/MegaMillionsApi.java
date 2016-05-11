package com.asesolutions.mobile.loteria.data;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MegaMillionsApi {
    @GET("h6w8-42p9.json")
    Call<List<MegaMillionsApiResult>> listResults(
            @Query("$where") String date,
            @Query("$limit") int number);
}
