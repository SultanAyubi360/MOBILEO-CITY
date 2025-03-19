package com.sultan.mobileocity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<myHolder> implements Filterable {

    Context context;
    ArrayList<Model> models,filterList;
    CustomFilter filter;

    public MyAdapter(Context context, ArrayList<Model> models) {
        this.context = context;
        this.models = models;
        this.filterList = models;
    }

    @NonNull
    @Override
    public myHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.model,null);
        myHolder holder = new myHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull myHolder holder, int position) {
        holder.nameTxt.setText(models.get(position).getName());
        holder.img.setImageResource(models.get(position).getImg());
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View view, int pos) {
                if(models.get(pos).getName().equals("General")){
                    Intent intent = new Intent(context,GeneralActivity.class);
                    context.startActivity(intent);
                }
                if(models.get(pos).getName().equals("Mobile Id")){
                    Intent intent = new Intent(context,DeviceIDActivity.class);
                    context.startActivity(intent);
                }
                if(models.get(pos).getName().equals("Display")){
                    Intent intent = new Intent(context,DisplayActivity.class);
                    context.startActivity(intent);
                }
                if(models.get(pos).getName().equals("Battery")){
                    Intent intent = new Intent(context,BatteryActivity.class);
                    context.startActivity(intent);
                }
                if(models.get(pos).getName().equals("User Apps")){
                    Intent intent = new Intent(context,UserAppsActivity.class);
                    context.startActivity(intent);
                }
                if(models.get(pos).getName().equals("System Apps")){
                    Intent intent = new Intent(context,SystemAppsActivity.class);
                    context.startActivity(intent);
                }
                if(models.get(pos).getName().equals("Memory")){
                    Intent intent = new Intent(context,MemoryActivity.class);
                    context.startActivity(intent);
                }
                if(models.get(pos).getName().equals("CPU")){
                    Intent intent = new Intent(context,CpuActivity.class);
                    context.startActivity(intent);
                }
                if(models.get(pos).getName().equals("Sensors")){
                    Intent intent = new Intent(context,SensorsActivity.class);
                    context.startActivity(intent);
                }
                if(models.get(pos).getName().equals("SIM")){
                    Intent intent = new Intent(context,SimActivity.class);
                    context.startActivity(intent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    @Override
    public Filter getFilter() {
        if(filter == null){
            filter = new CustomFilter(filterList,this);
        }
        return filter;
    }
}
