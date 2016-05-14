package com.asesolutions.mobile.loteria.data;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

public class MegaMillionsService {
    public static Observable<List<MegaMillionsApiResult>> fetchResults() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://data.ny.gov/resource/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MegaMillionsApi service = retrofit.create(MegaMillionsApi.class);

        // TODO: use the timestamp from the last sync
        Call<List<MegaMillionsApiResult>> listCall = service.listResults("draw_date>'2005-01-01T14:00:00'", 50000);

        return Observable.fromCallable(() -> listCall.execute().body());
    }
}
