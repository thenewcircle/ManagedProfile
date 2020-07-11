package com.example.managedprofile;


import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import kotlin.Suppress;

import static android.app.admin.DevicePolicyManager.*;


/**
 * This {@link Fragment} handles initiation of managed profile provisioning.
 */
public class SetupProfileFragment extends Fragment {

    private static final int REQUEST_PROVISION_MANAGED_PROFILE = 1;

    public static SetupProfileFragment newInstance() {
        return new SetupProfileFragment();
    }

    public SetupProfileFragment() {
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setup_profile, container, false);
    }

    @Override

    public void onViewCreated(View view, Bundle savedInstanceState) {
        view.findViewById(R.id.set_up_profile).setOnClickListener((v) -> provisionManagedProfile());
    }

    /**
     * Initiates the managed profile provisioning. If we already have a managed profile set up on
     * this device, we will get an error dialog in the following provisioning phase.
     */
    private void provisionManagedProfile() {
        FragmentActivity activity = getActivity();
        if (null == activity) {
            return;
        }

        // Create an intent that will have an ACTION_PROVISION_MANAGED_PROFILE as action
        Intent intent = new Intent(ACTION_PROVISION_MANAGED_PROFILE);

        // As part of the intent, We will need to add an extra key/value to connect the admin
        // managing app to this package
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // For Android M and newer, we would need to provide our implementation for
            // DeviceAdminReceiver.  We instantiate a ComponentName object with the class name and
            // provide that as an extra (Parcelable) value, with the
            // EXTRA_PROVISIONING_DEVICE_ADMIN_COMPONENT_NAME key
            intent.putExtra(EXTRA_PROVISIONING_DEVICE_ADMIN_COMPONENT_NAME,
                    new ComponentName(activity, DeviceAdminReceiverImpl.class.getName()));
        } else {
            // For older Android OS, we will need to use the
            // EXTRA_PROVISIONING_DEVICE_ADMIN_PACKAGE_NAME key with the packageName as a value.
            // This sets the device management application as only this package. This is deprecated
            // since Android API-23 supports more than one device admin app
            intent.putExtra(EXTRA_PROVISIONING_DEVICE_ADMIN_PACKAGE_NAME,
                    activity.getApplicationContext().getPackageName());
        }

        // This app will also manage the work profile so we target our own package name by putting
        // it as an extra value in the intent with the EXTRA_PROVISIONING_DEVICE_ADMIN_PACKAGE_NAME
        // key

        // Start the action to initiate provisioning this device
        // If successful, DEVICE_ADMIN_ENABLED action will be called and need to be
        // filtered by our DeviceAdminReceiver implementation as callback to the
        // DeviceAdminReceiver.onProfileProvisioningComplete
        if (intent.resolveActivity(activity.getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_PROVISION_MANAGED_PROFILE);
            activity.finish();
        } else {
            Toast.makeText(activity, "Device provisioning is not enabled. Stopping.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Add code to handle requestCode from our Managed Profile
        if (requestCode == REQUEST_PROVISION_MANAGED_PROFILE) {
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(getActivity(), "Provisioning done.",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Provisioning failed.",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}