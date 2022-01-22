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
    private ProgressBar loadingPB;
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
        //if (resultArrayList.size() > 0)
         //  resultArrayList.clear();
        recyclerView = findViewById(R.id.recyclerView);
        load = findViewById(R.id.load);
        load.setVisibility(View.GONE);

        //initRetrofit();
        //initAdapter();
        pagination();
        initRetrofit();
        initAdapter();
       // initRetrofit();
    }

    private void pagination() {

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
               // if (dy > 0) { //check for scroll down
                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();
                    Log.e("visible", "pagination " + visibleItemCount + totalItemCount + pastVisiblesItems);

                    if (!loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                           // loading = false;
                            load.setVisibility(View.VISIBLE);
                            page++;
                            Log.e("...", "Last Item Wow !" + page);
                            //Toast.makeText(getApplicationContext(), "No more data to load!", Toast.LENGTH_SHORT).show();
                            // Do pagination.. i.e. fetch new data
                            //_loadAPI_POST();
                            initRetrofit();

                        }
                    }
               //}
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    private void initAdapter() {
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        movieAdapter = new MovieAdapter(this,resultArrayList);
        recyclerView.setAdapter(movieAdapter);
       // movieAdapter.notifyDataSetChanged();

    }

    private void initRetrofit() {
        //apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Root> call = apiService.getMovies("ec01f8c2eb6ac402f2ca026dc2d9b8fd",page);
        call.enqueue(new Callback<Root>() {
            @Override
            public void onResponse(@NonNull Call<Root> call, @NonNull Response<Root> response) {
               if (response.isSuccessful()){
                   assert response.body() != null;
                   Root root;
                   root=response.body();
                   Log.e("TAG","page"+root.page);
                   Log.e("TAG","response"+root.results.size());
                   resultArrayList.addAll(root.results);
                   Log.e("TAG","resultArrayList"+resultArrayList.size());
                   //initAdapter();
                   movieAdapter.notifyDataSetChanged();

                   Log.e("TAG","size"+resultArrayList.size());
                   //Log.e("TAG","size"+resultArrayList);

//                   for(int i=0;i<=resultArrayList.size()-1;i++){
//                     Result result =new Result();
//                     resultArrayList.add(result);
//                   }
                  // initAdapter();
                   loading = false;
                   load.setVisibility(View.GONE);

                   //response.body().results.toString();
               }

            }


            @Override
            public void onFailure(Call<Root> call, Throwable t) {
                Log.d("TAG", "Response = " + t.toString());
            }
        });

    }
}