package com.example.managedprofile;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class EnableProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final PostProvisioningHelper helper = new PostProvisioningHelper(this);
        // Important: After the profile has been created, the MDM must enable it for corporate
        // apps to become visible in the launcher.
        helper.completeProvisioning();

        // This is just a friendly shortcut to the main screen.
        setContentView(R.layout.activity_setup);
        findViewById(R.id.icon).setOnClickListener((v) -> {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });
    }
}
