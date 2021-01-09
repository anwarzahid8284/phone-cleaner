package com.example.phonerepaire.Adpatar;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phonerepaire.Activity.ToolActivity;
import com.example.phonerepaire.Model.DeviceInfo;
import com.example.phonerepaire.R;

import java.util.List;

public class InfoAdapter extends RecyclerView.Adapter<InfoAdapter.InfoVHolder> {
    Context context;
    List<DeviceInfo> deviceInfoList;
    LayoutInflater layoutInflater;

    public InfoAdapter(List<DeviceInfo> deviceInfoList, Context context) {
        this.context = context;
        this.deviceInfoList = deviceInfoList;
        this.layoutInflater = LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public InfoVHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.info_item, parent, false);
        return new InfoVHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InfoVHolder holder, int position) {
        holder.textViewName.setText(deviceInfoList.get(position).getInfoName());
        holder.imageView.setImageResource(deviceInfoList.get(position).getDrawable());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ToolActivity.class);
                intent.putExtra("selectOption", deviceInfoList.get(position).getInfoName());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return deviceInfoList.size();
    }

    public static class InfoVHolder extends RecyclerView.ViewHolder {
        TextView textViewName;
        ImageView imageView;

        public InfoVHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.nameId);
            imageView = itemView.findViewById(R.id.imageViewId);
        }
    }
}
