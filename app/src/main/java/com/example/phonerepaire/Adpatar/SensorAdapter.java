package com.example.phonerepaire.Adpatar;

import android.content.Context;
import android.hardware.Sensor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phonerepaire.Model.SensorDetails;
import com.example.phonerepaire.R;

import java.util.List;

public class SensorAdapter extends RecyclerView.Adapter<SensorAdapter.SensorVHolder> {
    List<SensorDetails> sensorDetailsList;
    Context context;
    LayoutInflater layoutInflater;

    public SensorAdapter(List<SensorDetails> sensorDetailsList, Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.sensorDetailsList = sensorDetailsList;

    }

    @NonNull
    @Override
    public SensorVHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.sensor_item, parent, false);
        return new SensorVHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SensorVHolder holder, int position) {
        holder.textViewSensorName.setText(sensorDetailsList.get(position).getSensorName());
    }

    @Override
    public int getItemCount() {
        return sensorDetailsList.size();
    }

    public static class SensorVHolder extends RecyclerView.ViewHolder {
        TextView textViewSensorName;

        public SensorVHolder(@NonNull View itemView) {
            super(itemView);
            textViewSensorName = itemView.findViewById(R.id.sensor_id);
        }
    }
}
