package com.example.managedprofile;

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

        // launch the EnableProfileActivity now that your device is provisioned
        Intent launch = new Intent(context, EnableProfileActivity.class);
        launch.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(launch);
    }

    public static ComponentName getComponentName(Context context) {
        // We create a unique ComponentName for this DeviceAdminReceiver
        // by providing this application context and the class
        return new ComponentName(context.getApplicationContext(), DeviceAdminReceiverImpl.class);
    }

}