package com.example.phonerepaire.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AppOpsManager;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phonerepaire.R;

public class SplashScreen extends AppCompatActivity {
    private long SPLASH_DISPLAY_LENGTH = 4000;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    boolean isStorage = false, isUsageAccess = false, isContact = false
            ,isLocation=false,isCamera=false;
    Button buttonStorage,buttonUsageAccess,buttonContactAccess
            ,buttonLocationAccess,buttonCameraAccess;
    Handler handler = new Handler(Looper.getMainLooper());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        sharedPreferences = getSharedPreferences("PermissionScreen", MODE_PRIVATE);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (acceptedCondition()) {
                    if (storagePermission() & usagePermission() & contactPermission()
                    & locationPermission() & cameraPermission()) {
                        Intent intent = new Intent(SplashScreen.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        acceptPolicy();
                    }

                } else {
                    acceptPolicy();
                }
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    private void acceptPolicy() {
        final Dialog alert = new Dialog(SplashScreen.this);
        alert.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alert.setContentView(R.layout.permission_allowed);
        alert.setCancelable(false);
         buttonStorage = alert.findViewById(R.id.storageBtnAccess);
         buttonUsageAccess = alert.findViewById(R.id.usageAccesBtnAccess);
         buttonContactAccess = alert.findViewById(R.id.contactBtnAccess);
         buttonLocationAccess=alert.findViewById(R.id.locationBtnAccess);
         buttonCameraAccess=alert.findViewById(R.id.cameraBtnAccess);
        TextView textView = alert.findViewById(R.id.forwardId);
        alert.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        alert.show();
        // allow storage access
        buttonStorage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (storagePermission()) {
                    buttonStorage.setText("Allowed");
                    buttonStorage.setBackgroundResource(R.drawable.clicked_button_bg);
                    editor = sharedPreferences.edit();
                    editor.putBoolean("storagePermission", true);
                    editor.apply();
                }else {
                    buttonStorage.setText("Allow Access");
                    buttonStorage.setBackgroundResource(R.drawable.button_bg);
                }
            }
        });

        // allow usage access
        buttonUsageAccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!usagePermission()) {
                    Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
                    startActivity(intent);
                    buttonUsageAccess.setText("Allow Access");
                    buttonUsageAccess.setBackgroundResource(R.drawable.button_bg);
                    editor = sharedPreferences.edit();
                    editor.putBoolean("usageAccessPermission", true);
                    editor.apply();
                }else {
                    buttonUsageAccess.setText("Allowed");
                    buttonUsageAccess.setBackgroundResource(R.drawable.clicked_button_bg);
                }
            }
        });

        // allow contact access
        buttonContactAccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (contactPermission()) {
                    buttonContactAccess.setText("Allowed");
                    buttonContactAccess.setBackgroundResource(R.drawable.clicked_button_bg);
                    editor = sharedPreferences.edit();
                    editor.putBoolean("contactPermission", true);
                    editor.apply();
                }else {
                    buttonContactAccess.setText("Allow Access");
                    buttonContactAccess.setBackgroundResource(R.drawable.button_bg);
                }
            }
        });

        // allow location Access
        buttonLocationAccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (locationPermission()) {
                    buttonLocationAccess.setText("Allowed");
                    buttonLocationAccess.setBackgroundResource(R.drawable.clicked_button_bg);
                    editor = sharedPreferences.edit();
                    editor.putBoolean("locationPermission", true);
                    editor.apply();
                }else {
                    buttonLocationAccess.setText("Allow Access");
                    buttonLocationAccess.setBackgroundResource(R.drawable.button_bg);
                }
            }
        });
        buttonCameraAccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cameraPermission()) {
                    buttonCameraAccess.setText("Allowed");
                    buttonCameraAccess.setBackgroundResource(R.drawable.clicked_button_bg);
                    editor = sharedPreferences.edit();
                    editor.putBoolean("cameraPermission", true);
                    editor.apply();
                }else {
                    buttonCameraAccess.setText("Allow Access");
                    buttonCameraAccess.setBackgroundResource(R.drawable.button_bg);
                }
            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (acceptedCondition()) {
                    Intent intent = new Intent(SplashScreen.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                    alert.dismiss();
                } else {
                    Toast.makeText(SplashScreen.this, "Permission Required", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private boolean storagePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

                return true;
            } else {
                return true;
            }
        }
        return false;
    }

    private boolean usagePermission() {
        try {
            PackageManager packageManager = getPackageManager();
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(getPackageName(), 0);
            AppOpsManager appOpsManager = (AppOpsManager) getSystemService(Context.APP_OPS_SERVICE);
            int mode = 0;
            mode = appOpsManager.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
                    applicationInfo.uid, applicationInfo.packageName);
            return (mode == AppOpsManager.MODE_ALLOWED);

        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    private boolean cameraPermission(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
                return true;
            } else {
                return true;
            }
        }else {
            return true;
        }
    }

    //  location permission granted
    private boolean locationPermission(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                return true;
            } else {
                return true;
            }
        }else {
            return true;
        }
    }

    private boolean contactPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_CALL_LOG}, 1);
                return true;
            } else {
                return true;
            }
        }
        return false;
    }

    public boolean acceptedCondition() {
        isStorage = sharedPreferences.getBoolean("storagePermission", false);
        isUsageAccess = sharedPreferences.getBoolean("usageAccessPermission", false);
        isContact = sharedPreferences.getBoolean("contactPermission", false);
        isLocation = sharedPreferences.getBoolean("locationPermission", false);
        isCamera = sharedPreferences.getBoolean("cameraPermission", false);
        if (isContact & isUsageAccess & isContact & isLocation & isCamera) {
            return true;
        }
        return false;
    }

}