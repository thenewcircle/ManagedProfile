package com.example.managedprofile;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.os.Bundle;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            DevicePolicyManager manager = (DevicePolicyManager)
                    getSystemService(Context.DEVICE_POLICY_SERVICE);
            if (manager.isProfileOwnerApp(getApplicationContext().getPackageName())) {
                // If the managed profile is already set up, we show the main screen.
                showMainFragment();
            } else {
                // If not, we show the set up screen.
                showSetupProfile();
            }
        }
    }

    private void showSetupProfile() {
        getFragmentManager().beginTransaction()
                .replace(R.id.container, SetupProfileFragment.newInstance())
                .commit();
    }

    private void showMainFragment() {
        getFragmentManager().beginTransaction()
                .add(R.id.container, ManagedProfileFragment.newInstance())
                .commit();
    }
}
