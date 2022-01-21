package com.pranayan.lockstockassigmentpranayan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.pranayan.lockstockassigmentpranayan.R;
import com.pranayan.lockstockassigmentpranayan.model.Result;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private ArrayList<Result> resultArrayList;
    private LayoutInflater mInflater;

    // data is passed into the constructor
    public MovieAdapter(Context context, ArrayList<Result> resultArrayList) {
        this.mInflater = LayoutInflater.from(context);
        this.resultArrayList = resultArrayList;
    }



    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Result result = resultArrayList.get(position);
        holder.id.setText(String.valueOf(result.id));
        holder.original_language.setText(result.original_language);
        holder.popularity.setText(String.valueOf(result.popularity));
        holder.title.setText(result.title);

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return resultArrayList.size();
    }


    // stores and recycles views as they are scrolled off screen
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView id;
        TextView original_language;
        TextView popularity;
        TextView title;

        ViewHolder(View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.id);
            original_language = itemView.findViewById(R.id.original_language);
            popularity = itemView.findViewById(R.id.popularity);
            title = itemView.findViewById(R.id.title);
        }

    }

}
