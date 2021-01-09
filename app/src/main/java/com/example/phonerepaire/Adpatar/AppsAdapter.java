package com.example.phonerepaire.Adpatar;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.phonerepaire.Model.AppDetails;
import com.example.phonerepaire.R;

import java.util.List;

public class AppsAdapter extends RecyclerView.Adapter<AppsAdapter.AppsVHolder> {
    List<AppDetails> appDetailsList;
    LayoutInflater layoutInflater;
    Context context;
    boolean value;

    public AppsAdapter(List<AppDetails> appDetailsList, Context context, boolean value) {
        this.appDetailsList = appDetailsList;
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.value = value;
    }

    @NonNull
    @Override
    public AppsVHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.apps_item, parent, false);
        return new AppsVHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AppsVHolder holder, final int position) {
        holder.textViewAppName.setText(appDetailsList.get(position).getAppName());
        holder.textViewPackageName.setText(appDetailsList.get(position).getAppPackage());
        Glide.with(context).load(appDetailsList.get(position).getAppIcon()).into(holder.imageViewAppIcon);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applicationInfo(position, value);
            }
        });

    }

    private void applicationInfo(int position, boolean value) {
        final Dialog alert = new Dialog(context);
        alert.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alert.setContentView(R.layout.application_information);
        ImageView appIcon = alert.findViewById(R.id.iconId);
        TextView appName = alert.findViewById(R.id.appName_id);
        TextView appPackageName = alert.findViewById(R.id.packageNameId);
        TextView appVersion = alert.findViewById(R.id.versionNameId);
        TextView appVersionCode = alert.findViewById(R.id.versionCodeId);
        TextView appFirstTimeInstalled = alert.findViewById(R.id.firstInstalledId);
        TextView appLastTimeInstalled = alert.findViewById(R.id.lastInstalledId);
        TextView appRAMUsed = alert.findViewById(R.id.ramUsedId);
        TextView appStorageUsed = alert.findViewById(R.id.storageUsedId);
        TextView appSize = alert.findViewById(R.id.applicationSizeId);
        Button btnUninstalled = alert.findViewById(R.id.btnUninstalled_id);
        alert.setCancelable(true);
        alert.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        Glide.with(context).load(appDetailsList.get(position).getAppIcon()).into(appIcon);
        appName.setText(appDetailsList.get(position).getAppName());
        appPackageName.setText(appDetailsList.get(position).getAppPackage());
        appVersion.setText(appDetailsList.get(position).getVersionName());
        appVersionCode.setText("" + appDetailsList.get(position).getVersionCode());
        appFirstTimeInstalled.setText(appDetailsList.get(position).getFirstTimeInstalled());
        appLastTimeInstalled.setText(appDetailsList.get(position).getLastUpdateInstalled());
        appRAMUsed.setText(""+appDetailsList.get(position).getMemoryUsed());
        appStorageUsed.setText("" + appDetailsList.get(position).getStorageUsed());
        appSize.setText(appDetailsList.get(position).getApkSize());
        if (value) {
            btnUninstalled.setVisibility(View.VISIBLE);
        } else {
            btnUninstalled.setVisibility(View.GONE);
        }
        alert.show();
        btnUninstalled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_UNINSTALL_PACKAGE);
                intent.setData(Uri.parse("package:"+appDetailsList.get(position).getAppPackage()));
                context.startActivity(intent);
                appDetailsList.remove(position);
                notifyDataSetChanged();
                alert.dismiss();
            }
        });


    }


    @Override
    public int getItemCount() {
        return appDetailsList.size();
    }

    public static class AppsVHolder extends RecyclerView.ViewHolder {
        TextView textViewAppName, textViewPackageName;
        ImageView imageViewAppIcon;

        public AppsVHolder(@NonNull View itemView) {
            super(itemView);
            textViewAppName = itemView.findViewById(R.id.appId);
            textViewPackageName = itemView.findViewById(R.id.packageId);
            imageViewAppIcon = itemView.findViewById(R.id.appIconId);
        }
    }
}
