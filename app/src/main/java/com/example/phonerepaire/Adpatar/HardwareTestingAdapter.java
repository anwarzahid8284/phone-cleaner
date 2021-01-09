package com.example.phonerepaire.Adpatar;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.KeyguardManager;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.PointF;
import android.hardware.biometrics.BiometricManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.hardware.fingerprint.FingerprintManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phonerepaire.Classes.DrawLineCanvas;
import com.example.phonerepaire.Model.HardwareTesting;
import com.example.phonerepaire.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.content.Context.KEYGUARD_SERVICE;
import static android.content.Context.VIBRATOR_SERVICE;

public class HardwareTestingAdapter extends RecyclerView.Adapter<HardwareTestingAdapter.HTestingVHolder> {
    List<HardwareTesting> hardwareTestingList;
    Context context;
    LayoutInflater layoutInflater;
    PackageManager pm;
    private CameraManager camManager;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    SweetAlertDialog pDialog;
    View view;
    private static final String TAG = "Touch";


    public HardwareTestingAdapter(List<HardwareTesting> hardwareTestingList, Context context) {
        this.hardwareTestingList = hardwareTestingList;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.pm = context.getPackageManager();


    }

    @NonNull
    @Override
    public HTestingVHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.hardware_testing_item, parent, false);
        return new HTestingVHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HTestingVHolder holder, int position) {
        holder.imageViewTesting.setImageResource(hardwareTestingList.get(position).getDrawable());
        holder.textViewTestingName.setText(hardwareTestingList.get(position).getHardwareTestingName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hardwareValue = hardwareTestingList.get(position).getHardwareTestingName();
                view = v;
                switch (hardwareValue) {
                    case "Display":
                        displayTest();
                        break;
                    case "Vibration Test":
                        phoneVibrate();
                        break;
                    case "SIM Card":
                        simCard();
                        break;
                    case "Touch Sensor":
                        touchSensorTest();
                        break;
                    case "Speaker Test":
                        speakerTest();
                        break;
                    case "Check Headphone":
                        headPhoneTest();
                        break;
                    case "Check Bluetooth":
                        bluetoothTest();
                        break;
                    case "Flashlight":
                        flashLightTest();
                        break;
                    case "Fingerprint Test":
                        fingerPrintTest();
                        break;

                }
            }


        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void displayTest() {
        final Dialog alert = new Dialog(context);
        alert.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alert.setContentView(R.layout.display_test);
        ConstraintLayout constraintLayout = alert.findViewById(R.id.layoutId);
        alert.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        alert.show();
        constraintLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        v.setBackgroundColor(Color.RED);
                        break;
                    case MotionEvent.ACTION_UP:
                        v.setBackgroundColor(Color.BLUE);
                        break;

                }
                return true;
            }

        });
    }


    private void speakerTest() {
        MediaPlayer mp = MediaPlayer.create(context, R.raw.voice);
        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            if (mp != null) {
                mp.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void headPhoneTest() {
        IntentFilter receiverFilter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                final String action = intent.getAction();
                if (Intent.ACTION_HEADSET_PLUG.equals(action)) {
                    int iii = intent.getIntExtra("state", -1);
                    if (iii == 0) {
                        Snackbar.make(view, "microphone  not plugged", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

                    }
                    if (iii == 1) {
                        Snackbar.make(view, "microphone plugged in", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }

                }

            }
        };
        context.registerReceiver(broadcastReceiver, receiverFilter);
    }

    private void bluetoothTest() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter.enable()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    pDialog = new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE);
                    pDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    pDialog.setTitleText("You have Passed test For Bluetooth");
                    pDialog.setCancelable(false);
                    pDialog.show();
                    bluetoothAdapter.disable();
                }
            }, 5000);
        } else {
            Snackbar.make(view, "Bluetooth not working", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }

    }

    private void fingerPrintTest() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (Build.VERSION.SDK_INT >= 28) {
                if (checkForBiometrics()) {
                    Snackbar.make(view, "You test pass for  FingerPrint", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                } else {
                    Snackbar.make(view, "You have not enrolled fingerprint", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

            } else {
                FingerprintManager fingerprintManager = (FingerprintManager) context.getSystemService(Context.FINGERPRINT_SERVICE);
                if (!fingerprintManager.isHardwareDetected()) {
                    Snackbar.make(view, "Fingerprint not supported by this Phone", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else if (!fingerprintManager.hasEnrolledFingerprints()) {
                    Snackbar.make(view, "You are not enrolled FingerPrint", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                } else {
                    Snackbar.make(view, "You have Passed test For Fingerprint", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }

        } else {
            Toast.makeText(context, "Fingerprint not supported by this Phone", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean checkForBiometrics() {
        Log.d(TAG, "checkForBiometrics started");
        boolean canAuthenticate = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Build.VERSION.SDK_INT < 29) {
                KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService(KEYGUARD_SERVICE);
                PackageManager packageManager = context.getPackageManager();
                if (!packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)) {
                    Log.w(TAG, "checkForBiometrics, Fingerprint Sensor not supported");
                    Toast.makeText(context, "checkForBiometrics, Fingerprint Sensor not supported", Toast.LENGTH_SHORT).show();
                    canAuthenticate = false;
                }
                if (!keyguardManager.isKeyguardSecure()) {
                    Log.w(TAG, "checkForBiometrics, Lock screen security not enabled in Settings");
                    Toast.makeText(context, "checkForBiometrics, Lock screen security not enabled in Settings", Toast.LENGTH_SHORT).show();
                    canAuthenticate = false;
                }
            } else {
                BiometricManager biometricManager = context.getSystemService(BiometricManager.class);
                if (biometricManager.canAuthenticate() != BiometricManager.BIOMETRIC_SUCCESS) {
                    Log.w(TAG, "checkForBiometrics, biometrics not supported");
                    Toast.makeText(context, "You are not enrolled FingerPrint", Toast.LENGTH_SHORT).show();
                    canAuthenticate = false;
                }
            }
        } else {
            canAuthenticate = false;
        }
        return canAuthenticate;
    }

    private boolean flashLightTest() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            sharedPreferences = context.getSharedPreferences("Light", Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
            boolean checkLight = sharedPreferences.getBoolean("light", false);
            try {
                camManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
                String cameraId;
                if (checkLight) {
                    if (camManager != null) {
                        cameraId = camManager.getCameraIdList()[0]; // Usually front camera is at 0 position.
                        camManager.setTorchMode(cameraId, false);
                        editor.putBoolean("light", false);
                        editor.apply();
                        return true;
                    }
                } else {
                    cameraId = camManager.getCameraIdList()[0];
                    camManager.setTorchMode(cameraId, true);
                    editor.putBoolean("light", true);
                    editor.apply();
                }
            } catch (CameraAccessException e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }
        return false;
    }


    @SuppressLint("HardwareIds")
    private void simCard() {
        int permissionCheck = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            permissionCheck = context.checkSelfPermission(Manifest.permission.READ_PHONE_STATE);
        }

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
        } else {
            TelephonyManager telemamanger = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                String simSerialNumber = telemamanger.getSimSerialNumber();
                if (!simSerialNumber.isEmpty()) {
                    Toast.makeText(context, "" + telemamanger.getSimOperatorName(), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context, "Sorry Not Supported", Toast.LENGTH_SHORT).show();

            }

        }

    }

    private void phoneVibrate() {
        if (Build.VERSION.SDK_INT >= 26) {
            ((Vibrator) context.getSystemService(VIBRATOR_SERVICE)).vibrate(VibrationEffect.createOneShot(150, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            ((Vibrator) context.getSystemService(VIBRATOR_SERVICE)).vibrate(150);
        }
    }

    @Override
    public int getItemCount() {
        return hardwareTestingList.size();
    }


    public static class HTestingVHolder extends RecyclerView.ViewHolder {
        TextView textViewTestingName;
        ImageView imageViewTesting;

        public HTestingVHolder(@NonNull View itemView) {
            super(itemView);
            textViewTestingName = itemView.findViewById(R.id.testing_name_Id);
            imageViewTesting = itemView.findViewById(R.id.testing_imageView_Id);

        }
    }
    private void touchSensorTest() {
        final Dialog alert = new Dialog(context,R.style.full_screen_dialog);
        alert.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alert.setContentView(R.layout.touch_screen_layout);
        alert.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        DrawLineCanvas drawLineCanvas = alert.findViewById(R.id.dls);
        alert.show();
    }

}
