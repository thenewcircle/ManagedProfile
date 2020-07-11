package com.example.managedprofile

import android.app.Activity
import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment

/**
 * This [Fragment] handles initiation of managed profile provisioning.
 */
class SetupProfileFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_setup_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.findViewById<View>(R.id.set_up_profile).setOnClickListener { v: View? -> provisionManagedProfile() }
    }

    /**
     * Initiates the managed profile provisioning. If we already have a managed profile set up on
     * this device, we will get an error dialog in the following provisioning phase.
     */
    private fun provisionManagedProfile() {
        val activity = activity ?: return

        // Create an intent that will have an ACTION_PROVISION_MANAGED_PROFILE as action
        val intent = Intent(DevicePolicyManager.ACTION_PROVISION_MANAGED_PROFILE)

        // Use a different intent extra below M to configure the admin component.

        // Use a different intent extra below M to configure the admin component.
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            intent.putExtra(
                    DevicePolicyManager.EXTRA_PROVISIONING_DEVICE_ADMIN_PACKAGE_NAME,
                    activity.applicationContext.packageName
            )
        } else {
            val component = ComponentName(activity, DeviceAdminReceiverImpl::class.java.name)
            intent.putExtra(
                    DevicePolicyManager.EXTRA_PROVISIONING_DEVICE_ADMIN_COMPONENT_NAME,
                    component
            )
        }

        // Start the action to initiate provisioning this device
        // If successful, DEVICE_ADMIN_ENABLED action will be called and need to be
        // filtered by our DeviceAdminReceiver implementation as callback to the
        // DeviceAdminReceiver.onProfileProvisioningComplete
        if (intent.resolveActivity(activity.packageManager) != null) {
            startActivityForResult(intent, REQUEST_PROVISION_MANAGED_PROFILE)
            activity.finish()
        } else {
            Toast.makeText(activity, "Device provisioning is not enabled. Stopping.",
                    Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)

        // Add code to handle requestCode from our Managed Profile
        if (requestCode == REQUEST_PROVISION_MANAGED_PROFILE) {
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(activity, "Provisioning done.",
                        Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(activity, "Provisioning failed.",
                        Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        private const val REQUEST_PROVISION_MANAGED_PROFILE = 1
        fun newInstance(): SetupProfileFragment {
            return SetupProfileFragment()
        }
    }
}