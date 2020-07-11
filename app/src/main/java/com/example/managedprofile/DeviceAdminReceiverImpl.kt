package com.example.managedprofile

import android.app.admin.DeviceAdminReceiver
import android.app.admin.DevicePolicyManager
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
class DeviceAdminReceiverImpl : DeviceAdminReceiver() {
    override fun onProfileProvisioningComplete(context: Context, intent: Intent) {
        Log.d(TAG, "onProfileProvisioningComplete")

        // Important: After the profile has been created, the MDM must enable it for corporate
        // apps to become visible in the launcher.
        val devicePolicyManager = context.getSystemService(Context.DEVICE_POLICY_SERVICE)
                as DevicePolicyManager

        // This is the name for the newly created managed profile.
        val componentName = getComponentName(context)
        devicePolicyManager.setProfileName(componentName, context.getString(R.string.profile_name))

        // We enable the profile here.
        devicePolicyManager.setProfileEnabled(componentName)
    }

    companion object {
        private const val TAG = "DAR"
        fun getComponentName(context: Context?): ComponentName {
            // We create a unique ComponentName for this DeviceAdminReceiver
            // by providing this application context and the class
            return ComponentName(context!!.applicationContext, DeviceAdminReceiverImpl::class.java)
        }
    }
}