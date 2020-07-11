package com.example.managedprofile

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        // As part of the intent, We will need to add an extra key/value to connect the admin
        // managing app to this package
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // For Android M and newer, we would need to provide our implementation for
            // DeviceAdminReceiver.  We instantiate a ComponentName object with the class name and
            // provide that as an extra (Parcelable) value, with the
            // EXTRA_PROVISIONING_DEVICE_ADMIN_COMPONENT_NAME key
        } else {
            // For older Android OS, we will need to use the
            // EXTRA_PROVISIONING_DEVICE_ADMIN_PACKAGE_NAME key with the packageName as a value.
            // This sets the device management application as only this package. This is deprecated
            // since Android API-23 supports more than one device admin app
        }

        // Start the action to initiate provisioning this device
        // If successful, DEVICE_ADMIN_ENABLED action will be called and need to be
        // filtered by our DeviceAdminReceiver implementation as callback to the
        // DeviceAdminReceiver.onProfileProvisioningComplete
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // Add code to handle requestCode from our Manager Profile
        super.onActivityResult(requestCode, resultCode, data)
    }

    companion object {
        private const val REQUEST_PROVISION_MANAGED_PROFILE = 1
        fun newInstance(): SetupProfileFragment {
            return SetupProfileFragment()
        }
    }
}