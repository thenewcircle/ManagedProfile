package com.example.managedprofile;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import static android.app.admin.DevicePolicyManager.ACTION_PROVISION_MANAGED_PROFILE;
import static android.app.admin.DevicePolicyManager.EXTRA_PROVISIONING_DEVICE_ADMIN_PACKAGE_NAME;


/**
 * This {@link android.app.Fragment} handles initiation of managed profile provisioning.
 */
public class SetupProfileFragment extends android.app.Fragment implements View.OnClickListener {

    private static final int REQUEST_PROVISION_MANAGED_PROFILE = 1;

    public static SetupProfileFragment newInstance() {
        return new SetupProfileFragment();
    }

    public SetupProfileFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setup_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        view.findViewById(R.id.set_up_profile).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.set_up_profile: {
                provisionManagedProfile();
                break;
            }
        }
    }

    /**
     * Initiates the managed profile provisioning. If we already have a managed profile set up on
     * this device, we will get an error dialog in the following provisioning phase.
     */
    private void provisionManagedProfile() {
        Activity activity = getActivity();
        if (null == activity) {
            return;
        }

        // Create an intent that will have an ACTION_PROVISION_MANAGED_PROFILE as action

        // This app will also managed the work profile, so we target our own package name
        // by adding it to the Extra of EXTRA_PROVISIONING_DEVICE_ADMIN_PACKAGE_NAME
        // of the intent

        // Start the action to initiate provisioning this device
        // If successful, DEVICE_ADMIN_ENABLED action will be called and need to be
        // filtered by our DeviceAdminReceiver implementation as callback to the
        // DeviceAdminReceiver.onProfileProvisioningComplete
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Add code to handle requestCode from our Manager Profile

        super.onActivityResult(requestCode, resultCode, data);
    }

}