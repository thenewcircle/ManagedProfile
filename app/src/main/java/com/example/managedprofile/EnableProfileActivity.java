package com.example.managedprofile;

import android.app.Activity;
import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class EnableProfileActivity extends Activity implements View.OnClickListener {

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
        findViewById(R.id.icon).setOnClickListener(this);
    }

    private void enableProfile() {
        // Get the DevicePolicyManager
        DevicePolicyManager devicePolicyManager =
                (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);

        // set a profile name by providing the devicePolicyManager a componentName
        // and a name for this profile
        ComponentName componentName = DeviceAdminReceiverImpl.getComponentName(this);
        devicePolicyManager.setProfileName(componentName, getString(R.string.profile_name));


        // Enable the profile
        devicePolicyManager.setProfileEnabled(componentName);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.icon: {
                // Opens up the main screen
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
            }
        }
    }

}
