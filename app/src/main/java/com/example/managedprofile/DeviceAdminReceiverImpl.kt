package com.example.managedprofile

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.util.Log

/**
 * Called on the new profile when managed profile provisioning has completed. Managed profile
 * provisioning is the process of setting up the device so that it has a separate profile which
 * is managed by the mobile device management(mdm) application that triggered the provisioning.
 * Note that the managed profile is not fully visible until it is enabled.
 */
class DeviceAdminReceiverImpl : android.app.admin.DeviceAdminReceiver() {

    override fun onProfileProvisioningComplete(context: Context, intent: Intent) {
        Log.d(TAG, "onProfileProvisioningComplete")

        // launch the EnableProfileActivity now that your device is provisioned
        val launch = Intent(context, EnableProfileActivity::class.java)
        launch.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(launch)
    }

    companion object {
        private const val TAG = "DAR"

        fun getComponentName(context: Context): ComponentName {
            // We create a unique ComponentName for this DeviceAdminReceiver
            // by providing this application context and the class
            return ComponentName(context.applicationContext, DeviceAdminReceiverImpl::class.java)
        }
    }
}