package com.pranayan.lockstockassigmentpranayan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pranayan.lockstockassigmentpranayan.adapter.MovieAdapter;
import com.pranayan.lockstockassigmentpranayan.model.Result;
import com.pranayan.lockstockassigmentpranayan.model.Root;
import com.pranayan.lockstockassigmentpranayan.retrofit.ApiClient;
import com.pranayan.lockstockassigmentpranayan.retrofit.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private ArrayList<Result> resultArrayList = new ArrayList<>();
    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
    int page=1;
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    LinearLayoutManager mLayoutManager;
    TextView load;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        load = findViewById(R.id.load);
        load.setVisibility(View.GONE);
        pagination();
        initRetrofit();
        initAdapter();
    }

    private void pagination() {

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

                    if (!loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            load.setVisibility(View.VISIBLE);
                            page++;
                            initRetrofit();

                        }
                    }
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    private void initAdapter() {
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        movieAdapter = new MovieAdapter(this,resultArrayList);
        recyclerView.setAdapter(movieAdapter);

    }

    private void initRetrofit() {
        Call<Root> call = apiService.getMovies("ec01f8c2eb6ac402f2ca026dc2d9b8fd",page);
        call.enqueue(new Callback<Root>() {
            @Override
            public void onResponse(@NonNull Call<Root> call, @NonNull Response<Root> response) {
               if (response.isSuccessful()){
                   assert response.body() != null;
                   Root root;
                   root=response.body();
                   resultArrayList.addAll(root.results);
                   movieAdapter.notifyDataSetChanged();
                   loading = false;
                   load.setVisibility(View.GONE);
               }
            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {
            }
        });

    }
}