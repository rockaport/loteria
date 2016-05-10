package com.asesolutions.mobile.loteria;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.asesolutions.mobile.loteria.data.Database;
import com.asesolutions.mobile.loteria.data.MegaMillionsApi;
import com.asesolutions.mobile.loteria.data.MegaMillionsApiResult;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {
    int z = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();


        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("https://data.ny.gov/resource/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    int b = 10;
                    MegaMillionsApi service = retrofit.create(MegaMillionsApi.class);
                    Call<List<MegaMillionsApiResult>> x = service.listResults();
                    List<MegaMillionsApiResult> results = x.execute().body();
                    Database.save(results);

                    Timber.d(results.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                    Timber.d("Hey");
                }
            }
        });


//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://api.github.com/")
//                .build();
//
//        GitHubService service = retrofit.create(GitHubService.class);
    }
}
