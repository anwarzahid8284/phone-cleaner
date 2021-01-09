package com.example.phonerepaire.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.phonerepaire.Adpatar.SensorAdapter;
import com.example.phonerepaire.Fragments.BatteryFragment;
import com.example.phonerepaire.Fragments.BatteryInfoFragment;
import com.example.phonerepaire.Fragments.BatterySaverFragment;
import com.example.phonerepaire.Fragments.BluetoothFragment;
import com.example.phonerepaire.Fragments.CleanCacheFragment;
import com.example.phonerepaire.Fragments.DisplayFragment;
import com.example.phonerepaire.Fragments.EmptyFolderFragment;
import com.example.phonerepaire.Fragments.FeaturesFragment;
import com.example.phonerepaire.Fragments.HardwareTestingFragment;
import com.example.phonerepaire.Fragments.ManageAppsFragment;
import com.example.phonerepaire.Fragments.MemoryFragment;
import com.example.phonerepaire.Fragments.MobileFragment;
import com.example.phonerepaire.Fragments.NetworkSpeedTestingFragment;
import com.example.phonerepaire.Fragments.OperatingSystemFragment;
import com.example.phonerepaire.Fragments.PorcessorFragment;
import com.example.phonerepaire.Fragments.RamBoosterFragment;
import com.example.phonerepaire.Fragments.RepairSystemFragment;
import com.example.phonerepaire.Fragments.SensorFragment;
import com.example.phonerepaire.Fragments.StorageAndRAMFragment;
import com.example.phonerepaire.R;

import java.util.Objects;

public class ToolActivity extends AppCompatActivity {
    Fragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tool);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        String selectedOption = intent.getStringExtra("selectOption");
        if (selectedOption != null) {
            switch (selectedOption) {
                case "CleanCache":
                    fragment = new CleanCacheFragment();
                    ToolActivity.this.setTitle(R.string.clean_cache);
                    break;
                case "ManageApps":
                    fragment = new ManageAppsFragment();
                    ToolActivity.this.setTitle(R.string.manage_apps);
                    break;
                case "RepairSystem":
                    fragment = new RepairSystemFragment();
                    ToolActivity.this.setTitle(R.string.repair_system);
                    break;
                case "BoosterRAM":
                    fragment = new RamBoosterFragment();
                    ToolActivity.this.setTitle(R.string.booster_ram);
                    break;
                case "EmptyFolder":
                    fragment = new EmptyFolderFragment();
                    ToolActivity.this.setTitle(R.string.empty_folder);
                    break;
                case "BatterySaver":
                    fragment = new BatterySaverFragment();
                    ToolActivity.this.setTitle(R.string.battery_saver);
                    break;
                case "WifiSpeed":
                    fragment = new NetworkSpeedTestingFragment();
                    ToolActivity.this.setTitle("Wifi Speed");

                    break;
                case "HardwareTesting":
                    fragment = new HardwareTestingFragment();
                    ToolActivity.this.setTitle("Hardware Testing");
                    break;
                case "memory":
                    fragment=new MemoryFragment();
                    ToolActivity.this.setTitle("Memory");
                    break;
                case "BatteryFragment":
                    fragment=new BatteryInfoFragment();
                    ToolActivity.this.setTitle(R.string.battery_information);
                    break;
                case "Battery":
                    fragment=new BatteryFragment();
                    ToolActivity.this.setTitle("Battery");
                    break;
                case "Storage and RAM":
                    fragment=new StorageAndRAMFragment();
                    ToolActivity.this.setTitle("Storage And RAM");
                    break;
                case "Features":
                    fragment=new FeaturesFragment();
                    ToolActivity.this.setTitle("Features");
                    break;
                case "Mobile":
                    fragment=new MobileFragment();
                    ToolActivity.this.setTitle("Mobile");
                    break;
                case "Operating System":
                    fragment=new OperatingSystemFragment();
                    ToolActivity.this.setTitle("Operating System");
                    break;
                case "Display":
                    fragment=new DisplayFragment();
                    ToolActivity.this.setTitle("Display");
                    break;
                case "Processor":
                    fragment=new PorcessorFragment();
                    ToolActivity.this.setTitle("Processor");
                    break;
                case "Bluetooth":
                    fragment=new BluetoothFragment();
                    ToolActivity.this.setTitle("Bluetooth");
                    break;
                case "Sensor":
                    fragment=new SensorFragment();
                    ToolActivity.this.setTitle("Sensors");
                    break;
            }
             FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.toolActivity_Frag_container_Id, fragment);
            transaction.addToBackStack(null);
            transaction.commit();

        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}