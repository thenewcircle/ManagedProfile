package com.example.managedprofile

import android.app.Activity
import android.app.admin.DevicePolicyManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CompoundButton
import android.widget.Switch
import android.widget.Toast
import androidx.fragment.app.Fragment

class ManagedProfileFragment : Fragment(), View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    // to remove this managed profile.
    private var mButtonRemoveProfile: Button? = null

    // whether the chrome app is enabled in this profile
    private var mChromeEnabled = false

    // return the DevicePolicyManager
    private val devicePolicyManager: DevicePolicyManager?
        private get() {
            // Get the current activity
            val activity = activity
            return if (null == activity || activity.isFinishing) {
                null
            } else null
            // return the DevicePolicyManager
        }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_managed_profile,
                container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Retrieves whether the chrome app is enabled in this profile
        mChromeEnabled = isApplicationEnabled(PACKAGE_NAME_CHROME)
    }

    private fun isAppInstalled(packageName: String): Boolean {
        // Get the applicationInfo and add the GET_UNINSTALLED_PACKAGES flag
        // to allow getting the application information from the list of
        // uninstalled applications

        // Check the ApplicationInfo of the target app, and see if the flags have
        // ApplicationInfo.FLAG_INSTALLED turned on using bitwise operation.

        // Wrap this in a try catch with NameNotFoundException exception
        return false
    }

    /**
     * Checks if the application is available in this profile.
     *
     * @param packageName The package name
     * @return True if the application is available in this profile.
     */
    private fun isApplicationEnabled(packageName: String): Boolean {
        val activity = activity

        // Check if the app is installed first
        if (isAppInstalled(packageName)) {
            // Query our DPM to see if the app is not hidden in this profile
            // and return the result
        }
        return false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mButtonRemoveProfile = view.findViewById(R.id.remove_profile)
        mButtonRemoveProfile!!.setOnClickListener(this)
        val toggleChrome = view.findViewById<Switch>(R.id.toggle_chrome)
        toggleChrome.isChecked = mChromeEnabled
        toggleChrome.setOnCheckedChangeListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.remove_profile -> {
                mButtonRemoveProfile!!.isEnabled = false
                removeProfile()
            }
        }
    }

    override fun onCheckedChanged(compoundButton: CompoundButton, checked: Boolean) {
        when (compoundButton.id) {
            R.id.toggle_chrome -> {
                setAppEnabled(PACKAGE_NAME_CHROME, checked)
                mChromeEnabled = isApplicationEnabled(PACKAGE_NAME_CHROME)
            }
        }
    }

    /**
     * Enables or disables the specified app in this profile.
     *
     * @param packageName The package name of the target app.
     * @param enabled     Pass true to enable the app.
     */
    private fun setAppEnabled(packageName: String, enabled: Boolean) {
        val activity = activity
        if (null == activity || activity.isFinishing) {
            return
        }
        if (!isAppInstalled(packageName)) {
            // If the app is not installed in this profile, we can enable it by
            // DPM.enableSystemApp.  No need to disable and uninstalled app
        } else {
            // If the app is already installed, we will enable or disable it by
            // DPM.setApplicationHidden
        }
        Toast.makeText(activity,
                if (isApplicationEnabled(packageName)) R.string.enabled else R.string.disabled,
                Toast.LENGTH_SHORT).show()
    }

    /**
     * Wipes out all the data related to this managed profile.
     */
    private fun removeProfile() {
        // Get the current activity
        val activity = activity
        if (null == activity || activity.isFinishing) {
            return
        }

        // Ask that all user data be wiped.

        // The screen turns off here
    }

    companion object {
        private const val TAG = "ManagedProfileFragment"

        // Package name of chrome
        private const val PACKAGE_NAME_CHROME = "com.android.chrome"
        fun newInstance(): ManagedProfileFragment {
            return ManagedProfileFragment()
        }
    }
}