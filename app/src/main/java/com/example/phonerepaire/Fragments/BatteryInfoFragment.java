package com.example.phonerepaire.Fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.phonerepaire.R;

import java.util.Objects;

public class BatteryInfoFragment extends Fragment {
    TextView textViewPowerSource,textViewStatus,
            textViewLevel,textViewHealth,textViewVoltage,textViewCapacity
            ,textViewTemperature,textViewTechnology,textViewPlugged,textViewSize;
    ProgressBar progressBar;
    int percentage=0,progressBarStatus=0;
    Handler handler = new Handler(Looper.getMainLooper());
    IntentFilter intentFilter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.battery_info_frag,container,false);
        progressBar=view.findViewById(R.id.progressBarId);
        textViewPowerSource=view.findViewById(R.id.powerSource_status_id);
        textViewStatus=view.findViewById(R.id.status_status_id);
        textViewLevel=view.findViewById(R.id.level_status_id);
        textViewHealth=view.findViewById(R.id.health_status_id);
        textViewVoltage=view.findViewById(R.id.voltage_status_id);
        textViewCapacity=view.findViewById(R.id.capacity_status_id);
        textViewTemperature=view.findViewById(R.id.temperature_status_id);
        textViewTechnology=view.findViewById(R.id.technology_status_id);
        textViewSize=view.findViewById(R.id.sizeId);
        loadBatterySection();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        requireActivity().registerReceiver(batteryInfoReceiver, intentFilter);

    }

    @Override
    public void onPause() {
        super.onPause();
        requireActivity().unregisterReceiver(batteryInfoReceiver);
    }

    private void loadBatterySection() {
       intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_POWER_CONNECTED);
        intentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
    }
    private BroadcastReceiver batteryInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateBatteryData(intent);
        }
    };
    private void updateBatteryData(Intent intent) {
        int levelPer,scalePer,status;
        boolean present = intent.getBooleanExtra(BatteryManager.EXTRA_PRESENT, false);

        if (present) {
            int health = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, 0);
            String batteryHealth="";
            switch (health) {
                case BatteryManager.BATTERY_HEALTH_COLD:
                    batteryHealth = "Cold";
                    break;

                case BatteryManager.BATTERY_HEALTH_DEAD:
                    batteryHealth="Dead";
                    break;

                case BatteryManager.BATTERY_HEALTH_GOOD:
                    batteryHealth="Good";
                    break;

                case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
                    batteryHealth ="Over Voltage";
                    break;

                case BatteryManager.BATTERY_HEALTH_OVERHEAT:
                    batteryHealth = "Over Heat";
                    break;

                case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
                    batteryHealth = "Unspecified failure";
                    break;

                case BatteryManager.BATTERY_HEALTH_UNKNOWN:
                    batteryHealth="Unknown Health";
                    break;
                default:
            }
            textViewHealth.setText(batteryHealth);


            int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0);
            int pluggedLbl = R.string.battery_notCharging;

            switch (plugged) {
                case BatteryManager.BATTERY_PLUGGED_WIRELESS:
                    pluggedLbl = R.string.battery_wireless;
                    break;

                case BatteryManager.BATTERY_PLUGGED_USB:
                    pluggedLbl = R.string.battery_usbsource;
                    break;

                case BatteryManager.BATTERY_PLUGGED_AC:
                    pluggedLbl = R.string.battery_acsource;
                    break;

                default:
            }

            // display plugged status ...
            textViewPowerSource.setText(pluggedLbl);

            status= intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);

            int statusLbl = R.string.battery_discharging;

            switch (status) {
                case BatteryManager.BATTERY_STATUS_CHARGING:
                    statusLbl = R.string.battery_charge;
                    break;

                case BatteryManager.BATTERY_STATUS_DISCHARGING:
                    statusLbl = R.string.battery_discharging;
                    break;

                case BatteryManager.BATTERY_STATUS_FULL:
                    statusLbl = R.string.battery_batteryFull;
                    break;

                case BatteryManager.BATTERY_STATUS_UNKNOWN:
                    statusLbl = -1;
                    break;
                case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                default:
            }
            if (statusLbl != -1) textViewStatus.setText("" + getString(statusLbl));


            if (intent.getExtras() != null) {
                String technology = intent.getExtras().getString(BatteryManager.EXTRA_TECHNOLOGY);

                if (!"".equals(technology)) {
                    textViewTechnology.setText("" + technology);
                }
            }

            int temperature = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0);

            if (temperature > 0) {
                float temp = ((float) temperature) / 10f;
                textViewTemperature.setText("" + temp + "°C");
            }

            int voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0);

            if (voltage > 0) {
                textViewVoltage.setText("" + voltage + " mV");
            }

            long capacity = getBatteryCapacity(getActivity());

            if (capacity > 0) {
                textViewCapacity.setText("" + capacity + " mAh");
            }

        } else {
        }
        levelPer= intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        scalePer= intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        if (levelPer != -1 && scalePer != -1) {
            percentage = (int) ((levelPer / (float) scalePer) * 100f);
        }
        if (levelPer != -1) {
            textViewLevel.setText(levelPer+"%");
        }
        new Thread(new Runnable() {
            public void run() {
                while ( progressBarStatus<percentage ) {
                    progressBarStatus += 1;
                    handler.post(new Runnable() {
                        public void run() {
                            textViewSize.setText(progressBarStatus + "%");
                            progressBar.setProgress(progressBarStatus);
                        }
                    });
                    try {
                        // Sleep for 200 milliseconds.
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
    public long getBatteryCapacity(Context ctx) {
        BatteryManager mBatteryManager = (BatteryManager) ctx.getSystemService(Context.BATTERY_SERVICE);
        long chargeCounter = mBatteryManager.getLongProperty(BatteryManager.BATTERY_PROPERTY_CHARGE_COUNTER);
        long capacity = mBatteryManager.getLongProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
        return (long) (((float) chargeCounter / (float) capacity) * 100f);

    }

}
