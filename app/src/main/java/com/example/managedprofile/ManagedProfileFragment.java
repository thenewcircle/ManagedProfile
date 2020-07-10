package com.example.managedprofile;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;


public class ManagedProfileFragment extends Fragment
        implements View.OnClickListener,
        CompoundButton.OnCheckedChangeListener {

    private static final String TAG = "ManagedProfileFragment";

    // Package name of chrome
    private static final String PACKAGE_NAME_CHROME = "com.android.chrome";


    // to remove this managed profile.
    private Button mButtonRemoveProfile;

    // Whether the chrome app is enabled in this profile
    private boolean mChromeEnabled;

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
        // Retrieves whether the chrome app is enabled in this profile
        mChromeEnabled = isApplicationEnabled(PACKAGE_NAME_CHROME);
    }

    // Return the DPM
    private DevicePolicyManager getDevicePolicyManager() {
        // Get the current activity
        FragmentActivity activity = getActivity();
        if (null == activity || activity.isFinishing()) {
            return null;
        }

        // return the DevicePolicyManager
        return null;
    }

    private boolean isAppInstalled(String packageName) {
        // Get the applicationInfo and add the GET_UNINSTALLED_PACKAGES flag
        // to allow getting the application information from the list of
        // uninstalled applications

        // Check the ApplicationInfo of the target app, and see if the flags have
        // ApplicationInfo.FLAG_INSTALLED turned on using bitwise operation.

        // Wrap this in a try catch with NameNotFoundException exception
        return false;
    }

    /**
     * Checks if the application is available in this profile.
     *
     * @param packageName The package name
     * @return True if the application is available in this profile.
     */
    private boolean isApplicationEnabled(String packageName) {
        FragmentActivity activity = getActivity();

        // Check if the app is installed first
        if (isAppInstalled(packageName)) {
            // Query our DPM to see if the app is not hidden in this profile
            // and return the result
        }
        return false;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mButtonRemoveProfile = view.findViewById(R.id.remove_profile);
        mButtonRemoveProfile.setOnClickListener(this);
        Switch toggleChrome = view.findViewById(R.id.toggle_chrome);
        toggleChrome.setChecked(mChromeEnabled);
        toggleChrome.setOnCheckedChangeListener(this);
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
            case R.id.toggle_chrome: {
                setAppEnabled(PACKAGE_NAME_CHROME, checked);
                mChromeEnabled = isApplicationEnabled(PACKAGE_NAME_CHROME);
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
        FragmentActivity activity = getActivity();
        if (null == activity || activity.isFinishing()) {
            return;
        }

        if (!isAppInstalled(packageName)) {
            // If the app is not installed in this profile, we can enable it by
            // DPM.enableSystemApp.  No need to disable and uninstalled app
        } else {
            // If the app is already installed, we will enable or disable it by
            // DPM.setApplicationHidden
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
        FragmentActivity activity = getActivity();
        if (null == activity || activity.isFinishing()) {
            return;
        }

        // Ask that all user data be wiped.

        // The screen turns off here
    }
}
