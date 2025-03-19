package com.sultan.mobileocity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class myHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    ImageView img;
    TextView nameTxt;
    ItemClickListener itemClickListener;

    public myHolder(@NonNull View itemView) {
        super(itemView);

        this.img= itemView.findViewById(R.id.modelImage);
        this.nameTxt = itemView.findViewById(R.id.modelTxt);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        this.itemClickListener.onItemClick(v, getLayoutPosition());
    }

    public void setItemClickListener(ItemClickListener ic){
        this.itemClickListener = ic;
    }
}
