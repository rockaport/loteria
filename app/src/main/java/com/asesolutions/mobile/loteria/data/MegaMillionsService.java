package com.asesolutions.mobile.loteria.data;

import com.asesolutions.mobile.loteria.database.Database;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MegaMillionsService {
    public static void syncDatabase() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://data.ny.gov/resource/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MegaMillionsApi service = retrofit.create(MegaMillionsApi.class);

        // TODO: use the timestamp from the last sync
        Call<List<MegaMillionsApiResult>> listCall = service.listResults("draw_date>'2005-01-01T14:00:00'", 50000);

        try {
            Database.save(listCall.execute().body());
        } catch (IOException e) {
            e.printStackTrace();
            // TODO: 5/13/16 THis needs to get handled properly, also, this service should fetch
            // data and some other class (probably) should manage saving it to the database.
            // Should use rxjava
        }
    }
}
