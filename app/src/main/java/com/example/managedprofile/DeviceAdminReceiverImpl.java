package com.example.managedprofile;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Called on the new profile when managed profile provisioning has completed. Managed profile
 * provisioning is the process of setting up the device so that it has a separate profile which
 * is managed by the mobile device management(mdm) application that triggered the provisioning.
 * Note that the managed profile is not fully visible until it is enabled.
 */
public class DeviceAdminReceiverImpl extends android.app.admin.DeviceAdminReceiver {
    private static final String TAG = "DAR";

    @Override
    public void onProfileProvisioningComplete(Context context, Intent intent) {
        Log.d(TAG, "onProfileProvisioningComplete");

        DevicePolicyManager devicePolicyManager =
                (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);

        // This is the name for the newly created managed profile.
        ComponentName componentName = getComponentName(context);
        devicePolicyManager.setProfileName(componentName, context.getString(R.string.profile_name));

        // We enable the profile here.
        devicePolicyManager.setProfileEnabled(componentName);
    }

    public static ComponentName getComponentName(Context context) {
        // We create a unique ComponentName for this DeviceAdminReceiver
        // by providing this application context and the class
        return new ComponentName(context.getApplicationContext(), DeviceAdminReceiverImpl.class);
    }
}