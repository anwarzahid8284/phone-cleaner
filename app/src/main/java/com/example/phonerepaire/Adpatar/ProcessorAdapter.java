package com.example.phonerepaire.Adpatar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phonerepaire.Model.ProcessorDetails;
import com.example.phonerepaire.R;

import java.util.List;
import java.util.concurrent.Flow;

public class ProcessorAdapter extends RecyclerView.Adapter<ProcessorAdapter.ProcessorVHolder> {
    Context context;
    LayoutInflater layoutInflater;
    List<ProcessorDetails> processorDetailsList;

    public ProcessorAdapter(List<ProcessorDetails> processorDetails, Context context) {
        this.processorDetailsList = processorDetails;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public ProcessorVHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.processor_item, parent, false);
        return new ProcessorVHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProcessorVHolder holder, int position) {
        holder.textViewCPU.setText(processorDetailsList.get(position).getCpu());
        holder.textViewCPUName.setText(processorDetailsList.get(position).getCpuName());
    }

    @Override
    public int getItemCount() {
        return processorDetailsList.size();
    }

    public static class ProcessorVHolder extends RecyclerView.ViewHolder {
        TextView textViewCPU, textViewCPUName;

        public ProcessorVHolder(@NonNull View itemView) {
            super(itemView);
            textViewCPU = itemView.findViewById(R.id.cpu_id);
            textViewCPUName = itemView.findViewById(R.id.cpu_nameId);
        }
    }
}
