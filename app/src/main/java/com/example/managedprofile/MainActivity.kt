package com.example.managedprofile

import android.app.admin.DevicePolicyManager
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            val manager = getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
            if (manager.isProfileOwnerApp(applicationContext.packageName)) {
                // If the managed profile is already set up, we show the main screen.
                showMainFragment()
            } else {
                // If not, we show the set up screen.
                showSetupProfile()
            }
        }
    }

    private fun showSetupProfile() {
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, SetupProfileFragment.newInstance())
                .commit()
    }

    private fun showMainFragment() {
        supportFragmentManager.beginTransaction()
                .add(R.id.container, ManagedProfileFragment.newInstance())
                .commit()
    }
}