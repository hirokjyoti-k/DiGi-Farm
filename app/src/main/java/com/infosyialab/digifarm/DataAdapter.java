package com.infosyialab.digifarm;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataViewHolder> {


    List<Data> dataList;
    Context context;

    public DataAdapter(Context context, List<Data> dataList) {
        this.dataList = dataList;
        this.context = context;
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_view, viewGroup, false);
        return new DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder dataViewHolder, int i) {

        final Data data = dataList.get(i);

        dataViewHolder.name.setText(data.getName());
        Glide.with(context).load(data.getImage()).into(dataViewHolder.imageView);

        dataViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Details.class);
                intent.putExtra("Name", data.getName());
                intent.putExtra("Humidity", data.getHumidity());
                intent.putExtra("Time", data.getTime());
                intent.putExtra("Soil", data.getSoil());
                intent.putExtra("Temperature", data.getTemperature());
                intent.putExtra("Image", data.getImage());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class DataViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        ImageView imageView;

        public DataViewHolder(@NonNull View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.cpName);
            imageView = (ImageView) itemView.findViewById(R.id.img);

        }
    }
}
