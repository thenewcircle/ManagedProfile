package com.example.managedprofile

import android.app.admin.DevicePolicyManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View

class EnableProfileActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (null == savedInstanceState) {
            // Important: After the profile has been created, the MDM must enable it for corporate
            // apps to become visible in the launcher.
            enableProfile()
        }
        // This is just a friendly shortcut to the main screen.
        setContentView(R.layout.activity_setup)
        findViewById<View>(R.id.icon).setOnClickListener(this)
    }

    private fun enableProfile() {
        // Get the DevicePolicyManager
        val devicePolicyManager = getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager

        // set a profile name by providing the devicePolicyManager a componentName
        // and a name for this profile
        val componentName = DeviceAdminReceiverImpl.getComponentName(this)
        devicePolicyManager.setProfileName(componentName, getString(R.string.profile_name))


        // Enable the profile
        devicePolicyManager.setProfileEnabled(componentName)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.icon -> {
                // Opens up the main screen
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
    }
}
