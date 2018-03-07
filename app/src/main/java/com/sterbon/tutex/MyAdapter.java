package com.sterbon.tutex;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class MyAdapter extends RecyclerView.Adapter<MyHolder> {
    private String mStrings[];

    public MyAdapter(String[] data) {
         this.mStrings = data;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.model,parent,false);
        return new MyHolder(v);

    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.nameTxt.setText(mStrings[position]);
    }
    @Override
    public int getItemCount() {
        return mStrings.length;
    }
}