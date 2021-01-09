package com.example.phonerepaire.Adpatar;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phonerepaire.Model.MoreInfo;
import com.example.phonerepaire.R;

import java.util.List;

public class MoreAdapter extends RecyclerView.Adapter<MoreAdapter.MoreVHolder> {
    List<MoreInfo> moreInfoList;
    Context context;
    LayoutInflater layoutInflater;

    public MoreAdapter(List<MoreInfo> moreInfoList, Context context) {
        this.moreInfoList = moreInfoList;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MoreVHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.more_item, parent, false);
        return new MoreVHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoreVHolder holder, final int position) {

        if (moreInfoList.get(position).getMoreText().equals("Battery")) {
            holder.textViewName.setText(moreInfoList.get(position).getMoreText());
            holder.imageView.setImageResource(R.drawable.battery_ic);
            holder.textViewName.setTypeface(Typeface.DEFAULT_BOLD);
            holder.textViewName.setTextColor(context.getResources().getColor(R.color.whiteColor));
            holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
            holder.imageView.setColorFilter(ContextCompat.getColor(context, R.color.whiteColor), android.graphics.PorterDuff.Mode.MULTIPLY);
            holder.textViewName.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
        } else if (moreInfoList.get(position).getMoreText().equals("Storage")) {
            holder.textViewName.setText(moreInfoList.get(position).getMoreText());
            holder.imageView.setImageResource(R.drawable.storage_ic);
            holder.textViewName.setTypeface(Typeface.DEFAULT_BOLD);
            holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
            holder.textViewName.setTextColor(context.getResources().getColor(R.color.whiteColor));
            holder.textViewName.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
            holder.imageView.setColorFilter(ContextCompat.getColor(context, R.color.whiteColor), android.graphics.PorterDuff.Mode.MULTIPLY);

        } else if (moreInfoList.get(position).getMoreText().equals("Phone")) {
            holder.textViewName.setText(moreInfoList.get(position).getMoreText());
            holder.imageView.setImageResource(R.drawable.mobile_ic);
            holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
            holder.textViewName.setTextColor(context.getResources().getColor(R.color.whiteColor));
            holder.textViewName.setTypeface(Typeface.DEFAULT_BOLD);
            holder.textViewName.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
            holder.imageView.setColorFilter(ContextCompat.getColor(context, R.color.whiteColor), android.graphics.PorterDuff.Mode.MULTIPLY);
        } else {
            holder.textViewName.setText(moreInfoList.get(position).getMoreText());
            holder.cardView.setCardBackgroundColor(Color.WHITE);
            holder.textViewName.setTextColor(Color.GRAY);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentSettingFlags(position);
            }
        });
    }


    @Override
    public int getItemCount() {
        return moreInfoList.size();
    }

    public static class MoreVHolder extends RecyclerView.ViewHolder {
        TextView textViewName;
        ImageView imageView;
        CardView cardView;

        public MoreVHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = (TextView) itemView.findViewById(R.id.name_Id);
            imageView = (ImageView) itemView.findViewById(R.id.imageView_Id);
            cardView = (CardView) itemView.findViewById(R.id.cardViewId);

        }
    }

    private void intentSettingFlags(int position) {
        String intentValue = moreInfoList.get(position).getMoreText();
        switch (intentValue) {
            case "Battery Usage":
                context.startActivity(new Intent(Intent.ACTION_POWER_USAGE_SUMMARY));
                break;
            case "Display Setting":
                context.startActivity(new Intent(android.provider.Settings.ACTION_DISPLAY_SETTINGS));
                break;
            case "Save Battery":
                context.startActivity(new Intent(android.provider.Settings.ACTION_BATTERY_SAVER_SETTINGS));
                break;
            case "Storage Setting":
                context.startActivity(new Intent(android.provider.Settings.ACTION_INTERNAL_STORAGE_SETTINGS));
                break;
            case "About Phone":
                context.startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
                break;
            case "Bluetooth Setting":
                context.startActivity(new Intent(android.provider.Settings.ACTION_BLUETOOTH_SETTINGS));
                break;
            case "Manage Network Usage":
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.setComponent(new ComponentName("com.android.settings",
                        "com.android.settings.Settings$DataUsageSummaryActivity"));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
                break;
            case "Network Operators":
                context.startActivity(new Intent(android.provider.Settings.ACTION_NETWORK_OPERATOR_SETTINGS));
                break;
            case "Wireless Setting":
                context.startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
                break;
            case "All Wifi Networks":
                context.startActivity(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS));
                break;
            case "Wifi Advanced Setting":
                context.startActivity(new Intent(android.provider.Settings.ACTION_WIFI_IP_SETTINGS));
                break;
            case "Data Roaming Setting":
                context.startActivity(new Intent(android.provider.Settings.ACTION_DATA_ROAMING_SETTINGS));
                break;
        }
    }
}

