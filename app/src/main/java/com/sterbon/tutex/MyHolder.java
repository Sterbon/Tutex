package com.sterbon.tutex;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class MyHolder extends RecyclerView.ViewHolder {

    TextView nameTxt;

    public MyHolder(View itemView) {
        super(itemView);
        nameTxt = (TextView)itemView.findViewById(R.id.out);
    }
}
