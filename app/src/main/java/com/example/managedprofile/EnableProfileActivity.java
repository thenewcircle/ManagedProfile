package com.example.managedprofile;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class EnableProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null == savedInstanceState) {
            // Important: After the profile has been created, the MDM must enable it for corporate
            // apps to become visible in the launcher.
            enableProfile();
        }
        // This is just a friendly shortcut to the main screen.
        setContentView(R.layout.activity_setup);
        findViewById(R.id.icon).setOnClickListener((v) -> {
            // Opens up the main screen
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });
    }

    private void enableProfile() {
        // Get the DevicePolicyManager
        DevicePolicyManager devicePolicyManager =
                (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);

        // set a profile name by providing the devicePolicyManager the DeviceAdminReceiver
        // componentName and a name for this profile

        // Enable the profile
    }

}
