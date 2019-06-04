package com.example.managedprofile


import android.content.Intent
import android.os.Bundle

import androidx.fragment.app.Fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


/**
 * This [Fragment] handles initiation of managed profile provisioning.
 */
class SetupProfileFragment : Fragment(), View.OnClickListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_setup_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.findViewById<View>(R.id.set_up_profile).setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.set_up_profile -> {
                provisionManagedProfile()
            }
        }
    }

    /**
     * Initiates the managed profile provisioning. If we already have a managed profile set up on
     * this device, we will get an error dialog in the following provisioning phase.
     */
    private fun provisionManagedProfile() {
        val activity = activity ?: return

        // Create an intent that will have an ACTION_PROVISION_MANAGED_PROFILE as action

        // This app will also managed the work profile, so we target our own package name
        // by adding it to the Extra of EXTRA_PROVISIONING_DEVICE_ADMIN_PACKAGE_NAME
        // of the intent

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

        private val REQUEST_PROVISION_MANAGED_PROFILE = 1

        fun newInstance(): SetupProfileFragment {
            return SetupProfileFragment()
        }
    }

}