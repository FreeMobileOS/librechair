package com.google.android.apps.nexuslauncher;

import android.animation.AnimatorSet;
import android.os.Bundle;
import android.support.annotation.Nullable;
import ch.deletescape.lawnchair.settings.ui.SettingsActivity;
import com.android.launcher3.Launcher;
import com.android.launcher3.Utilities;
import com.android.launcher3.config.FeatureFlags;
import com.google.android.libraries.gsa.launcherclient.LauncherClient;

public class NexusLauncherActivity extends Launcher {

    private NexusLauncher mLauncher;

    public NexusLauncherActivity() {
        mLauncher = new NexusLauncher(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        FeatureFlags.QSB_ON_FIRST_SCREEN = showSmartspace();
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (FeatureFlags.QSB_ON_FIRST_SCREEN != showSmartspace()) {
            if (Utilities.ATLEAST_NOUGAT) {
                recreate();
            } else {
                finish();
                startActivity(getIntent());
            }
        }
    }

    private boolean showSmartspace() {
        return Utilities.getPrefs(this).getBoolean(SettingsActivity.SMARTSPACE_PREF, true);
    }

    @Nullable
    public LauncherClient getGoogleNow() {
        return mLauncher.mClient;
    }

    public void playQsbAnimation() {
        mLauncher.mQsbAnimationController.dZ();
    }

    public AnimatorSet openQsb() {
        return mLauncher.mQsbAnimationController.openQsb();
    }
}
