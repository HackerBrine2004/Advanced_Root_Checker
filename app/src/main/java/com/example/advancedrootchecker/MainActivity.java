package com.example.advancedrootchecker;

import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView deviceInfoTextView;
    private TextView rootStatusTextView;
    private Button checkRootButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        deviceInfoTextView = findViewById(R.id.device_info);
        rootStatusTextView = findViewById(R.id.root_status);
        checkRootButton = findViewById(R.id.check_root_button);

        // Display device specifications
        displayDeviceSpecifications();

        // Set up button listener
        checkRootButton.setOnClickListener(v -> checkRootStatus());
    }

    private void displayDeviceSpecifications() {
        String deviceInfo = "Manufacturer: " + Build.MANUFACTURER + "\n" +
                "Model: " + Build.MODEL + "\n" +
                "OS Version: " + Build.VERSION.RELEASE + "\n" +
                "SDK Version: " + Build.VERSION.SDK_INT;
        deviceInfoTextView.setText(deviceInfo);
    }

    private void checkRootStatus() {
        boolean isRooted = isDeviceRooted();
        if (isRooted) {
            rootStatusTextView.setText("Your device is rooted");
        } else {
            rootStatusTextView.setText("Your device is not rooted");
        }
    }

    private boolean isDeviceRooted() {
        // Check for root access binaries
        String[] paths = {"/sbin/su", "/system/xbin/su", "/system/bin/su", "/data/local/su"};
        for (String path : paths) {
            if (new java.io.File(path).exists()) {
                return true;
            }
        }

        // Check if the "su" command can be executed
        try {
            Process process = Runtime.getRuntime().exec("which su");
            java.io.BufferedReader reader = new java.io.BufferedReader(
                    new java.io.InputStreamReader(process.getInputStream()));
            String line = reader.readLine();
            if (line != null && line.contains("su")) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
