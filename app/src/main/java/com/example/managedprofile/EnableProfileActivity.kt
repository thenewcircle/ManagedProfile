package com.example.managedprofile

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class EnableProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (null == savedInstanceState) {
            val helper = PostProvisioningHelper(this)
            // Important: After the profile has been created, the MDM must enable it for corporate
            // apps to become visible in the launcher.
            helper.completeProvisioning()
        }
        // This is just a friendly shortcut to the main screen.
        setContentView(R.layout.activity_setup)
        findViewById<View>(R.id.icon).setOnClickListener { v: View? ->
            // Opens up the main screen
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}