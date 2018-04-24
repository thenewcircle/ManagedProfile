package com.example.managedprofile;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import static android.app.admin.DevicePolicyManager.FLAG_MANAGED_CAN_ACCESS_PARENT;
import static android.app.admin.DevicePolicyManager.FLAG_PARENT_CAN_ACCESS_MANAGED;


public class ManagedProfileFragment extends android.app.Fragment
        implements View.OnClickListener,
        CompoundButton.OnCheckedChangeListener {

    private static final String TAG = "ManagedProfileFragment";

    // Package name of calculator
    private static final String PACKAGE_NAME_CALCULATOR = "com.android.calculator2";


    // to remove this managed profile.
    private Button mButtonRemoveProfile;

    // Whether the calculator app is enabled in this profile
    private boolean mCalculatorEnabled;

    public ManagedProfileFragment() {
    }

    public static ManagedProfileFragment newInstance() {
        return new ManagedProfileFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_managed_profile,
                container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Retrieves whether the calculator app is enabled in this profile
        mCalculatorEnabled = isApplicationEnabled(PACKAGE_NAME_CALCULATOR);
    }

    // Return the DPM
    private DevicePolicyManager getDevicePolicyManager() {
        // Get the current activity
        Activity activity = getActivity();
        if (null == activity || activity.isFinishing()) {
            return null;
        }

        // return the DevicePolicyManager
        return (DevicePolicyManager) activity.getSystemService(Context.DEVICE_POLICY_SERVICE);
    }

    private boolean isAppInstalled(String packageName) {
        try {
            // Get the applicationInfo and add the GET_UNINSTALLED_PACKAGES flag
            // to allow getting the application information from the list of
            // uninstalled applications
            ApplicationInfo applicationInfo = getActivity().getPackageManager()
                    .getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);

            // Here, we check the ApplicationInfo of the target app, and see if the flags have
            // ApplicationInfo.FLAG_INSTALLED turned on using bitwise operation.
            return !(0 ==
                    (applicationInfo.flags & ApplicationInfo.FLAG_INSTALLED));
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    /**
     * Checks if the application is available in this profile.
     *
     * @param packageName The package name
     * @return True if the application is available in this profile.
     */
    private boolean isApplicationEnabled(String packageName) {
        Activity activity = getActivity();

        // Check if the app is installed first
        if (isAppInstalled(packageName)) {
            // Query our DPM to see if the app is not hidden in this profile
            // and return the result
            return !getDevicePolicyManager().isApplicationHidden(
                    DeviceAdminReceiverImpl.getComponentName(activity), packageName);
        }
        return false;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mButtonRemoveProfile = view.findViewById(R.id.remove_profile);
        mButtonRemoveProfile.setOnClickListener(this);
        Switch toggleCalculator = view.findViewById(R.id.toggle_calculator);
        toggleCalculator.setChecked(mCalculatorEnabled);
        toggleCalculator.setOnCheckedChangeListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.remove_profile: {
                mButtonRemoveProfile.setEnabled(false);
                removeProfile();
                break;
            }
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
        switch (compoundButton.getId()) {
            case R.id.toggle_calculator: {
                setAppEnabled(PACKAGE_NAME_CALCULATOR, checked);
                mCalculatorEnabled = isApplicationEnabled(PACKAGE_NAME_CALCULATOR);
                break;
            }
        }
    }

    /**
     * Enables or disables the specified app in this profile.
     *
     * @param packageName The package name of the target app.
     * @param enabled     Pass true to enable the app.
     */
    private void setAppEnabled(String packageName, boolean enabled) {
        Activity activity = getActivity();
        if (null == activity || activity.isFinishing()) {
            return;
        }

        if (!isAppInstalled(packageName)) {
            // If the app is not installed in this profile, we can enable it by
            // DPM.enableSystemApp.  No need to disable and uninstalled app
            if (enabled) {
                getDevicePolicyManager().enableSystemApp(
                        DeviceAdminReceiverImpl.getComponentName(activity), packageName);
            } else {
                // But we cannot disable the app since it is already disabled
                Log.e(TAG, "Cannot disable this app: " + packageName);
                return;
            }
        } else {
            // If the app is already installed, we will enable or disable it by
            // DPM.setApplicationHidden
            getDevicePolicyManager().setApplicationHidden(
                    DeviceAdminReceiverImpl.getComponentName(activity), packageName, !enabled);
        }
        Toast.makeText(activity,
                isApplicationEnabled(packageName) ? R.string.enabled : R.string.disabled,
                Toast.LENGTH_SHORT).show();
    }

    /**
     * Wipes out all the data related to this managed profile.
     */
    private void removeProfile() {
        // Get the current activity
        Activity activity = getActivity();
        if (null == activity || activity.isFinishing()) {
            return;
        }

        // Ask that all user data be wiped.
        getDevicePolicyManager().wipeData(0);

        // The screen turns off here
    }
}
